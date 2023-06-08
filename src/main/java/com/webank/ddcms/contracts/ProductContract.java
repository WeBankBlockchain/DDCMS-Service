package com.webank.ddcms.contracts;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionEncoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.*;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ProductContract extends Contract {
  public static final String[] BINARY_ARRAY = {
    "608060405234801561001057600080fd5b50604051610cf7380380610cf783398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610c64806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80631ac90bfa1461005c5780632fef994e1461009f5780633a20e9df146100c25780639dccaf5f146100e2578063fd7f643d1461010a575b600080fd5b61008a61006a36600461099a565b600560209081526000928352604080842090915290825290205460ff1681565b60405190151581526020015b60405180910390f35b6100b26100ad3660046109bc565b610150565b6040516100969493929190610a1b565b6100d56100d0366004610a46565b610419565b6040516100969190610a5f565b6100f56100f0366004610a46565b6104e5565b60408051928352602083019190915201610096565b61011d610118366004610a46565b6107e8565b60405161009691908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b600080600080610161336002610858565b6000868152600160208190526040909120908101546101bd5760405162461bcd60e51b81526020600482015260136024820152721c1c9bd91d58dd081b9bdd08195e1a5cdd1959606a1b60448201526064015b60405180910390fd5b6000600282015460ff1660048111156101d8576101d86109f1565b1461021e5760405162461bcd60e51b8152602060048201526016602482015275496e76616c69642070726f647563742073746174757360501b60448201526064016101b4565b600087815260046020819052604080832083549151632e3a17fb60e21b8152339381019390935292916001600160a01b039091169063b8e85fec9060240160a060405180830381865afa158015610279573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061029d9190610a9d565b60208082015160008c8152600583526040808220838352909352919091205490985090915060ff16156103035760405162461bcd60e51b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101b4565b6002820154881561033957825461031b906001610b52565b8084556001840154909750955080871061033457600194505b610380565b60018084015461034891610b52565b60018085018290558454600386015490995091975060029161036a9190610b6a565b6103749190610b81565b86111561038057600294505b60028401805486919060ff191660018360048111156103a1576103a16109f1565b021790555060008a81526005602090815260408083208b845290915290819020805460ff19166001179055517f9ac8a9816adf17e701a8c64224935576790810da1ea7e678b6d393969c00906a90610404908c908b908d908c908c908c90610ba3565b60405180910390a15050505092959194509250565b61043a60408051606081018252600080825260208201819052909182015290565b60008281526001602081815260409283902083516060810185528154815292810154918301919091526002810154919290919083019060ff166004811115610484576104846109f1565b6004811115610495576104956109f1565b90525060208101519091506104e05760405162461bcd60e51b8152602060048201526011602482015270141c9bd91d58dd081b9bdd08195e1a5cdd607a1b60448201526064016101b4565b919050565b6000806104f3336001610858565b8261052f5760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101b4565b600083815260026020526040902054156105845760405162461bcd60e51b81526020600482015260166024820152750c8eae0d8d2c6c2e8ca40e0e4dec8eac6e840d0c2e6d60531b60448201526064016101b4565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa1580156105cd573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105f19190610a9d565b6020818101805160009081526003835260408082205483518251808701919091528083018290528251808203840181526060820180855281519188019190912060c0830185528c825295516080830190815260a090920185815286865260019788905293909420845181559051818701559151600283018054959a5096975090959294919390929160ff1990911690836004811115610692576106926109f1565b021790555050506000858152600260205260409020849055806106b481610be0565b602080850151600090815260039091526040808220839055905490516334b7dfab60e01b81529193506001600160a01b031691506334b7dfab906106fd90600290600401610bfb565b602060405180830381865afa15801561071a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061073e9190610c15565b60408051608081018252600080825260208201529194508101610762600286610b81565b61076d906001610b52565b815260209081018590526000868152600482526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518681529081018790527f230d813a6754febeccc450a72cb9a421228cff2e4f14527e50f0e7cf513659e7910160405180910390a15050915091565b6108136040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260046020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008054604051632e3a17fb60e21b81526001600160a01b0385811660048301529091169063b8e85fec9060240160a060405180830381865afa1580156108a3573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108c79190610a9d565b90506001816060015160038111156108e1576108e16109f1565b146109275760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101b4565b816003811115610939576109396109f1565b8160400151600381111561094f5761094f6109f1565b146109955760405162461bcd60e51b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101b4565b505050565b600080604083850312156109ad57600080fd5b50508035926020909101359150565b600080604083850312156109cf57600080fd5b82359150602083013580151581146109e657600080fd5b809150509250929050565b634e487b7160e01b600052602160045260246000fd5b60058110610a1757610a176109f1565b9052565b848152602081018490526040810183905260808101610a3d6060830184610a07565b95945050505050565b600060208284031215610a5857600080fd5b5035919050565b600060608201905082518252602083015160208301526040830151610a876040840182610a07565b5092915050565b8051600481106104e057600080fd5b600060a08284031215610aaf57600080fd5b60405160a0810181811067ffffffffffffffff82111715610ae057634e487b7160e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610afa57600080fd5b815260208381015190820152610b1260408401610a8e565b6040820152610b2360608401610a8e565b6060820152608083015160808201528091505092915050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610b6557610b65610b3c565b500190565b600082821015610b7c57610b7c610b3c565b500390565b600082610b9e57634e487b7160e01b600052601260045260246000fd5b500490565b600060c0820190508782528660208301528515156040830152846060830152836080830152610bd560a0830184610a07565b979650505050505050565b6000600019821415610bf457610bf4610b3c565b5060010190565b6020810160048310610c0f57610c0f6109f1565b91905290565b600060208284031215610c2757600080fd5b505191905056fea264697066735822122019b396689ebb4032a3006ff4ef78f3680de3d2cf42497c1041c60bcc39f4507664736f6c634300080b0033"
  };

  public static final String BINARY =
      org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

  public static final String[] SM_BINARY_ARRAY = {
    "608060405234801561001057600080fd5b50604051610d03380380610d0383398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610c70806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c8063023fe0121461005c578063080f34c31461008857806323defefe146100c657806334a249b3146100e6578063dc69aec91461012c575b600080fd5b61006f61006a3660046109a6565b610154565b60405161007f9493929190610a05565b60405180910390f35b6100b6610096366004610a30565b600560209081526000928352604080842090915290825290205460ff1681565b604051901515815260200161007f565b6100d96100d4366004610a52565b610420565b60405161007f9190610a6b565b6100f96100f4366004610a52565b6104ed565b60405161007f91908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b61013f61013a366004610a52565b61055d565b6040805192835260208301919091520161007f565b600080600080610165336002610862565b6000868152600160208190526040909120908101546101c257604051636381e58960e11b81526020600482015260136024820152721c1c9bd91d58dd081b9bdd08195e1a5cdd1959606a1b60448201526064015b60405180910390fd5b6000600282015460ff1660048111156101dd576101dd6109db565b1461022457604051636381e58960e11b8152602060048201526016602482015275496e76616c69642070726f647563742073746174757360501b60448201526064016101b9565b600087815260046020819052604080832083549151631637d7b960e11b8152339381019390935292916001600160a01b0390911690632c6faf729060240160a060405180830381865afa15801561027f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102a39190610aa9565b60208082015160008c8152600583526040808220838352909352919091205490985090915060ff161561030a57604051636381e58960e11b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101b9565b60028201548815610340578254610322906001610b5e565b8084556001840154909750955080871061033b57600194505b610387565b60018084015461034f91610b5e565b6001808501829055845460038601549099509197506002916103719190610b76565b61037b9190610b8d565b86111561038757600294505b60028401805486919060ff191660018360048111156103a8576103a86109db565b021790555060008a81526005602090815260408083208b845290915290819020805460ff19166001179055517f0b6308727823b88c4641f5585ee5313438ef92c7cd00c5ab4900ad5ffb10696c9061040b908c908b908d908c908c908c90610baf565b60405180910390a15050505092959194509250565b61044160408051606081018252600080825260208201819052909182015290565b60008281526001602081815260409283902083516060810185528154815292810154918301919091526002810154919290919083019060ff16600481111561048b5761048b6109db565b600481111561049c5761049c6109db565b90525060208101519091506104e857604051636381e58960e11b8152602060048201526011602482015270141c9bd91d58dd081b9bdd08195e1a5cdd607a1b60448201526064016101b9565b919050565b6105186040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260046020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008061056b336001610862565b826105a857604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101b9565b600083815260026020526040902054156105fe57604051636381e58960e11b81526020600482015260166024820152750c8eae0d8d2c6c2e8ca40e0e4dec8eac6e840d0c2e6d60531b60448201526064016101b9565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa158015610647573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061066b9190610aa9565b6020818101805160009081526003835260408082205483518251808701919091528083018290528251808203840181526060820180855281519188019190912060c0830185528c825295516080830190815260a090920185815286865260019788905293909420845181559051818701559151600283018054959a5096975090959294919390929160ff199091169083600481111561070c5761070c6109db565b0217905550505060008581526002602052604090208490558061072e81610bec565b60208085015160009081526003909152604080822083905590549051638c25a22560e01b81529193506001600160a01b03169150638c25a2259061077790600290600401610c07565b602060405180830381865afa158015610794573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107b89190610c21565b604080516080810182526000808252602082015291945081016107dc600286610b8d565b6107e7906001610b5e565b815260209081018590526000868152600482526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518681529081018790527f175e07a3cf31f68cbef842ccd950c943db09be45362fb2e12e1bdedbfebca8a8910160405180910390a15050915091565b60008054604051631637d7b960e11b81526001600160a01b03858116600483015290911690632c6faf729060240160a060405180830381865afa1580156108ad573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108d19190610aa9565b90506001816060015160038111156108eb576108eb6109db565b1461093257604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101b9565b816003811115610944576109446109db565b8160400151600381111561095a5761095a6109db565b146109a157604051636381e58960e11b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101b9565b505050565b600080604083850312156109b957600080fd5b82359150602083013580151581146109d057600080fd5b809150509250929050565b63b95aa35560e01b600052602160045260246000fd5b60058110610a0157610a016109db565b9052565b848152602081018490526040810183905260808101610a2760608301846109f1565b95945050505050565b60008060408385031215610a4357600080fd5b50508035926020909101359150565b600060208284031215610a6457600080fd5b5035919050565b600060608201905082518252602083015160208301526040830151610a9360408401826109f1565b5092915050565b8051600481106104e857600080fd5b600060a08284031215610abb57600080fd5b60405160a0810181811067ffffffffffffffff82111715610aec5763b95aa35560e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610b0657600080fd5b815260208381015190820152610b1e60408401610a9a565b6040820152610b2f60608401610a9a565b6060820152608083015160808201528091505092915050565b63b95aa35560e01b600052601160045260246000fd5b60008219821115610b7157610b71610b48565b500190565b600082821015610b8857610b88610b48565b500390565b600082610baa5763b95aa35560e01b600052601260045260246000fd5b500490565b600060c0820190508782528660208301528515156040830152846060830152836080830152610be160a08301846109f1565b979650505050505050565b6000600019821415610c0057610c00610b48565b5060010190565b6020810160048310610c1b57610c1b6109db565b91905290565b600060208284031215610c3357600080fd5b505191905056fea2646970667358221220e81120fcf54beb09d8e89b78237837a3424c3bc32f72311ef5845fdb6819d45b64736f6c634300080b0033"
  };

  public static final String SM_BINARY =
      org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

  public static final String[] ABI_ARRAY = {
    "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_accountContract\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"CreateProductEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"voterId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"name\":\"VoteProductEvent\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"}],\"name\":\"approveProduct\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"witnessDid\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"selector\":[804231502,37740562],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"createProduct\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"selector\":[2647437151,3697913545],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"}],\"name\":\"getProduct\",\"outputs\":[{\"components\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"ownerId\",\"type\":\"bytes32\"},{\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"status\",\"type\":\"uint8\"}],\"internalType\":\"struct ProductContract.ProductInfo\",\"name\":\"productInfo\",\"type\":\"tuple\"}],\"selector\":[975235551,601816830],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"}],\"name\":\"getVoteInfo\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"threshold\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"internalType\":\"struct ProductContract.VoteInfo\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[4252984381,883050931],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":5,\"value\":[0]},{\"kind\":3,\"slot\":5,\"value\":[1]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"productVoters\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[449383418,135214275],\"stateMutability\":\"view\",\"type\":\"function\"}]"
  };

  public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

  public static final String FUNC_APPROVEPRODUCT = "approveProduct";

  public static final String FUNC_CREATEPRODUCT = "createProduct";

  public static final String FUNC_GETPRODUCT = "getProduct";

  public static final String FUNC_GETVOTEINFO = "getVoteInfo";

  public static final String FUNC_PRODUCTVOTERS = "productVoters";

  public static final Event CREATEPRODUCTEVENT_EVENT =
      new Event(
          "CreateProductEvent",
          Arrays.asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));

  public static final Event VOTEPRODUCTEVENT_EVENT =
      new Event(
          "VoteProductEvent",
          Arrays.asList(
              new TypeReference<Bytes32>() {},
              new TypeReference<Bytes32>() {},
              new TypeReference<Bool>() {},
              new TypeReference<Uint256>() {},
              new TypeReference<Uint256>() {},
              new TypeReference<Uint8>() {}));

  protected ProductContract(String contractAddress, Client client, CryptoKeyPair credential) {
    super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
  }

  public static String getBinary(CryptoSuite cryptoSuite) {
    return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
  }

  public static String getABI() {
    return ABI;
  }

  public static ProductContract load(
      String contractAddress, Client client, CryptoKeyPair credential) {
    return new ProductContract(contractAddress, client, credential);
  }

  public static ProductContract deploy(
      Client client, CryptoKeyPair credential, String _accountContract) throws ContractException {
    byte[] encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_accountContract)));
    return deploy(
        ProductContract.class,
        client,
        credential,
        getBinary(client.getCryptoSuite()),
        getABI(),
        encodedConstructor,
        null);
  }

  public List<CreateProductEventEventResponse> getCreateProductEventEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(CREATEPRODUCTEVENT_EVENT, transactionReceipt);
    ArrayList<CreateProductEventEventResponse> responses =
        new ArrayList<CreateProductEventEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      CreateProductEventEventResponse typedResponse = new CreateProductEventEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.productId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public List<VoteProductEventEventResponse> getVoteProductEventEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(VOTEPRODUCTEVENT_EVENT, transactionReceipt);
    ArrayList<VoteProductEventEventResponse> responses =
        new ArrayList<VoteProductEventEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      VoteProductEventEventResponse typedResponse = new VoteProductEventEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.productId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.voterId = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
      typedResponse.agree = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
      typedResponse.agreeCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
      typedResponse.denyCount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
      typedResponse.afterStatus = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public TransactionReceipt approveProduct(byte[] productId, Boolean agree) {
    final Function function =
        new Function(
            FUNC_APPROVEPRODUCT,
            Arrays.asList(new Bytes32(productId), new Bool(agree)),
            Collections.emptyList(),
            0);
    return executeTransaction(function);
  }

  public String approveProduct(byte[] productId, Boolean agree, TransactionCallback callback) {
    final Function function =
        new Function(
            FUNC_APPROVEPRODUCT,
            Arrays.asList(new Bytes32(productId), new Bool(agree)),
            Collections.emptyList(),
            0);
    return asyncExecuteTransaction(function, callback);
  }

  public String getSignedTransactionForApproveProduct(byte[] productId, Boolean agree) {
    final Function function =
        new Function(
            FUNC_APPROVEPRODUCT,
            Arrays.asList(new Bytes32(productId), new Bool(agree)),
            Collections.emptyList(),
            0);
    return createSignedTransaction(function);
  }

  public Tuple2<byte[], Boolean> getApproveProductInput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getInput().substring(10);
    final Function function =
        new Function(
            FUNC_APPROVEPRODUCT,
            Arrays.asList(),
            Arrays.asList(new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple2<byte[], Boolean>(
        (byte[]) results.get(0).getValue(), (Boolean) results.get(1).getValue());
  }

  public Tuple4<byte[], BigInteger, BigInteger, BigInteger> getApproveProductOutput(
      TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getOutput();
    final Function function =
        new Function(
            FUNC_APPROVEPRODUCT,
            Arrays.asList(),
            Arrays.asList(
                new TypeReference<Bytes32>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<Uint8>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple4<byte[], BigInteger, BigInteger, BigInteger>(
        (byte[]) results.get(0).getValue(),
        (BigInteger) results.get(1).getValue(),
        (BigInteger) results.get(2).getValue(),
        (BigInteger) results.get(3).getValue());
  }

  public TransactionReceipt createProduct(byte[] hash) {
    final Function function =
        new Function(
            FUNC_CREATEPRODUCT, Arrays.asList(new Bytes32(hash)), Collections.emptyList(), 0);
    return executeTransaction(function);
  }

  public String createProduct(byte[] hash, TransactionCallback callback) {
    final Function function =
        new Function(
            FUNC_CREATEPRODUCT, Arrays.asList(new Bytes32(hash)), Collections.emptyList(), 0);
    return asyncExecuteTransaction(function, callback);
  }

  public String getSignedTransactionForCreateProduct(byte[] hash) {
    final Function function =
        new Function(
            FUNC_CREATEPRODUCT, Arrays.asList(new Bytes32(hash)), Collections.emptyList(), 0);
    return createSignedTransaction(function);
  }

  public Tuple1<byte[]> getCreateProductInput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getInput().substring(10);
    final Function function =
        new Function(
            FUNC_CREATEPRODUCT, Arrays.asList(), Arrays.asList(new TypeReference<Bytes32>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple1<byte[]>((byte[]) results.get(0).getValue());
  }

  public Tuple2<byte[], BigInteger> getCreateProductOutput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getOutput();
    final Function function =
        new Function(
            FUNC_CREATEPRODUCT,
            Arrays.asList(),
            Arrays.asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple2<byte[], BigInteger>(
        (byte[]) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
  }

  public ProductContract.ProductInfo getProduct(byte[] productId) throws ContractException {
    final Function function =
        new Function(
            FUNC_GETPRODUCT,
            Arrays.asList(new Bytes32(productId)),
            Arrays.asList(new TypeReference<ProductContract.ProductInfo>() {}));
    return executeCallWithSingleValueReturn(function, ProductContract.ProductInfo.class);
  }

  public ProductContract.VoteInfo getVoteInfo(byte[] productId) throws ContractException {
    final Function function =
        new Function(
            FUNC_GETVOTEINFO,
            Arrays.asList(new Bytes32(productId)),
            Arrays.asList(new TypeReference<ProductContract.VoteInfo>() {}));
    return executeCallWithSingleValueReturn(function, ProductContract.VoteInfo.class);
  }

  public Boolean productVoters(byte[] param0, byte[] param1) throws ContractException {
    final Function function =
        new Function(
            FUNC_PRODUCTVOTERS,
            Arrays.asList(new Bytes32(param0), new Bytes32(param1)),
            Arrays.asList(new TypeReference<Bool>() {}));
    return executeCallWithSingleValueReturn(function, Boolean.class);
  }

  public static class ProductInfo extends StaticStruct {
    public byte[] hash;

    public byte[] ownerId;

    public BigInteger status;

    public ProductInfo(Bytes32 hash, Bytes32 ownerId, Uint8 status) {
      super(hash, ownerId, status);
      this.hash = hash.getValue();
      this.ownerId = ownerId.getValue();
      this.status = status.getValue();
    }

    public ProductInfo(byte[] hash, byte[] ownerId, BigInteger status) {
      super(new Bytes32(hash), new Bytes32(ownerId), new Uint8(status));
      this.hash = hash;
      this.ownerId = ownerId;
      this.status = status;
    }
  }

  public static class VoteInfo extends StaticStruct {
    public BigInteger agreeCount;

    public BigInteger denyCount;

    public BigInteger threshold;

    public BigInteger witnessCount;

    public VoteInfo(
        Uint256 agreeCount, Uint256 denyCount, Uint256 threshold, Uint256 witnessCount) {
      super(agreeCount, denyCount, threshold, witnessCount);
      this.agreeCount = agreeCount.getValue();
      this.denyCount = denyCount.getValue();
      this.threshold = threshold.getValue();
      this.witnessCount = witnessCount.getValue();
    }

    public VoteInfo(
        BigInteger agreeCount,
        BigInteger denyCount,
        BigInteger threshold,
        BigInteger witnessCount) {
      super(
          new Uint256(agreeCount),
          new Uint256(denyCount),
          new Uint256(threshold),
          new Uint256(witnessCount));
      this.agreeCount = agreeCount;
      this.denyCount = denyCount;
      this.threshold = threshold;
      this.witnessCount = witnessCount;
    }
  }

  public static class CreateProductEventEventResponse {
    public TransactionReceipt.Logs log;

    public byte[] productId;

    public byte[] hash;
  }

  public static class VoteProductEventEventResponse {
    public TransactionReceipt.Logs log;

    public byte[] productId;

    public byte[] voterId;

    public Boolean agree;

    public BigInteger agreeCount;

    public BigInteger denyCount;

    public BigInteger afterStatus;
  }
}
