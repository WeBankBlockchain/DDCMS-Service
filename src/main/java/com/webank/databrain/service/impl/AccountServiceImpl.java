package com.webank.databrain.service.impl;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.config.SysConfig;
import com.webank.databrain.dao.bc.contract.AccountModule;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.entity.CompanyInfoEntity;
import com.webank.databrain.dao.entity.PersonInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import com.webank.databrain.dao.mapper.CompanyInfoMapper;
import com.webank.databrain.dao.mapper.PersonInfoMapper;
import com.webank.databrain.enums.AccountStatus;
import com.webank.databrain.enums.AccountType;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.handler.JwtTokenHandler;
import com.webank.databrain.handler.ThreadLocalKeyPairHandler;
import com.webank.databrain.service.AccountService;
import com.webank.databrain.utils.BlockchainUtils;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.account.ApproveAccountRequest;
import com.webank.databrain.vo.request.account.LoginRequest;
import com.webank.databrain.vo.request.account.RegisterRequest;
import com.webank.databrain.vo.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Slf4j
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
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenHandler tokenHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResponse registerAccount(RegisterRequest request) throws TransactionException, JsonProcessingException {
        //Generation private key
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.generateRandomKeyPair();
        //Save to blockchain
        AccountModule accountContract = AccountModule.load(
                sysConfig.getContractConfig().getAccountContract(),
                client,
                keyPair);
        TransactionReceipt txReceipt = accountContract.register(BigInteger.valueOf(Long.parseLong(request.getAccountType())), cryptoSuite.hash(request.getUserName().getBytes()));
        byte[] didBytes = accountContract.getRegisterOutput(txReceipt).getValue1();
        BlockchainUtils.ensureTransactionSuccess(txReceipt, txDecoder);

        AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
        accountInfoEntity.setAccountType(Integer.parseInt(request.getAccountType()));
        accountInfoEntity.setDid(Base64.encode(didBytes));
        accountInfoEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        accountInfoEntity.setStatus(AccountStatus.Registered.ordinal());
        accountInfoEntity.setPrivateKey(keyPair.getHexPrivateKey());
        accountInfoEntity.setUserName(request.getUserName());

        accountInfoMapper.insertAccount(accountInfoEntity);

        CompanyInfoEntity companyInfo = objectMapper.readValue(request.getDetailJson(), CompanyInfoEntity.class);
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
        if (accountInfo.getAccountType() != AccountType.ADMIN.getRoleKey() &&
                accountInfo.getStatus() != AccountStatus.Approved.ordinal()) {
            return CommonResponse.error(CodeEnum.ACCOUNT_NOT_APPROVED);
        }

        try {
            // 使用auth进行用户认证
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
            // 调用UserDetailService实现类的认证方法
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 认证通过，则生成token，并返回
            LoginUserBO loginInfoBo = (LoginUserBO) authentication.getPrincipal();

            String token = JwtTokenHandler.TOKEN_PREFIX + tokenHandler.generateToken(loginInfoBo.getEntity().getDid());
            LoginResponse response = new LoginResponse();
            response.setToken(token);

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
        if (entity.getStatus() == AccountStatus.Approved.ordinal() || entity.getStatus() == AccountStatus.Denied.ordinal()) {
            return CommonResponse.error(CodeEnum.ACCOUNT_HAS_APPROVED);
        }
        byte[] didBytes = Base64.decode(did);
        CryptoKeyPair witnessKeyPair = this.witnessKeyPair;
        AccountModule accountModule = AccountModule.load(sysConfig.getContractConfig().getAccountContract(), client, witnessKeyPair);
        TransactionReceipt txReceipt = accountModule.approve(didBytes, approve);
        BlockchainUtils.ensureTransactionSuccess(txReceipt, txDecoder);
        //修改数据库状态
        AccountStatus status = approve ? AccountStatus.Approved : AccountStatus.Denied;
        accountInfoMapper.updateStatus(did, status.ordinal());
        return CommonResponse.success();
    }
}
