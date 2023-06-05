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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 启动时初始化admin账户
 */
@Component
@Slf4j
public class AdminRegisterTask implements ApplicationRunner {

    @Autowired
    private AccountService accountService;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountInfoEntity adminAccount = accountService.loadAdminAccount();
        if (adminAccount != null) {
            log.info("Admin already registered. ");
        }else {
            log.info("Initialize admin account");
            accountService.initAdmin();
        }
        MultipartFile f;
        f.get
        log.info("Initialize admin account register success ");
    }
}
