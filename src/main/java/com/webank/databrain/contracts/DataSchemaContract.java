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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f4e380380610f4e83398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e90806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80632a39a9b41461005157806339d75b771461007d57806387061ac31461009d578063fd7f643d146100c5575b600080fd5b61006461005f366004610b44565b61010b565b6040516100749493929190610ba3565b60405180910390f35b61009061008b366004610bce565b6103b4565b6040516100749190610be7565b6100b06100ab366004610c20565b610494565b60408051928352602083019190915201610074565b6100d86100d3366004610bce565b610981565b60405161007491908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b600080600080600261011f338260016109f1565b8661016a5760405162461bcd60e51b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b60448201526064015b60405180910390fd5b600087815260026020526040812090600382015460ff16600481111561019257610192610b79565b146101df5760405162461bcd60e51b815260206004820152601a60248201527f496e76616c6964206461746120736368656d61207374617475730000000000006044820152606401610161565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa158015610228573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061024c9190610c52565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff16156102b85760405162461bcd60e51b815260206004820152600e60248201526d4475706c696361746520766f746560901b6044820152606401610161565b600281015460038201548a156102eb5782546102d5906001610d0a565b80845598508189106102e657600196505b610319565b6001808401546102fa91610d0a565b60018401819055975061030d8282610d22565b88111561031957600296505b60038501805488919060ff1916600183600481111561033a5761033a610b79565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f29fefc3e7b6df316e91b9a4f9d2336b0c9ac71f4eede613e4286eb010537ea839061039d908e908d908f908e908e908e90610d39565b60405180910390a150505050505092959194509250565b6103dc6040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff16600481111561042f5761042f610b79565b600481111561044057610440610b79565b905250602081015190915061048f5760405162461bcd60e51b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b6044820152606401610161565b919050565b600080836104d35760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b6044820152606401610161565b826105145760405162461bcd60e51b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b6044820152606401610161565b600084815260036020526040902054156105705760405162461bcd60e51b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d6120686173680000000000006044820152606401610161565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa1580156105b9573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105dd9190610c52565b600154604051633a20e9df60e01b8152600481018790529192506000916001600160a01b0390911690633a20e9df90602401606060405180830381865afa15801561062c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106509190610d76565b905060018160400151600481111561066a5761066a610b79565b146106ae5760405162461bcd60e51b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b6044820152606401610161565b81602001518160200151146106fd5760405162461bcd60e51b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b6044820152606401610161565b60018260600151600381111561071557610715610b79565b146107575760405162461bcd60e51b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b6044820152606401610161565b6000858152600460205260409020546107978682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b9450604051806080016040528088815260200184602001518152602001878152602001600060048111156107cd576107cd610b79565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083600481111561082b5761082b610b79565b0217905550505060008781526003602052604090208590558061084d81610dea565b6000888152600460208190526040808320849055915491516334b7dfab60e01b81529294506001600160a01b0390911692506334b7dfab916108929160029101610e05565b602060405180830381865afa1580156108af573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108d39190610e1f565b6040805160808101825260008082526020820152919550810160026108f9876001610d0a565b6109039190610e38565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527fc40138f2abc0799418ac7cc8ff6f08368f39a551d6caa1fa343b6c08aa25bb51910160405180910390a15050509250929050565b6109ac6040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008054604051632e3a17fb60e21b81526001600160a01b0386811660048301529091169063b8e85fec9060240160a060405180830381865afa158015610a3c573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a609190610c52565b9050816003811115610a7457610a74610b79565b81606001516003811115610a8a57610a8a610b79565b14610ad05760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b6044820152606401610161565b826003811115610ae257610ae2610b79565b81604001516003811115610af857610af8610b79565b14610b3e5760405162461bcd60e51b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b6044820152606401610161565b50505050565b60008060408385031215610b5757600080fd5b8235915060208301358015158114610b6e57600080fd5b809150509250929050565b634e487b7160e01b600052602160045260246000fd5b60058110610b9f57610b9f610b79565b9052565b848152602081018490526040810183905260808101610bc56060830184610b8f565b95945050505050565b600060208284031215610be057600080fd5b5035919050565b60006080820190508251825260208301516020830152604083015160408301526060830151610c196060840182610b8f565b5092915050565b60008060408385031215610c3357600080fd5b50508035926020909101359150565b60048110610c4f57600080fd5b50565b600060a08284031215610c6457600080fd5b60405160a0810181811067ffffffffffffffff82111715610c9557634e487b7160e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610caf57600080fd5b8152602083810151908201526040830151610cc981610c42565b60408201526060830151610cdc81610c42565b60608201526080928301519281019290925250919050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610d1d57610d1d610cf4565b500190565b600082821015610d3457610d34610cf4565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d6b60a0830184610b8f565b979650505050505050565b600060608284031215610d8857600080fd5b6040516060810181811067ffffffffffffffff82111715610db957634e487b7160e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610dde57600080fd5b60408201529392505050565b6000600019821415610dfe57610dfe610cf4565b5060010190565b6020810160048310610e1957610e19610b79565b91905290565b600060208284031215610e3157600080fd5b5051919050565b600082610e5557634e487b7160e01b600052601260045260246000fd5b50049056fea26469706673582212204a2d70fabb9421655a0836e8c1ec22c1573bc7f18df7580081577c46d8c9005464736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f5a380380610f5a83398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e9c806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806334a249b314610051578063a0ccc927146100a0578063aa61cf72146100c3578063f8da933a146100eb575b600080fd5b61006461005f366004610b50565b61010b565b60405161009791908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b60405180910390f35b6100b36100ae366004610b69565b61017b565b6040516100979493929190610bc8565b6100d66100d1366004610bf3565b610427565b60408051928352602083019190915201610097565b6100fe6100f9366004610b50565b61091a565b6040516100979190610c15565b6101366040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b600080600080600261018f338260016109fb565b866101db57604051636381e58960e11b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b60448201526064015b60405180910390fd5b600087815260026020526040812090600382015460ff16600481111561020357610203610b9e565b1461025157604051636381e58960e11b815260206004820152601a60248201527f496e76616c6964206461746120736368656d612073746174757300000000000060448201526064016101d2565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa15801561029a573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102be9190610c5e565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff161561032b57604051636381e58960e11b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101d2565b600281015460038201548a1561035e578254610348906001610d16565b808455985081891061035957600196505b61038c565b60018084015461036d91610d16565b6001840181905597506103808282610d2e565b88111561038c57600296505b60038501805488919060ff191660018360048111156103ad576103ad610b9e565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f663a09eb7428d9225d1d2c815ea18d3745064b851ad06b66f4f47a9aba859b6f90610410908e908d908f908e908e908e90610d45565b60405180910390a150505050505092959194509250565b6000808361046757604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101d2565b826104a957604051636381e58960e11b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b60448201526064016101d2565b6000848152600360205260409020541561050657604051636381e58960e11b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d61206861736800000000000060448201526064016101d2565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa15801561054f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105739190610c5e565b6001546040516311ef7f7f60e11b8152600481018790529192506000916001600160a01b03909116906323defefe90602401606060405180830381865afa1580156105c2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105e69190610d82565b905060018160400151600481111561060057610600610b9e565b1461064557604051636381e58960e11b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b60448201526064016101d2565b816020015181602001511461069557604051636381e58960e11b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b60448201526064016101d2565b6001826060015160038111156106ad576106ad610b9e565b146106f057604051636381e58960e11b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b60448201526064016101d2565b6000858152600460205260409020546107308682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b94506040518060800160405280888152602001846020015181526020018781526020016000600481111561076657610766610b9e565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff021916908360048111156107c4576107c4610b9e565b021790555050506000878152600360205260409020859055806107e681610df6565b600088815260046020819052604080832084905591549151638c25a22560e01b81529294506001600160a01b039091169250638c25a2259161082b9160029101610e11565b602060405180830381865afa158015610848573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061086c9190610e2b565b604080516080810182526000808252602082015291955081016002610892876001610d16565b61089c9190610e44565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527f05fd4f713f9fdded770a2d1b269da2d71a761e7471fad40cc24b362525998a50910160405180910390a15050509250929050565b6109426040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff16600481111561099557610995610b9e565b60048111156109a6576109a6610b9e565b90525060208101519091506109f657604051636381e58960e11b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b60448201526064016101d2565b919050565b60008054604051631637d7b960e11b81526001600160a01b03868116600483015290911690632c6faf729060240160a060405180830381865afa158015610a46573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a6a9190610c5e565b9050816003811115610a7e57610a7e610b9e565b81606001516003811115610a9457610a94610b9e565b14610adb57604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101d2565b826003811115610aed57610aed610b9e565b81604001516003811115610b0357610b03610b9e565b14610b4a57604051636381e58960e11b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101d2565b50505050565b600060208284031215610b6257600080fd5b5035919050565b60008060408385031215610b7c57600080fd5b8235915060208301358015158114610b9357600080fd5b809150509250929050565b63b95aa35560e01b600052602160045260246000fd5b60058110610bc457610bc4610b9e565b9052565b848152602081018490526040810183905260808101610bea6060830184610bb4565b95945050505050565b60008060408385031215610c0657600080fd5b50508035926020909101359150565b60006080820190508251825260208301516020830152604083015160408301526060830151610c476060840182610bb4565b5092915050565b60048110610c5b57600080fd5b50565b600060a08284031215610c7057600080fd5b60405160a0810181811067ffffffffffffffff82111715610ca15763b95aa35560e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610cbb57600080fd5b8152602083810151908201526040830151610cd581610c4e565b60408201526060830151610ce881610c4e565b60608201526080928301519281019290925250919050565b63b95aa35560e01b600052601160045260246000fd5b60008219821115610d2957610d29610d00565b500190565b600082821015610d4057610d40610d00565b500390565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d7760a0830184610bb4565b979650505050505050565b600060608284031215610d9457600080fd5b6040516060810181811067ffffffffffffffff82111715610dc55763b95aa35560e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610dea57600080fd5b60408201529392505050565b6000600019821415610e0a57610e0a610d00565b5060010190565b6020810160048310610e2557610e25610b9e565b91905290565b600060208284031215610e3d57600080fd5b5051919050565b600082610e615763b95aa35560e01b600052601260045260246000fd5b50049056fea264697066735822122035ca25e67d01c9c43d7dcb9fefbcbabb1d58ff0a158a7cd5adda09e1ea10a45b64736f6c634300080b0033"};

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
