package com.webank.databrain.contracts;

import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;

//部署智能合约
public class Deployer {

//    public static void main(String[] args) throws Exception{
//        //Load config
//        Client client = BcosSDK.build("config/config.toml").getClient();
//        System.out.println(client);
//        //Admin key
//        String adminPrivateKey = "11afa82f974469792aa0172931b813d4fc7dd9177f3211779efc5f955d5e480f";
//        CryptoSuite cryptoSuite = new CryptoSuite(0);
//        CryptoKeyPair witness = cryptoSuite.loadKeyPair(adminPrivateKey);
//
//        //Deploy Account Module
//        AccountContract accountContract = AccountContract.deploy(client, witness);
//        System.out.println("Account contract address: "+accountContract.getContractAddress());
//
//        //Deploy Product Module
//        ProductContract productContract = ProductContract.deploy(client, witness, accountContract.getContractAddress());
//        System.out.println("Product contract address: "+productContract.getContractAddress());
//
//        //Deploy Data Schema Module
//        DataSchemaContract dataSchemaModule = DataSchemaContract.deploy(client, witness,  accountContract.getContractAddress(), productContract.getContractAddress());
//        System.out.println("Data Schema contract address: "+dataSchemaModule.getContractAddress());
//    }

    public static void main(String[] args) throws Exception{

        //Load config
        Client client = BcosSDK.build("config/config.toml").getClient();
        System.out.println(client);
        //Witness key
        String adminPrivateKey = "11afa82f974469792aa0172931b813d4fc7dd9177f3211779efc5f955d5e480f";
        CryptoSuite cryptoSuite = new CryptoSuite(0);
        CryptoKeyPair witness = cryptoSuite.loadKeyPair(adminPrivateKey);

        //Deploy Data Schema Module
        DataSchemaContract dataSchemaModule = DataSchemaContract.deploy(client, witness, "0x696f782b4f34d31ee6edf47ff0ce92bb50197f05","0xf1091018ea5d3a50d0c4e459cf7e232016f6341d");
        System.out.println("Data Schema contract address: "+dataSchemaModule.getContractAddress());
    }
}
