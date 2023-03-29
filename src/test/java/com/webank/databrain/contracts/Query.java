package com.webank.databrain.contracts;

import com.webank.databrain.utils.BlockchainUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.v3.transaction.tools.JsonUtils;
import org.fisco.bcos.sdk.v3.utils.Hex;

public class Query {
    public static void main(String[] args) throws Exception{
//        test();
        BcosSDK sdk = BcosSDK.build("config/config.toml");
        Client client = sdk.getClient();

        CryptoKeyPair keyPair = client.getCryptoSuite().loadKeyPair("d8791b279e84ae1bb130e4ce8ecdc6802f6e636ab04b3de7fa53a730f86d3a80");
        DataSchemaContract contract = DataSchemaContract.load("0xb406218430b053754a54355b08a8cd622322cdf4", client, keyPair);
//        DataSchemaContract contract = DataSchemaContract.load("0x46abd0bec4098ad19746272fdc62c28bf2236cbe", client, keyPair);
//
        TransactionReceipt receipt = contract.createDataSchema(Hex.decode("0x1b19a225befd1cec35f1f3bbb3e139a37e53df67adf364e308dcf4aab8e5a015"), Hex.decode("0x0b19a225befd1cec35f1f3bbb3e139a37e53df67adf364e308dcf4aab8e5a015"));
        BlockchainUtils.ensureTransactionSuccess(receipt, new TransactionDecoderService(new CryptoSuite(0), false));
    }

    public static void test() throws Exception{
        BcosSDK sdk = BcosSDK.build("config/config.toml");
        Client client = sdk.getClient();

        CryptoKeyPair keyPair = client.getCryptoSuite().loadKeyPair("d8791b279e84ae1bb130e4ce8ecdc6802f6e636ab04b3de7fa53a730f86d3a80");
        AccountContract contract = AccountContract.load("0x696f782b4f34d31ee6edf47ff0ce92bb50197f05", client, keyPair);
        System.out.println(Hex.toHexString(contract.getAccountByAddress(keyPair.getAddress()).did));

    }
}
