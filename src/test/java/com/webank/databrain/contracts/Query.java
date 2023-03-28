package com.webank.databrain.contracts;

import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.transaction.tools.JsonUtils;
import org.fisco.bcos.sdk.v3.utils.Hex;

public class Query {
    public static void main(String[] args) throws Exception{
        BcosSDK sdk = BcosSDK.build("config/config.toml");
        Client client = sdk.getClient();
        String address = "0x46abd0bec4098ad19746272fdc62c28bf2236cbe";
        String dataSchemaId = "0x7e301a447e6a841c4aed479a8d3aeae637e74158c6eb8584c787a76ef0e586e9";
        DataSchemaContract contract = DataSchemaContract.load(address, client, client.getCryptoSuite().getCryptoKeyPair());
        DataSchemaContract.VoteInfo voteInfo = contract.getVoteInfo(Hex.decode(dataSchemaId));
        System.out.println(voteInfo.agreeCount);
        System.out.println(voteInfo.denyCount);
        System.out.println(voteInfo.threshold);
        System.out.println(voteInfo.witnessCount);
    }
}
