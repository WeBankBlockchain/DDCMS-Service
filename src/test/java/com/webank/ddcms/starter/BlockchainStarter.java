package com.webank.ddcms.starter;

import com.webank.ddcms.config.SysConfig;
import com.webank.ddcms.contracts.AccountContract;
import com.webank.ddcms.contracts.DataSchemaContract;
import com.webank.ddcms.contracts.ProductContract;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlockchainStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private CryptoKeyPair adminKeyPair;

    @Autowired
    private Client client;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            SysConfig.ContractConfig contractConfig = new SysConfig.ContractConfig();

            AccountContract accountContract = AccountContract.deploy(client, adminKeyPair);
            contractConfig.setAccountContract(accountContract.getContractAddress());

            ProductContract productContract = ProductContract.deploy(client, adminKeyPair, accountContract.getContractAddress());
            contractConfig.setProductContract(productContract.getContractAddress());

            DataSchemaContract dataSchemaModule = DataSchemaContract.deploy(client, adminKeyPair, accountContract.getContractAddress(), productContract.getContractAddress());
            contractConfig.setDataSchemaContract(dataSchemaModule.getContractAddress());
            System.out.println("合约地址替换为临时部署的");
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }


    }
}
