package com.webank.ddcms.service.impl;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.webank.ddcms.bo.LoginUserBO;
import com.webank.ddcms.config.SysConfig;
import com.webank.ddcms.contracts.AccountContract;
import com.webank.ddcms.dao.entity.AccountInfoEntity;
import com.webank.ddcms.dao.entity.CompanyInfoEntity;
import com.webank.ddcms.dao.entity.ThirdPartyInfoEntity;
import com.webank.ddcms.dao.mapper.AccountInfoMapper;
import com.webank.ddcms.dao.mapper.CompanyInfoMapper;
import com.webank.ddcms.dao.mapper.ThirdPartyInfoMapper;
import com.webank.ddcms.enums.AccountStatus;
import com.webank.ddcms.enums.AccountType;
import com.webank.ddcms.enums.CodeEnum;
import com.webank.ddcms.exception.DDCMSException;
import com.webank.ddcms.handler.JwtTokenHandler;
import com.webank.ddcms.handler.ThreadLocalKeyPairHandler;
import com.webank.ddcms.service.AccountService;
import com.webank.ddcms.utils.BlockchainUtils;
import com.webank.ddcms.utils.ThirdPartyUtils;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.request.account.*;
import com.webank.ddcms.vo.response.LoginResponse;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.fisco.bcos.sdk.v3.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private Client client;

    @Autowired
    private CryptoKeyPair witnessKeyPair;

    @Autowired
    private ThreadLocalKeyPairHandler keyPairHandler;

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private TransactionDecoderInterface txDecoder;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenHandler tokenHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ThirdPartyInfoMapper thirdPartyInfoMapper;

    @Autowired
    private ThirdPartyUtils thirdPartyUtils;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResponse registerAccount(RegisterRequest request)
            throws TransactionException, JsonProcessingException {
        // Args
        int accountType = Integer.parseInt(request.getAccountType());
        if (accountType != AccountType.WITNESS.getRoleKey()
                && accountType != AccountType.COMPANY.getRoleKey()) {
            throw new DDCMSException(CodeEnum.ADMIN_NOT_ALLOWED);
        }
        // Generation private key
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        CryptoKeyPair keyPair = null;
        if (!StringUtils.isEmpty(request.getHexPrivateKey())) {
            keyPair = cryptoSuite.loadKeyPair(request.getHexPrivateKey());
        } else {
            keyPair = cryptoSuite.generateRandomKeyPair();
        }
        // Save to blockchain
        AccountContract accountContract =
                AccountContract.load(sysConfig.getContractConfig().getAccountContract(), client, keyPair);
        TransactionReceipt txReceipt =
                accountContract.register(
                        BigInteger.valueOf(Long.parseLong(request.getAccountType())),
                        cryptoSuite.hash(request.getUserName().getBytes()));
        byte[] didBytes = accountContract.getRegisterOutput(txReceipt).getValue1();
        BlockchainUtils.ensureTransactionSuccess(txReceipt, txDecoder);

        AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
        accountInfoEntity.setAccountType(Integer.parseInt(request.getAccountType()));
        accountInfoEntity.setDid(Base64.encode(didBytes));
        accountInfoEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        accountInfoEntity.setStatus(AccountStatus.Registered.ordinal());
        accountInfoEntity.setPrivateKey(keyPair.getHexPrivateKey());
        accountInfoEntity.setUserName(request.getUserName());
        if (accountInfoEntity.getAccountType() == AccountType.ADMIN.getRoleKey()) {
            if (accountInfoMapper.selectTheFirstOne() != null) {
                throw new DDCMSException(CodeEnum.ADMIN_NOT_ALLOWED);
            }
            accountInfoEntity.setStatus(AccountStatus.Approved.ordinal());
        }
        accountInfoMapper.insertAccount(accountInfoEntity);

        CompanyInfoEntity companyInfo =
                objectMapper.readValue(request.getDetailJson(), CompanyInfoEntity.class);
        companyInfo.setAccountId(accountInfoEntity.getPkId());
        companyInfoMapper.insertCompany(companyInfo);

        return CommonResponse.success();
    }

    @Override
    public CommonResponse login(LoginRequest request) {

        AccountInfoEntity accountInfo = accountInfoMapper.selectByUserName(request.getUserName());
        if (accountInfo == null) {
            return CommonResponse.error(CodeEnum.USER_NOT_EXISTS);
        }
        if (accountInfo.getAccountType() != AccountType.ADMIN.getRoleKey()
                && accountInfo.getStatus() != AccountStatus.Approved.ordinal()) {
            return CommonResponse.error(CodeEnum.ACCOUNT_NOT_APPROVED);
        }

        try {
            // 使用auth进行用户认证
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
            // 调用UserDetailService实现类的认证方法
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 认证通过，则生成token，并返回
            LoginUserBO loginInfoBo = (LoginUserBO) authentication.getPrincipal();

            String token =
                    JwtTokenHandler.TOKEN_PREFIX
                            + tokenHandler.generateToken(loginInfoBo.getEntity().getDid());
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setAccountType(String.valueOf(accountInfo.getAccountType()));

            return CommonResponse.success(response);
        } catch (AuthenticationException e) {
            return CommonResponse.error(CodeEnum.LOGIN_FAILED);
        }
    }

    @Override
    public CommonResponse approveAccount(ApproveAccountRequest request) throws TransactionException {
        String did = request.getDid();
        boolean approve = request.isApproved();
        AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
        if (entity == null) {
            return CommonResponse.error(CodeEnum.USER_NOT_EXISTS);
        }
        if (entity.getStatus() == AccountStatus.Approved.ordinal()
                || entity.getStatus() == AccountStatus.Denied.ordinal()) {
            return CommonResponse.error(CodeEnum.ACCOUNT_HAS_APPROVED);
        }
        byte[] didBytes = Base64.decode(did);
        CryptoKeyPair witnessKeyPair = this.witnessKeyPair;
        AccountContract accountModule =
                AccountContract.load(
                        sysConfig.getContractConfig().getAccountContract(), client, witnessKeyPair);
        TransactionReceipt txReceipt = accountModule.approve(didBytes, approve);
        BlockchainUtils.ensureTransactionSuccess(txReceipt, txDecoder);
        // 修改数据库状态
        AccountStatus status = approve ? AccountStatus.Approved : AccountStatus.Denied;
        accountInfoMapper.updateStatus(did, status.ordinal());
        return CommonResponse.success();
    }

    @Override
    public AccountInfoEntity loadAdminAccount() throws Exception {
        AccountInfoEntity accountInfo = accountInfoMapper.selectTheFirstOne();
        if (accountInfo != null && accountInfo.getAccountType() != AccountType.ADMIN.getRoleKey()) {
            throw new RuntimeException("The first account in system must be admin");
        }
        return accountInfo;
    }

    @Override
    public CommonResponse bindThirdParty(BindThirdPartyRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取当前用户did
        LoginUserBO loginUserBO =
                (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String did = loginUserBO.getEntity().getDid();
        // 使用的第三方账号类型
        String originThirdPartyName = request.getType().name();
        // 转化为大驼峰
        String thirdPartyName = originThirdPartyName.substring(0, 1).toUpperCase() + originThirdPartyName.substring(1);
        // 调用第三方账号api获取第三方账号id
        Method getThirdPartyAccountInfo = ThirdPartyUtils.class.getMethod("get" + thirdPartyName + "AccountInfo", String.class);
        JsonObject thirdPartyAccountInfo = (JsonObject) getThirdPartyAccountInfo.invoke(thirdPartyUtils, request.getCode());
        long thirdPartyId = thirdPartyAccountInfo.get("id").getAsLong();

        Assert.isNull(thirdPartyInfoMapper.searchOne(originThirdPartyName + "_id", thirdPartyId), "绑定失败: 该第三方账号已被绑定");

        if (null != thirdPartyInfoMapper.searchOneByDid(did)) {
            // 之前绑定过第三方账号
            thirdPartyInfoMapper.updateOne(did, originThirdPartyName + "_id", thirdPartyId);
        } else {
            // 之前没绑定过第三方账号
            thirdPartyInfoMapper.insertOne(did, originThirdPartyName + "_id", thirdPartyId);
        }

        return CommonResponse.success();
    }

    @Override
    public CommonResponse loginWithThirdParty(LoginWithThirdPartyRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 使用的第三方账号类型
        String originThirdPartyName = request.getType().name();
        // 转化为大驼峰
        String thirdPartyName = originThirdPartyName.substring(0, 1).toUpperCase() + originThirdPartyName.substring(1);
        // 调用第三方账号api获取第三方账号id
        Method getThirdPartyAccountInfo = ThirdPartyUtils.class.getMethod("get" + thirdPartyName + "AccountInfo", String.class);
        JsonObject thirdPartyAccountInfo = (JsonObject) getThirdPartyAccountInfo.invoke(thirdPartyUtils, request.getCode());
        long thirdPartyId = thirdPartyAccountInfo.get("id").getAsLong();

        ThirdPartyInfoEntity thirdPartyInfoEntity = thirdPartyInfoMapper.searchOne(originThirdPartyName + "_id", thirdPartyId);
        Assert.notNull(thirdPartyInfoEntity, "登陆失败: 该第三方账号并未绑定任何账号");

        AccountInfoEntity accountInfoEntity = accountInfoMapper.selectByDid(thirdPartyInfoEntity.getDid());
        Assert.notNull(accountInfoEntity, "登陆失败: 查询不到该用户");

        // 填充权限
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Objects.requireNonNull(AccountType.getAccountType(accountInfoEntity.getAccountType())).getRoleName());

        LoginUserBO loginInfoBo = new LoginUserBO(accountInfoEntity, permissionList);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginInfoBo, null, loginInfoBo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 生成token
        String token =
                JwtTokenHandler.TOKEN_PREFIX
                        + tokenHandler.generateToken(loginInfoBo.getEntity().getDid());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        // 填充使用第三方账号登录时前端缺失的用户名
        response.setUserName(accountInfoEntity.getUserName());
        response.setAccountType(String.valueOf(accountInfoEntity.getAccountType()));

        return CommonResponse.success(response);
    }

    @Override
    public void initAdmin() throws Exception {
        // Generation private key
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(sysConfig.getAdminPrivateKey());
        // Save to blockchain
        AccountContract accountContract =
                AccountContract.load(sysConfig.getContractConfig().getAccountContract(), client, keyPair);
        AccountContract.AccountData accountData =
                accountContract.getAccountByAddress(keyPair.getAddress());

        AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
        accountInfoEntity.setAccountType(AccountType.ADMIN.getRoleKey());
        accountInfoEntity.setDid(Base64.encode(accountData.did));
        accountInfoEntity.setPassword(bCryptPasswordEncoder.encode(sysConfig.getAdminPassword()));
        accountInfoEntity.setStatus(AccountStatus.Approved.ordinal());
        accountInfoEntity.setPrivateKey(keyPair.getHexPrivateKey());
        accountInfoEntity.setUserName(sysConfig.getAdminAccount());

        accountInfoMapper.insertAccount(accountInfoEntity);

        CompanyInfoEntity companyInfo = new CompanyInfoEntity();
        companyInfo.setCompanyName(sysConfig.getAdminCompany());
        companyInfo.setCompanyDesc(sysConfig.getAdminCompany());
        companyInfo.setCompanyCertType("TBD");
        companyInfo.setCompanyContact("TBD");
        companyInfo.setCompanyCertNo("TBD");
        companyInfo.setCompanyCertFileUri("TBD");
        companyInfo.setAccountId(accountInfoEntity.getPkId());
        companyInfoMapper.insertCompany(companyInfo);
    }
}
