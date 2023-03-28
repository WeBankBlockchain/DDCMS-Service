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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f3b380380610f3b83398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e7d806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80632a39a9b41461005157806339d75b771461007d57806387061ac31461009d578063fd7f643d146100c5575b600080fd5b61006461005f366004610b31565b61010b565b6040516100749493929190610b90565b60405180910390f35b61009061008b366004610bbb565b6104f4565b6040516100749190610bd4565b6100b06100ab366004610c0d565b6105d4565b60408051928352602083019190915201610074565b6100d86100d3366004610bbb565b610ac1565b60405161007491908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b60008054604051632e3a17fb60e21b815233600482015282918291829182916001600160a01b039091169063b8e85fec9060240160a060405180830381865afa15801561015c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101809190610c3f565b905060018160600151600381111561019a5761019a610b66565b146101e55760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b6002816040015160038111156101fd576101fd610b66565b146102435760405162461bcd60e51b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b60448201526064016101dc565b866102895760405162461bcd60e51b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b60448201526064016101dc565b600087815260026020526040812090600382015460ff1660048111156102b1576102b1610b66565b146102fe5760405162461bcd60e51b815260206004820152601a60248201527f496e76616c6964206461746120736368656d612073746174757300000000000060448201526064016101dc565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa158015610347573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061036b9190610c3f565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff16156103d75760405162461bcd60e51b815260206004820152600e60248201526d4475706c696361746520766f746560901b60448201526064016101dc565b600281015460038201548a156104125782546103f4906001610cf7565b8084556001840154909950975081891061040d57600196505b610459565b60018084015461042191610cf7565b600180850182905584546003860154909b509199506002916104439190610d0f565b61044d9190610d26565b88111561045957600296505b60038501805488919060ff1916600183600481111561047a5761047a610b66565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f29fefc3e7b6df316e91b9a4f9d2336b0c9ac71f4eede613e4286eb010537ea83906104dd908e908d908f908e908e908e90610d48565b60405180910390a150505050505092959194509250565b61051c6040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff16600481111561056f5761056f610b66565b600481111561058057610580610b66565b90525060208101519091506105cf5760405162461bcd60e51b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b60448201526064016101dc565b919050565b600080836106135760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101dc565b826106545760405162461bcd60e51b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b60448201526064016101dc565b600084815260036020526040902054156106b05760405162461bcd60e51b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d61206861736800000000000060448201526064016101dc565b60008054604051632e3a17fb60e21b81523360048201526001600160a01b039091169063b8e85fec9060240160a060405180830381865afa1580156106f9573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061071d9190610c3f565b600154604051633a20e9df60e01b8152600481018790529192506000916001600160a01b0390911690633a20e9df90602401606060405180830381865afa15801561076c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107909190610d85565b90506001816040015160048111156107aa576107aa610b66565b146107ee5760405162461bcd60e51b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b60448201526064016101dc565b816020015181602001511461083d5760405162461bcd60e51b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b60448201526064016101dc565b60018260600151600381111561085557610855610b66565b146108975760405162461bcd60e51b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b60448201526064016101dc565b6000858152600460205260409020546108d78682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b94506040518060800160405280888152602001846020015181526020018781526020016000600481111561090d5761090d610b66565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083600481111561096b5761096b610b66565b0217905550505060008781526003602052604090208590558061098d81610df9565b6000888152600460208190526040808320849055915491516334b7dfab60e01b81529294506001600160a01b0390911692506334b7dfab916109d29160029101610e14565b602060405180830381865afa1580156109ef573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a139190610e2e565b604080516080810182526000808252602082015291955081016002610a39876001610cf7565b610a439190610d26565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527fc40138f2abc0799418ac7cc8ff6f08368f39a551d6caa1fa343b6c08aa25bb51910160405180910390a15050509250929050565b610aec6040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008060408385031215610b4457600080fd5b8235915060208301358015158114610b5b57600080fd5b809150509250929050565b634e487b7160e01b600052602160045260246000fd5b60058110610b8c57610b8c610b66565b9052565b848152602081018490526040810183905260808101610bb26060830184610b7c565b95945050505050565b600060208284031215610bcd57600080fd5b5035919050565b60006080820190508251825260208301516020830152604083015160408301526060830151610c066060840182610b7c565b5092915050565b60008060408385031215610c2057600080fd5b50508035926020909101359150565b60048110610c3c57600080fd5b50565b600060a08284031215610c5157600080fd5b60405160a0810181811067ffffffffffffffff82111715610c8257634e487b7160e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610c9c57600080fd5b8152602083810151908201526040830151610cb681610c2f565b60408201526060830151610cc981610c2f565b60608201526080928301519281019290925250919050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610d0a57610d0a610ce1565b500190565b600082821015610d2157610d21610ce1565b500390565b600082610d4357634e487b7160e01b600052601260045260246000fd5b500490565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d7a60a0830184610b7c565b979650505050505050565b600060608284031215610d9757600080fd5b6040516060810181811067ffffffffffffffff82111715610dc857634e487b7160e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610ded57600080fd5b60408201529392505050565b6000600019821415610e0d57610e0d610ce1565b5060010190565b6020810160048310610e2857610e28610b66565b91905290565b600060208284031215610e4057600080fd5b505191905056fea2646970667358221220f38d22313546df97ed17bd679275bdafc301e300f1e23c950ac73eaa5ffe3a1964736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50604051610f47380380610f4783398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e89806100be6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806334a249b314610051578063a0ccc927146100a0578063aa61cf72146100c3578063f8da933a146100eb575b600080fd5b61006461005f366004610b3d565b61010b565b60405161009791908151815260208083015190820152604080830151908201526060918201519181019190915260800190565b60405180910390f35b6100b36100ae366004610b56565b61017b565b6040516100979493929190610bb5565b6100d66100d1366004610be0565b610569565b60408051928352602083019190915201610097565b6100fe6100f9366004610b3d565b610a5c565b6040516100979190610c02565b6101366040518060800160405280600081526020016000815260200160008152602001600081525090565b50600090815260056020908152604091829020825160808101845281548152600182015492810192909252600281015492820192909252600390910154606082015290565b60008054604051631637d7b960e11b815233600482015282918291829182916001600160a01b0390911690632c6faf729060240160a060405180830381865afa1580156101cc573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101f09190610c4b565b905060018160600151600381111561020a5761020a610b8b565b1461025657604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b60028160400151600381111561026e5761026e610b8b565b146102b557604051636381e58960e11b81526020600482015260166024820152754163636f756e74206973206e6f74207769746e65737360501b604482015260640161024d565b866102fc57604051636381e58960e11b8152602060048201526016602482015275125b9d985b1a590819185d18481cd8da195b58481a5960521b604482015260640161024d565b600087815260026020526040812090600382015460ff16600481111561032457610324610b8b565b1461037257604051636381e58960e11b815260206004820152601a60248201527f496e76616c6964206461746120736368656d6120737461747573000000000000604482015260640161024d565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa1580156103bb573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103df9190610c4b565b60208082015160008c8152600583526040808220600685528183208484529094529020549099509192509060ff161561044c57604051636381e58960e11b815260206004820152600e60248201526d4475706c696361746520766f746560901b604482015260640161024d565b600281015460038201548a15610487578254610469906001610d03565b8084556001840154909950975081891061048257600196505b6104ce565b60018084015461049691610d03565b600180850182905584546003860154909b509199506002916104b89190610d1b565b6104c29190610d32565b8811156104ce57600296505b60038501805488919060ff191660018360048111156104ef576104ef610b8b565b021790555060008c81526006602090815260408083208d845290915290819020805460ff19166001179055517f663a09eb7428d9225d1d2c815ea18d3745064b851ad06b66f4f47a9aba859b6f90610552908e908d908f908e908e908e90610d54565b60405180910390a150505050505092959194509250565b600080836105a957604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b604482015260640161024d565b826105eb57604051636381e58960e11b8152602060048201526011602482015270125b9d985b1a59081c1c9bd91d58dd1259607a1b604482015260640161024d565b6000848152600360205260409020541561064857604051636381e58960e11b815260206004820152601a60248201527f6475706c6963617465206461746120736368656d612068617368000000000000604482015260640161024d565b60008054604051631637d7b960e11b81523360048201526001600160a01b0390911690632c6faf729060240160a060405180830381865afa158015610691573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106b59190610c4b565b6001546040516311ef7f7f60e11b8152600481018790529192506000916001600160a01b03909116906323defefe90602401606060405180830381865afa158015610704573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107289190610d91565b905060018160400151600481111561074257610742610b8b565b1461078757604051636381e58960e11b81526020600482015260146024820152731c1c9bd91d58dd081b9bdd08185c1c1c9bdd995960621b604482015260640161024d565b81602001518160200151146107d757604051636381e58960e11b815260206004820152601560248201527436bab9ba10313290383937b23ab1ba1037bbb732b960591b604482015260640161024d565b6001826060015160038111156107ef576107ef610b8b565b1461083257604051636381e58960e11b81526020600482015260126024820152711bdddb995c881b9bdd08185c1c1c9bdd995960721b604482015260640161024d565b6000858152600460205260409020546108728682604080516020808201949094528082019290925280518083038201815260609092019052805191012090565b9450604051806080016040528088815260200184602001518152602001878152602001600060048111156108a8576108a8610b8b565b8152506002600087815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083600481111561090657610906610b8b565b0217905550505060008781526003602052604090208590558061092881610e05565b600088815260046020819052604080832084905591549151638c25a22560e01b81529294506001600160a01b039091169250638c25a2259161096d9160029101610e20565b602060405180830381865afa15801561098a573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109ae9190610e3a565b6040805160808101825260008082526020820152919550810160026109d4876001610d03565b6109de9190610d32565b815260209081018690526000878152600582526040908190208351815583830151600182015583820151600282015560609093015160039093019290925581518781529081018990527f05fd4f713f9fdded770a2d1b269da2d71a761e7471fad40cc24b362525998a50910160405180910390a15050509250929050565b610a846040805160808101825260008082526020820181905291810182905290606082015290565b60008281526002602081815260409283902083516080810185528154815260018201549281019290925291820154928101929092526003810154606083019060ff166004811115610ad757610ad7610b8b565b6004811115610ae857610ae8610b8b565b9052506020810151909150610b3857604051636381e58960e11b815260206004820152601560248201527419185d18481cd8da195b58481b9bdd08195e1a5cdd605a1b604482015260640161024d565b919050565b600060208284031215610b4f57600080fd5b5035919050565b60008060408385031215610b6957600080fd5b8235915060208301358015158114610b8057600080fd5b809150509250929050565b63b95aa35560e01b600052602160045260246000fd5b60058110610bb157610bb1610b8b565b9052565b848152602081018490526040810183905260808101610bd76060830184610ba1565b95945050505050565b60008060408385031215610bf357600080fd5b50508035926020909101359150565b60006080820190508251825260208301516020830152604083015160408301526060830151610c346060840182610ba1565b5092915050565b60048110610c4857600080fd5b50565b600060a08284031215610c5d57600080fd5b60405160a0810181811067ffffffffffffffff82111715610c8e5763b95aa35560e01b600052604160045260246000fd5b60405282516001600160a01b0381168114610ca857600080fd5b8152602083810151908201526040830151610cc281610c3b565b60408201526060830151610cd581610c3b565b60608201526080928301519281019290925250919050565b63b95aa35560e01b600052601160045260246000fd5b60008219821115610d1657610d16610ced565b500190565b600082821015610d2d57610d2d610ced565b500390565b600082610d4f5763b95aa35560e01b600052601260045260246000fd5b500490565b600060c0820190508782528660208301528515156040830152846060830152836080830152610d8660a0830184610ba1565b979650505050505050565b600060608284031215610da357600080fd5b6040516060810181811067ffffffffffffffff82111715610dd45763b95aa35560e01b600052604160045260246000fd5b80604052508251815260208301516020820152604083015160058110610df957600080fd5b60408201529392505050565b6000600019821415610e1957610e19610ced565b5060010190565b6020810160048310610e3457610e34610b8b565b91905290565b600060208284031215610e4c57600080fd5b505191905056fea264697066735822122008e87064d17834a333a091ae98e1ea9d629ba4d6aacf725866b0b4fdfd0c88f064736f6c634300080b0033"};

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
        System.out.println(credential.getAddress());
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
