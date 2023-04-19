package com.webank.databrain.task;

import com.webank.databrain.config.SysConfig;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.entity.CompanyInfoEntity;
import com.webank.databrain.enums.AccountType;
import com.webank.databrain.service.AccountService;
import com.webank.databrain.vo.request.account.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.transaction.tools.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动时初始化admin账户
 */
@Component
@Slf4j
public class AdminRegisterTask implements ApplicationRunner {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountInfoEntity adminAccount = accountService.loadAdminAccount();
        if (adminAccount != null) {
            log.info("Admin already registered. ");
            return;
        }

        log.info("Initialize admin account");
        CompanyInfoEntity companyInfo = new CompanyInfoEntity();
        companyInfo.setCompanyName(sysConfig.getAdminCompany());
        companyInfo.setCompanyDesc(sysConfig.getAdminCompany());
        companyInfo.setCompanyCertType("TBD");
        companyInfo.setCompanyContact("TBD");
        companyInfo.setCompanyCertNo("TBD");
        companyInfo.setCompanyCertFileUri("TBD");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(sysConfig.getAdminAccount());
        request.setPassword(sysConfig.getAdminPassword());
        request.setAccountType(String.valueOf(AccountType.ADMIN.getRoleKey()));
        request.setHexPrivateKey(sysConfig.getAdminPrivateKey());
        request.setDetailJson(JsonUtils.toJson(companyInfo));
        accountService.registerAccount(request);
        log.info("Initialize admin account register success ");
    }
}
