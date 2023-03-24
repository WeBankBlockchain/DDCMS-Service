package com.webank.databrain.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionEncoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.StaticStruct;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
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

@SuppressWarnings("unchecked")
public class ProductContract extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610cf6380380610cf683398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610c63806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80631ac90bfa1461005c5780632fef994e1461009f5780633a20e9df146100c25780639dccaf5f146100e2578063fd7f643d1461010a575b600080fd5b61008a61006a366004610999565b600560209081526000928352604080842090915290825290205460ff1681565b60405190151581526020015b60405180910390f35b6100b26100ad3660046109bb565b610150565b6040516100969493929190610a1a565b6100d56100d0366004610a45565b610403565b6040516100969190610a5e565b6100f56100f0366004610a45565b6104cf565b60408051928352602083019190915201610096565b61011d610118366004610a45565b6107d6565b60405161009691908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b600080600080600261016433826001610846565b6000878152600160208190526040909120908101546101c05760405162461bcd60e51b81526020600482015260136024820152721c1c9bd91d58dd081b9bdd08195e1a5cdd1959606a1b60448201526064015b60405180910390fd5b6000600282015460ff1660048111156101db576101db6109f0565b146102215760405162461bcd60e51b8152602060048201526016602482015275496e76616c69642070726f647563742073746174757360501b60448201526064016101b7565b600088815260046020819052604080832083549151632e3a17fb60e21b8152339381019390935292916001600160a01b039091169063b8e85fec9060240160a060405180830381865afa15801561027c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102a09190610a9c565b60208082015160008d8152600583526040808220838352909352919091205490995090915060ff16156103065760405162461bcd60e51b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101b7565b6002820154891561033457825461031e906001610b51565b808455975080881061032f57600195505b610369565b60018084015461034391610b51565b60018401819055600384015490975061035d908290610b69565b87111561036957600295505b60028401805487919060ff1916600183600481111561038a5761038a6109f0565b021790555060008b81526005602090815260408083208c845290915290819020805460ff19166001179055517f9ac8a9816adf17e701a8c64224935576790810da1ea7e678b6d393969c00906a906103ed908d908c908e908d908d908d90610b80565b60405180910390a1505050505092959194509250565b61042460408051606081018252600080825260208201819052909182015290565b60008281526001602081815260409283902083516060810185528154815292810154918301919091526002810154919290919083019060ff16600481111561046e5761046e6109f0565b600481111561047f5761047f6109f0565b90525060208101519091506104ca5760405162461bcd60e51b8152602060048201526011602482015270141c9bd91d58dd081b9bdd08195e1a5cdd607a1b60448201526064016101b7565b919050565b60008060016104e033826001610846565b8361051c5760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101b7565b600084815260026020526040902054156105715760405162461bcd60e51b81526020600482015260166024820152750c8eae0d8d2c6c2e8ca40e0e4dec8eac6e840d0c2e6d60531b60448201526064016101b7565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa1580156105ba573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105de9190610a9c565b6020818101805160009081526003835260408082205483518251808701919091528083018290528251808203840181526060820180855281519188019190912060c0830185528d825295516080830190815260a090920185815286865260019788905293909420845181559051818701559151600283018054959b5096975090959294919390929160ff199091169083600481111561067f5761067f6109f0565b021790555050506000868152600260205260409020859055806106a181610bbd565b602080850151600090815260039091526040808220839055905490516334b7dfab60e01b81529193506001600160a01b031691506334b7dfab906106ea90600290600401610bd8565b602060405180830381865afa158015610707573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061072b9190610bf2565b6040805160808101825260008082526020820152919550810161074f600287610c0b565b61075a906001610b51565b815260209081018690526000878152600482526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018890527f230d813a6754febeccc450a72cb9a421228cff2e4f14527e50f0e7cf513659e7910160405180910390a1505050915091565b6108016040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260046020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008054604051632e3a17fb60e21b81526001600160a01b0386811660048301529091169063b8e85fec9060240160a060405180830381865afa158015610891573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108b59190610a9c565b90508160038111156108c9576108c96109f0565b816060015160038111156108df576108df6109f0565b146109255760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101b7565b826003811115610937576109376109f0565b8160400151600381111561094d5761094d6109f0565b146109935760405162461bcd60e51b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101b7565b50505050565b600080604083850312156109ac57600080fd5b50508035926020909101359150565b600080604083850312156109ce57600080fd5b82359150602083013580151581146109e557600080fd5b809150509250929050565b634e487b7160e01b600052602160045260246000fd5b60058110610a1657610a166109f0565b9052565b848152602081018490526040810183905260808101610a3c6060830184610a06565b95945050505050565b600060208284031215610a5757600080fd5b5035919050565b600060608201905082518252602083015160208301526040830151610a866040840182610a06565b5092915050565b8051600481106104ca57600080fd5b600060a08284031215610aae57600080fd5b60405160a0810181811067ffffffffffffffff82111715610adf57634e487b7160e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610af957600080fd5b815260208381015190820152610b1160408401610a8d565b6040820152610b2260608401610a8d565b6060820152608083015160808201528091505092915050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610b6457610b64610b3b565b500190565b600082821015610b7b57610b7b610b3b565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610bb260a0830184610a06565b979650505050505050565b6000600019821415610bd157610bd1610b3b565b5060010190565b6020810160048310610bec57610bec6109f0565b91905290565b600060208284031215610c0457600080fd5b5051919050565b600082610c2857634e487b7160e01b600052601260045260246000fd5b50049056fea26469706673582212204f662530ac2e7d86d910d5c2ffde18baaa668a2b75a840f98c17331a8c90508264736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610d02380380610d0283398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610c6f806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c8063023fe0121461005c578063080f34c31461008857806323defefe146100c657806334a249b3146100e6578063dc69aec91461012c575b600080fd5b61006f61006a3660046109a5565b610154565b60405161007f9493929190610a04565b60405180910390f35b6100b6610096366004610a2f565b600560209081526000928352604080842090915290825290205460ff1681565b604051901515815260200161007f565b6100d96100d4366004610a51565b61040a565b60405161007f9190610a6a565b6100f96100f4366004610a51565b6104d7565b60405161007f91908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b61013f61013a366004610a51565b610547565b6040805192835260208301919091520161007f565b600080600080600261016833826001610850565b6000878152600160208190526040909120908101546101c557604051636381e58960e11b81526020600482015260136024820152721c1c9bd91d58dd081b9bdd08195e1a5cdd1959606a1b60448201526064015b60405180910390fd5b6000600282015460ff1660048111156101e0576101e06109da565b1461022757604051636381e58960e11b8152602060048201526016602482015275496e76616c69642070726f647563742073746174757360501b60448201526064016101bc565b600088815260046020819052604080832083549151631637d7b960e11b8152339381019390935292916001600160a01b0390911690632c6faf729060240160a060405180830381865afa158015610282573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102a69190610aa8565b60208082015160008d8152600583526040808220838352909352919091205490995090915060ff161561030d57604051636381e58960e11b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101bc565b6002820154891561033b578254610325906001610b5d565b808455975080881061033657600195505b610370565b60018084015461034a91610b5d565b600184018190556003840154909750610364908290610b75565b87111561037057600295505b60028401805487919060ff19166001836004811115610391576103916109da565b021790555060008b81526005602090815260408083208c845290915290819020805460ff19166001179055517f0b6308727823b88c4641f5585ee5313438ef92c7cd00c5ab4900ad5ffb10696c906103f4908d908c908e908d908d908d90610b8c565b60405180910390a1505050505092959194509250565b61042b60408051606081018252600080825260208201819052909182015290565b60008281526001602081815260409283902083516060810185528154815292810154918301919091526002810154919290919083019060ff166004811115610475576104756109da565b6004811115610486576104866109da565b90525060208101519091506104d257604051636381e58960e11b8152602060048201526011602482015270141c9bd91d58dd081b9bdd08195e1a5cdd607a1b60448201526064016101bc565b919050565b6105026040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260046020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b600080600161055833826001610850565b8361059557604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101bc565b600084815260026020526040902054156105eb57604051636381e58960e11b81526020600482015260166024820152750c8eae0d8d2c6c2e8ca40e0e4dec8eac6e840d0c2e6d60531b60448201526064016101bc565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa158015610634573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106589190610aa8565b6020818101805160009081526003835260408082205483518251808701919091528083018290528251808203840181526060820180855281519188019190912060c0830185528d825295516080830190815260a090920185815286865260019788905293909420845181559051818701559151600283018054959b5096975090959294919390929160ff19909116908360048111156106f9576106f96109da565b0217905550505060008681526002602052604090208590558061071b81610bc9565b60208085015160009081526003909152604080822083905590549051638c25a22560e01b81529193506001600160a01b03169150638c25a2259061076490600290600401610be4565b602060405180830381865afa158015610781573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107a59190610bfe565b604080516080810182526000808252602082015291955081016107c9600287610c17565b6107d4906001610b5d565b815260209081018690526000878152600482526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018890527f175e07a3cf31f68cbef842ccd950c943db09be45362fb2e12e1bdedbfebca8a8910160405180910390a1505050915091565b60008054604051631637d7b960e11b81526001600160a01b03868116600483015290911690632c6faf729060240160a060405180830381865afa15801561089b573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108bf9190610aa8565b90508160038111156108d3576108d36109da565b816060015160038111156108e9576108e96109da565b1461093057604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101bc565b826003811115610942576109426109da565b81604001516003811115610958576109586109da565b1461099f57604051636381e58960e11b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101bc565b50505050565b600080604083850312156109b857600080fd5b82359150602083013580151581146109cf57600080fd5b809150509250929050565b63b95aa35560e01b600052602160045260246000fd5b60058110610a0057610a006109da565b9052565b848152602081018490526040810183905260808101610a2660608301846109f0565b95945050505050565b60008060408385031215610a4257600080fd5b50508035926020909101359150565b600060208284031215610a6357600080fd5b5035919050565b600060608201905082518252602083015160208301526040830151610a9260408401826109f0565b5092915050565b8051600481106104d257600080fd5b600060a08284031215610aba57600080fd5b60405160a0810181811067ffffffffffffffff82111715610aeb5763b95aa35560e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610b0557600080fd5b815260208381015190820152610b1d60408401610a99565b6040820152610b2e60608401610a99565b6060820152608083015160808201528091505092915050565b63b95aa35560e01b600052601160045260246000fd5b60008219821115610b7057610b70610b47565b500190565b600082821015610b8757610b87610b47565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610bbe60a08301846109f0565b979650505050505050565b6000600019821415610bdd57610bdd610b47565b5060010190565b6020810160048310610bf857610bf86109da565b91905290565b600060208284031215610c1057600080fd5b5051919050565b600082610c345763b95aa35560e01b600052601260045260246000fd5b50049056fea26469706673582212206fbf982ba45dc9c2595df1d192da8dba88328e9ab1632490c7407af0bdd4c0c364736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_accountContract\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"CreateProductEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"voterId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"name\":\"VoteProductEvent\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"}],\"name\":\"approveProduct\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"witnessDid\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"selector\":[804231502,37740562],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"createProduct\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"selector\":[2647437151,3697913545],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"}],\"name\":\"getProduct\",\"outputs\":[{\"components\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"ownerId\",\"type\":\"bytes32\"},{\"internalType\":\"enum ProductContract.ProductStatus\",\"name\":\"status\",\"type\":\"uint8\"}],\"internalType\":\"struct ProductContract.ProductInfo\",\"name\":\"productInfo\",\"type\":\"tuple\"}],\"selector\":[975235551,601816830],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"}],\"name\":\"getVoteInfo\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"threshold\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"internalType\":\"struct ProductContract.VoteInfo\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[4252984381,883050931],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":5,\"value\":[0]},{\"kind\":3,\"slot\":5,\"value\":[1]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"productVoters\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[449383418,135214275],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_APPROVEPRODUCT = "approveProduct";

    public static final String FUNC_CREATEPRODUCT = "createProduct";

    public static final String FUNC_GETPRODUCT = "getProduct";

    public static final String FUNC_GETVOTEINFO = "getVoteInfo";

    public static final String FUNC_PRODUCTVOTERS = "productVoters";

    public static final Event CREATEPRODUCTEVENT_EVENT = new Event("CreateProductEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event VOTEPRODUCTEVENT_EVENT = new Event("VoteProductEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
    ;

    protected ProductContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<CreateProductEventEventResponse> getCreateProductEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEPRODUCTEVENT_EVENT, transactionReceipt);
        ArrayList<CreateProductEventEventResponse> responses = new ArrayList<CreateProductEventEventResponse>(valueList.size());
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
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VOTEPRODUCTEVENT_EVENT, transactionReceipt);
        ArrayList<VoteProductEventEventResponse> responses = new ArrayList<VoteProductEventEventResponse>(valueList.size());
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
        final Function function = new Function(
                FUNC_APPROVEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(productId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String approveProduct(byte[] productId, Boolean agree, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(productId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApproveProduct(byte[] productId, Boolean agree) {
        final Function function = new Function(
                FUNC_APPROVEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(productId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple2<byte[], Boolean> getApproveProductInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVEPRODUCT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], Boolean>(

                (byte[]) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public Tuple4<byte[], BigInteger, BigInteger, BigInteger> getApproveProductOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_APPROVEPRODUCT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<byte[], BigInteger, BigInteger, BigInteger>(

                (byte[]) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue()
                );
    }

    public TransactionReceipt createProduct(byte[] hash) {
        final Function function = new Function(
                FUNC_CREATEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String createProduct(byte[] hash, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateProduct(byte[] hash) {
        final Function function = new Function(
                FUNC_CREATEPRODUCT, 
                Arrays.<Type>asList(new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getCreateProductInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATEPRODUCT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple2<byte[], BigInteger> getCreateProductOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_CREATEPRODUCT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], BigInteger>(

                (byte[]) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public ProductContract.ProductInfo getProduct(byte[] productId) throws ContractException {
        final Function function = new Function(FUNC_GETPRODUCT, 
                Arrays.<Type>asList(new Bytes32(productId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<ProductContract.ProductInfo>() {}));
        return executeCallWithSingleValueReturn(function, ProductContract.ProductInfo.class);
    }

    public ProductContract.VoteInfo getVoteInfo(byte[] productId) throws ContractException {
        final Function function = new Function(FUNC_GETVOTEINFO, 
                Arrays.<Type>asList(new Bytes32(productId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<ProductContract.VoteInfo>() {}));
        return executeCallWithSingleValueReturn(function, ProductContract.VoteInfo.class);
    }

    public Boolean productVoters(byte[] param0, byte[] param1) throws ContractException {
        final Function function = new Function(FUNC_PRODUCTVOTERS, 
                Arrays.<Type>asList(new Bytes32(param0),
                new Bytes32(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static ProductContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new ProductContract(contractAddress, client, credential);
    }

    public static ProductContract deploy(Client client, CryptoKeyPair credential,
            String _accountContract) throws ContractException {
        byte[] encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_accountContract)));
        return deploy(ProductContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), encodedConstructor, null);
    }

    public static class ProductInfo extends StaticStruct {
        public byte[] hash;

        public byte[] ownerId;

        public BigInteger status;

        public ProductInfo(Bytes32 hash, Bytes32 ownerId, Uint8 status) {
            super(hash,ownerId,status);
            this.hash = hash.getValue();
            this.ownerId = ownerId.getValue();
            this.status = status.getValue();
        }

        public ProductInfo(byte[] hash, byte[] ownerId, BigInteger status) {
            super(new Bytes32(hash),new Bytes32(ownerId),new Uint8(status));
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

        public VoteInfo(Uint256 agreeCount, Uint256 denyCount, Uint256 threshold,
                Uint256 witnessCount) {
            super(agreeCount,denyCount,threshold,witnessCount);
            this.agreeCount = agreeCount.getValue();
            this.denyCount = denyCount.getValue();
            this.threshold = threshold.getValue();
            this.witnessCount = witnessCount.getValue();
        }

        public VoteInfo(BigInteger agreeCount, BigInteger denyCount,
                BigInteger threshold, BigInteger witnessCount) {
            super(new Uint256(agreeCount),new Uint256(denyCount),new Uint256(threshold),new Uint256(witnessCount));
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
