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
public class DataSchemaContract extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f1a380380610f1a83398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e5c806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80632a39a9b41461005157806339d75b771461007d57806387061ac31461009d578063fd7f643d146100c5575b600080fd5b61006461005f366004610b10565b61010b565b6040516100749493929190610b6f565b60405180910390f35b61009061008b366004610b9a565b6104d3565b6040516100749190610bb3565b6100b06100ab366004610bec565b6105b3565b60408051928352602083019190915201610074565b6100d86100d3366004610b9a565b610aa0565b60405161007491908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b60008054604051632e3a17fb60e21b815233600482015282918291829182916001600160a01b039091169063b8e85fec9060240160a060405180830381865afa15801561015c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101809190610c1e565b905060018160600151600381111561019a5761019a610b45565b146101e55760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b6002816040015160038111156101fd576101fd610b45565b146102435760405162461bcd60e51b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101dc565b866102895760405162461bcd60e51b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b60448201526064016101dc565b600087815260026020526040812090600382015460ff1660048111156102b1576102b1610b45565b146102fe5760405162461bcd60e51b815260206004820152601a60248201527f496e76616c6964206461746120736368656d612073746174757300000000000060448201526064016101dc565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa158015610347573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061036b9190610c1e565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff16156103d75760405162461bcd60e51b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101dc565b600281015460038201548a1561040a5782546103f4906001610cd6565b808455985081891061040557600196505b610438565b60018084015461041991610cd6565b60018401819055975061042c8282610cee565b88111561043857600296505b60038501805488919060ff1916600183600481111561045957610459610b45565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f29fefc3e7b6df316e91b9a4f9d2336b0c9ac71f4eede613e4286eb010537ea83906104bc908e908d908f908e908e908e90610d05565b60405180910390a150505050505092959194509250565b6104fb6040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff16600481111561054e5761054e610b45565b600481111561055f5761055f610b45565b90525060208101519091506105ae5760405162461bcd60e51b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b60448201526064016101dc565b919050565b600080836105f25760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101dc565b826106335760405162461bcd60e51b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b60448201526064016101dc565b6000848152600360205260409020541561068f5760405162461bcd60e51b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d61206861736800000000000060448201526064016101dc565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa1580156106d8573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106fc9190610c1e565b600154604051633a20e9df60e01b8152600481018790529192506000916001600160a01b0390911690633a20e9df90602401606060405180830381865afa15801561074b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061076f9190610d42565b905060018160400151600481111561078957610789610b45565b146107cd5760405162461bcd60e51b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b60448201526064016101dc565b816020015181602001511461081c5760405162461bcd60e51b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b60448201526064016101dc565b60018260600151600381111561083457610834610b45565b146108765760405162461bcd60e51b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b60448201526064016101dc565b6000858152600460205260409020546108b68682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b9450604051806080016040528088815260200184602001518152602001878152602001600060048111156108ec576108ec610b45565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083600481111561094a5761094a610b45565b0217905550505060008781526003602052604090208590558061096c81610db6565b6000888152600460208190526040808320849055915491516334b7dfab60e01b81529294506001600160a01b0390911692506334b7dfab916109b19160029101610dd1565b602060405180830381865afa1580156109ce573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109f29190610deb565b604080516080810182526000808252602082015291955081016002610a18876001610cd6565b610a229190610e04565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527fc40138f2abc0799418ac7cc8ff6f08368f39a551d6caa1fa343b6c08aa25bb51910160405180910390a15050509250929050565b610acb6040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008060408385031215610b2357600080fd5b8235915060208301358015158114610b3a57600080fd5b809150509250929050565b634e487b7160e01b600052602160045260246000fd5b60058110610b6b57610b6b610b45565b9052565b848152602081018490526040810183905260808101610b916060830184610b5b565b95945050505050565b600060208284031215610bac57600080fd5b5035919050565b60006080820190508251825260208301516020830152604083015160408301526060830151610be56060840182610b5b565b5092915050565b60008060408385031215610bff57600080fd5b50508035926020909101359150565b60048110610c1b57600080fd5b50565b600060a08284031215610c3057600080fd5b60405160a0810181811067ffffffffffffffff82111715610c6157634e487b7160e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610c7b57600080fd5b8152602083810151908201526040830151610c9581610c0e565b60408201526060830151610ca881610c0e565b60608201526080928301519281019290925250919050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610ce957610ce9610cc0565b500190565b600082821015610d0057610d00610cc0565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d3760a0830184610b5b565b979650505050505050565b600060608284031215610d5457600080fd5b6040516060810181811067ffffffffffffffff82111715610d8557634e487b7160e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610daa57600080fd5b60408201529392505050565b6000600019821415610dca57610dca610cc0565b5060010190565b6020810160048310610de557610de5610b45565b91905290565b600060208284031215610dfd57600080fd5b5051919050565b600082610e2157634e487b7160e01b600052601260045260246000fd5b50049056fea2646970667358221220833dede7b8b748f59432dd9b2255bf09472bdedca100f67a70ab721c821bf7b964736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f26380380610f2683398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e68806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806334a249b314610051578063a0ccc927146100a0578063aa61cf72146100c3578063f8da933a146100eb575b600080fd5b61006461005f366004610b1c565b61010b565b60405161009791908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b60405180910390f35b6100b36100ae366004610b35565b61017b565b6040516100979493929190610b94565b6100d66100d1366004610bbf565b610548565b60408051928352602083019190915201610097565b6100fe6100f9366004610b1c565b610a3b565b6040516100979190610be1565b6101366040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008054604051631637d7b960e11b815233600482015282918291829182916001600160a01b0390911690632c6faf729060240160a060405180830381865afa1580156101cc573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101f09190610c2a565b905060018160600151600381111561020a5761020a610b6a565b1461025657604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b60028160400151600381111561026e5761026e610b6a565b146102b557604051636381e58960e11b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b604482015260640161024d565b866102fc57604051636381e58960e11b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b604482015260640161024d565b600087815260026020526040812090600382015460ff16600481111561032457610324610b6a565b1461037257604051636381e58960e11b815260206004820152601a60248201527f496e76616c6964206461746120736368656d6120737461747573000000000000604482015260640161024d565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa1580156103bb573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103df9190610c2a565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff161561044c57604051636381e58960e11b815260206004820152600e60248201526d4475706c696361746520766f746560901b604482015260640161024d565b600281015460038201548a1561047f578254610469906001610ce2565b808455985081891061047a57600196505b6104ad565b60018084015461048e91610ce2565b6001840181905597506104a18282610cfa565b8811156104ad57600296505b60038501805488919060ff191660018360048111156104ce576104ce610b6a565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f663a09eb7428d9225d1d2c815ea18d3745064b851ad06b66f4f47a9aba859b6f90610531908e908d908f908e908e908e90610d11565b60405180910390a150505050505092959194509250565b6000808361058857604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b604482015260640161024d565b826105ca57604051636381e58960e11b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b604482015260640161024d565b6000848152600360205260409020541561062757604051636381e58960e11b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d612068617368000000000000604482015260640161024d565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa158015610670573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106949190610c2a565b6001546040516311ef7f7f60e11b8152600481018790529192506000916001600160a01b03909116906323defefe90602401606060405180830381865afa1580156106e3573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107079190610d4e565b905060018160400151600481111561072157610721610b6a565b1461076657604051636381e58960e11b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b604482015260640161024d565b81602001518160200151146107b657604051636381e58960e11b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b604482015260640161024d565b6001826060015160038111156107ce576107ce610b6a565b1461081157604051636381e58960e11b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b604482015260640161024d565b6000858152600460205260409020546108518682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b94506040518060800160405280888152602001846020015181526020018781526020016000600481111561088757610887610b6a565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff021916908360048111156108e5576108e5610b6a565b0217905550505060008781526003602052604090208590558061090781610dc2565b600088815260046020819052604080832084905591549151638c25a22560e01b81529294506001600160a01b039091169250638c25a2259161094c9160029101610ddd565b602060405180830381865afa158015610969573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061098d9190610df7565b6040805160808101825260008082526020820152919550810160026109b3876001610ce2565b6109bd9190610e10565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527f05fd4f713f9fdded770a2d1b269da2d71a761e7471fad40cc24b362525998a50910160405180910390a15050509250929050565b610a636040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff166004811115610ab657610ab6610b6a565b6004811115610ac757610ac7610b6a565b9052506020810151909150610b1757604051636381e58960e11b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b604482015260640161024d565b919050565b600060208284031215610b2e57600080fd5b5035919050565b60008060408385031215610b4857600080fd5b8235915060208301358015158114610b5f57600080fd5b809150509250929050565b63b95aa35560e01b600052602160045260246000fd5b60058110610b9057610b90610b6a565b9052565b848152602081018490526040810183905260808101610bb66060830184610b80565b95945050505050565b60008060408385031215610bd257600080fd5b50508035926020909101359150565b60006080820190508251825260208301516020830152604083015160408301526060830151610c136060840182610b80565b5092915050565b60048110610c2757600080fd5b50565b600060a08284031215610c3c57600080fd5b60405160a0810181811067ffffffffffffffff82111715610c6d5763b95aa35560e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610c8757600080fd5b8152602083810151908201526040830151610ca181610c1a565b60408201526060830151610cb481610c1a565b60608201526080928301519281019290925250919050565b63b95aa35560e01b600052601160045260246000fd5b60008219821115610cf557610cf5610ccc565b500190565b600082821015610d0c57610d0c610ccc565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d4360a0830184610b80565b979650505050505050565b600060608284031215610d6057600080fd5b6040516060810181811067ffffffffffffffff82111715610d915763b95aa35560e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610db657600080fd5b60408201529392505050565b6000600019821415610dd657610dd6610ccc565b5060010190565b6020810160048310610df157610df1610b6a565b91905290565b600060208284031215610e0957600080fd5b5051919050565b600082610e2d5763b95aa35560e01b600052601260045260246000fd5b50049056fea2646970667358221220078f1bff38cdc2bd56a6886b69d21cacee5b51d43936ebcc68288d58dc8d794764736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_accountContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_productContract\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"CreateDataSchemaEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"voterId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"enum DataSchemaContract.DataSchemaStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"name\":\"VoteDataSchemaEvent\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"},{\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"}],\"name\":\"approveDataSchema\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"witnessDid\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"enum DataSchemaContract.DataSchemaStatus\",\"name\":\"afterStatus\",\"type\":\"uint8\"}],\"selector\":[708422068,2697775399],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"}],\"name\":\"createDataSchema\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"selector\":[2265324227,2858536818],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":2,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"}],\"name\":\"getDataSchema\",\"outputs\":[{\"components\":[{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"ownerId\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"productId\",\"type\":\"bytes32\"},{\"internalType\":\"enum DataSchemaContract.DataSchemaStatus\",\"name\":\"status\",\"type\":\"uint8\"}],\"internalType\":\"struct DataSchemaContract.DataSchemaInfo\",\"name\":\"dataSchema\",\"type\":\"tuple\"}],\"selector\":[970414967,4175074106],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":5,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"dataSchemaId\",\"type\":\"bytes32\"}],\"name\":\"getVoteInfo\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"agreeCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"denyCount\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"threshold\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"witnessCount\",\"type\":\"uint256\"}],\"internalType\":\"struct DataSchemaContract.VoteInfo\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[4252984381,883050931],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_APPROVEDATASCHEMA = "approveDataSchema";

    public static final String FUNC_CREATEDATASCHEMA = "createDataSchema";

    public static final String FUNC_GETDATASCHEMA = "getDataSchema";

    public static final String FUNC_GETVOTEINFO = "getVoteInfo";

    public static final Event CREATEDATASCHEMAEVENT_EVENT = new Event("CreateDataSchemaEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event VOTEDATASCHEMAEVENT_EVENT = new Event("VoteDataSchemaEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
    ;

    protected DataSchemaContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<CreateDataSchemaEventEventResponse> getCreateDataSchemaEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEDATASCHEMAEVENT_EVENT, transactionReceipt);
        ArrayList<CreateDataSchemaEventEventResponse> responses = new ArrayList<CreateDataSchemaEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CreateDataSchemaEventEventResponse typedResponse = new CreateDataSchemaEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dataSchemaId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<VoteDataSchemaEventEventResponse> getVoteDataSchemaEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VOTEDATASCHEMAEVENT_EVENT, transactionReceipt);
        ArrayList<VoteDataSchemaEventEventResponse> responses = new ArrayList<VoteDataSchemaEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VoteDataSchemaEventEventResponse typedResponse = new VoteDataSchemaEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dataSchemaId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.voterId = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.agree = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.agreeCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.denyCount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.afterStatus = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public TransactionReceipt approveDataSchema(byte[] dataSchemaId, Boolean agree) {
        final Function function = new Function(
                FUNC_APPROVEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(dataSchemaId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String approveDataSchema(byte[] dataSchemaId, Boolean agree,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(dataSchemaId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApproveDataSchema(byte[] dataSchemaId, Boolean agree) {
        final Function function = new Function(
                FUNC_APPROVEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(dataSchemaId),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple2<byte[], Boolean> getApproveDataSchemaInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVEDATASCHEMA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], Boolean>(

                (byte[]) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public Tuple4<byte[], BigInteger, BigInteger, BigInteger> getApproveDataSchemaOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_APPROVEDATASCHEMA, 
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

    public TransactionReceipt createDataSchema(byte[] hash, byte[] productId) {
        final Function function = new Function(
                FUNC_CREATEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(hash),
                new Bytes32(productId)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String createDataSchema(byte[] hash, byte[] productId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(hash),
                new Bytes32(productId)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateDataSchema(byte[] hash, byte[] productId) {
        final Function function = new Function(
                FUNC_CREATEDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(hash),
                new Bytes32(productId)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple2<byte[], byte[]> getCreateDataSchemaInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATEDATASCHEMA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], byte[]>(

                (byte[]) results.get(0).getValue(), 
                (byte[]) results.get(1).getValue()
                );
    }

    public Tuple2<byte[], BigInteger> getCreateDataSchemaOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_CREATEDATASCHEMA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], BigInteger>(

                (byte[]) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public DataSchemaContract.DataSchemaInfo getDataSchema(byte[] dataSchemaId) throws
            ContractException {
        final Function function = new Function(FUNC_GETDATASCHEMA, 
                Arrays.<Type>asList(new Bytes32(dataSchemaId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DataSchemaContract.DataSchemaInfo>() {}));
        return executeCallWithSingleValueReturn(function, DataSchemaContract.DataSchemaInfo.class);
    }

    public DataSchemaContract.VoteInfo getVoteInfo(byte[] dataSchemaId) throws ContractException {
        final Function function = new Function(FUNC_GETVOTEINFO, 
                Arrays.<Type>asList(new Bytes32(dataSchemaId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DataSchemaContract.VoteInfo>() {}));
        return executeCallWithSingleValueReturn(function, DataSchemaContract.VoteInfo.class);
    }

    public static DataSchemaContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new DataSchemaContract(contractAddress, client, credential);
    }

    public static DataSchemaContract deploy(Client client, CryptoKeyPair credential,
            String _accountContract, String _productContract) throws ContractException {
        byte[] encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_accountContract), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_productContract)));
        return deploy(DataSchemaContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), encodedConstructor, null);
    }

    public static class DataSchemaInfo extends StaticStruct {
        public byte[] hash;

        public byte[] ownerId;

        public byte[] productId;

        public BigInteger status;

        public DataSchemaInfo(Bytes32 hash, Bytes32 ownerId, Bytes32 productId,
                Uint8 status) {
            super(hash,ownerId,productId,status);
            this.hash = hash.getValue();
            this.ownerId = ownerId.getValue();
            this.productId = productId.getValue();
            this.status = status.getValue();
        }

        public DataSchemaInfo(byte[] hash, byte[] ownerId, byte[] productId,
                BigInteger status) {
            super(new Bytes32(hash),new Bytes32(ownerId),new Bytes32(productId),new Uint8(status));
            this.hash = hash;
            this.ownerId = ownerId;
            this.productId = productId;
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

    public static class CreateDataSchemaEventEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] dataSchemaId;

        public byte[] hash;
    }

    public static class VoteDataSchemaEventEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] dataSchemaId;

        public byte[] voterId;

        public Boolean agree;

        public BigInteger agreeCount;

        public BigInteger denyCount;

        public BigInteger afterStatus;
    }
}
