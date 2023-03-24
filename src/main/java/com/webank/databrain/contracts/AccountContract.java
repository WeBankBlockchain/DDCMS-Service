package com.webank.databrain.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
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
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class AccountContract extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b5062000023336003600160006200002a565b50620002d1565b600062000039848684620001da565b6001600160a01b03861660008181526020818152604091829020849055815160a08101835292835282018390529192509081018560038111156200008157620000816200024a565b81526020018460038111156200009b576200009b6200024a565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff1916908360038111156200010157620001016200024a565b0217905550606082015160028201805461ff0019166101008360038111156200012e576200012e6200024a565b021790555060808201518160030155905050600260008560038111156200015957620001596200024a565b60038111156200016d576200016d6200024a565b815260200190815260200160002060008154809291906200018e9062000260565b91905055507f509fd4a7c7eeb48b99013f5e25a4899d7c8bed600405508511e7845fd25ed4f781868685604051620001ca94939291906200028a565b60405180910390a1949350505050565b60008060f0856003811115620001f457620001f46200024a565b6040516001600160601b0319606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b634e487b7160e01b600052602160045260246000fd5b60006000198214156200028357634e487b7160e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b03841660208201526080810160048410620002bf57634e487b7160e01b600052602160045260246000fd5b60408201939093526060015292915050565b610b4380620002e16000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063b8e85fec1161005b578063b8e85fec146100dd578063cae27f8f146100fd578063ed1c5cc21461011d578063ee112f4b1461013057600080fd5b806334b7dfab146100825780634ec79937146100b557806369122155146100ca575b600080fd5b6100a2610090366004610923565b60026020526000908152604090205481565b6040519081526020015b60405180910390f35b6100c86100c3366004610945565b610190565b005b6100a26100d836600461097a565b6102fc565b6100f06100eb3660046109a4565b6103ac565b6040516100ac9190610a04565b6100a261010b3660046109a4565b60006020819052908152604090205481565b6100f061012b366004610a58565b6103ed565b61017f61013e366004610a58565b600160208190526000918252604090912080549181015460028201546003909201546001600160a01b0390931692909160ff80821692610100909204169085565b6040516100ac959493929190610a71565b600361019e338260016104d1565b600083815260016020526040902080546001600160a01b03166101fc5760405162461bcd60e51b81526020600482015260116024820152701058d8dbdd5b9d081b9bdd08195e1a5cdd607a1b60448201526064015b60405180910390fd5b60006002820154610100900460ff16600381111561021c5761021c6109cd565b146102625760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101f3565b82156102b15760028101805461ff0019166101001790556040805185815290517f8417a6bd6d60e8496c1139845a4c1956016465c032bbfaf5753ab54dbf6637089181900360200190a16102f6565b60028101805461ff0019166102001790556040805185815290517fea990d91318294cd03b1c8501bb7fa1debbb22d26a0ecbab6c2278e2fd75891d9181900360200190a15b50505050565b60008161033a5760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101f3565b33600081815260208190526040902054156103975760405162461bcd60e51b815260206004820152601a60248201527f6164647265737320616c7265616479207265676973746572656400000000000060448201526064016101f3565b6103a481856000866105c1565b949350505050565b6103de6040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6103e782610759565b92915050565b61041f6040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b600082815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff166003811115610472576104726109cd565b6003811115610483576104836109cd565b81526020016002820160019054906101000a900460ff1660038111156104ab576104ab6109cd565b60038111156104bc576104bc6109cd565b81526020016003820154815250509050919050565b60006104dc84610759565b90508160038111156104f0576104f06109cd565b81606001516003811115610506576105066109cd565b1461054c5760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101f3565b82600381111561055e5761055e6109cd565b81604001516003811115610574576105746109cd565b146102f65760405162461bcd60e51b815260206004820152601a60248201527f4163636f756e7420617265206e6f7420617574686f72697a656400000000000060448201526064016101f3565b60006105ce84868461089d565b6001600160a01b03861660008181526020818152604091829020849055815160a0810183529283528201839052919250908101856003811115610613576106136109cd565b815260200184600381111561062a5761062a6109cd565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff19169083600381111561068d5761068d6109cd565b0217905550606082015160028201805461ff0019166101008360038111156106b7576106b76109cd565b021790555060808201518160030155905050600260008560038111156106df576106df6109cd565b60038111156106f0576106f06109cd565b8152602001908152602001600020600081548092919061070f90610ab3565b91905055507f509fd4a7c7eeb48b99013f5e25a4899d7c8bed600405508511e7845fd25ed4f7818686856040516107499493929190610adc565b60405180910390a1949350505050565b61078b6040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6001600160a01b038216600090815260208190526040902054806107ea5760405162461bcd60e51b81526020600482015260166024820152751859191c995cdcc81b9bdd081c9959da5cdd195c995960521b60448201526064016101f3565b600081815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff16600381111561083d5761083d6109cd565b600381111561084e5761084e6109cd565b81526020016002820160019054906101000a900460ff166003811115610876576108766109cd565b6003811115610887576108876109cd565b8152602001600382015481525050915050919050565b60008060f08560038111156108b4576108b46109cd565b6040516bffffffffffffffffffffffff19606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b80356004811061091e57600080fd5b919050565b60006020828403121561093557600080fd5b61093e8261090f565b9392505050565b6000806040838503121561095857600080fd5b823591506020830135801515811461096f57600080fd5b809150509250929050565b6000806040838503121561098d57600080fd5b6109968361090f565b946020939093013593505050565b6000602082840312156109b657600080fd5b81356001600160a01b038116811461093e57600080fd5b634e487b7160e01b600052602160045260246000fd5b60048110610a0157634e487b7160e01b600052602160045260246000fd5b50565b81516001600160a01b0316815260208083015190820152604082015160a0820190610a2e816109e3565b60408301526060830151610a41816109e3565b806060840152506080830151608083015292915050565b600060208284031215610a6a57600080fd5b5035919050565b6001600160a01b03861681526020810185905260a08101610a91856109e3565b846040830152610aa0846109e3565b6060820193909352608001529392505050565b6000600019821415610ad557634e487b7160e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b038416602082015260808101610afb846109e3565b6040820193909352606001529291505056fea26469706673582212207bb5d5c12aabf11285779aee7c9a428cc05bfcc83d8f3a2a41974b5dfca7c39a64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b5062000023336003600160006200002a565b50620002d1565b600062000039848684620001da565b6001600160a01b03861660008181526020818152604091829020849055815160a08101835292835282018390529192509081018560038111156200008157620000816200024a565b81526020018460038111156200009b576200009b6200024a565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff1916908360038111156200010157620001016200024a565b0217905550606082015160028201805461ff0019166101008360038111156200012e576200012e6200024a565b021790555060808201518160030155905050600260008560038111156200015957620001596200024a565b60038111156200016d576200016d6200024a565b815260200190815260200160002060008154809291906200018e9062000260565b91905055507fe107be78c95f4c23dfc84b3d95c3af239c828961144dd74c17eadc85f15670c581868685604051620001ca94939291906200028a565b60405180910390a1949350505050565b60008060f0856003811115620001f457620001f46200024a565b6040516001600160601b0319606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b63b95aa35560e01b600052602160045260246000fd5b6000600019821415620002835763b95aa35560e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b03841660208201526080810160048410620002bf5763b95aa35560e01b600052602160045260246000fd5b60408201939093526060015292915050565b610b4e80620002e16000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80638c25a2251161005b5780638c25a225146100d35780639e97e65614610101578063d1d9b59814610161578063da7df57a1461018157600080fd5b80632c6faf72146100825780633001847c146100ab578063861bd55a146100be575b600080fd5b61009561009036600461091a565b610194565b6040516100a29190610981565b60405180910390f35b6100956100b93660046109d5565b6101d5565b6100d16100cc3660046109ee565b6102b9565b005b6100f36100e1366004610a37565b60026020526000908152604090205481565b6040519081526020016100a2565b61015061010f3660046109d5565b600160208190526000918252604090912080549181015460028201546003909201546001600160a01b0390931692909160ff80821692610100909204169085565b6040516100a2959493929190610a52565b6100f361016f36600461091a565b60006020819052908152604090205481565b6100f361018f366004610a94565b610427565b6101c66040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6101cf826104d9565b92915050565b6102076040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b600082815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff16600381111561025a5761025a61094a565b600381111561026b5761026b61094a565b81526020016002820160019054906101000a900460ff1660038111156102935761029361094a565b60038111156102a4576102a461094a565b81526020016003820154815250509050919050565b60036102c73382600161061e565b600083815260016020526040902080546001600160a01b031661032657604051636381e58960e11b81526020600482015260116024820152701058d8dbdd5b9d081b9bdd08195e1a5cdd607a1b60448201526064015b60405180910390fd5b60006002820154610100900460ff1660038111156103465761034661094a565b1461038d57604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b604482015260640161031d565b82156103dc5760028101805461ff0019166101001790556040805185815290517fabedbd59572657258f3fe6088f4b6df89d49c9875645e9eecc9ad08c61185c2c9181900360200190a1610421565b60028101805461ff0019166102001790556040805185815290517f1c57d3f08b142c5f7dbda71ebc0333d98cbd6fcf4621d0969b780f43e240fe289181900360200190a15b50505050565b60008161046657604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b604482015260640161031d565b33600081815260208190526040902054156104c457604051636381e58960e11b815260206004820152601a60248201527f6164647265737320616c72656164792072656769737465726564000000000000604482015260640161031d565b6104d18185600086610710565b949350505050565b61050b6040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6001600160a01b0382166000908152602081905260409020548061056b57604051636381e58960e11b81526020600482015260166024820152751859191c995cdcc81b9bdd081c9959da5cdd195c995960521b604482015260640161031d565b600081815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff1660038111156105be576105be61094a565b60038111156105cf576105cf61094a565b81526020016002820160019054906101000a900460ff1660038111156105f7576105f761094a565b60038111156106085761060861094a565b8152602001600382015481525050915050919050565b6000610629846104d9565b905081600381111561063d5761063d61094a565b816060015160038111156106535761065361094a565b1461069a57604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b604482015260640161031d565b8260038111156106ac576106ac61094a565b816040015160038111156106c2576106c261094a565b1461042157604051636381e58960e11b815260206004820152601a60248201527f4163636f756e7420617265206e6f7420617574686f72697a6564000000000000604482015260640161031d565b600061071d8486846108a8565b6001600160a01b03861660008181526020818152604091829020849055815160a08101835292835282018390529192509081018560038111156107625761076261094a565b81526020018460038111156107795761077961094a565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff1916908360038111156107dc576107dc61094a565b0217905550606082015160028201805461ff0019166101008360038111156108065761080661094a565b0217905550608082015181600301559050506002600085600381111561082e5761082e61094a565b600381111561083f5761083f61094a565b8152602001908152602001600020600081548092919061085e90610abe565b91905055507fe107be78c95f4c23dfc84b3d95c3af239c828961144dd74c17eadc85f15670c5818686856040516108989493929190610ae7565b60405180910390a1949350505050565b60008060f08560038111156108bf576108bf61094a565b6040516bffffffffffffffffffffffff19606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b60006020828403121561092c57600080fd5b81356001600160a01b038116811461094357600080fd5b9392505050565b63b95aa35560e01b600052602160045260246000fd5b6004811061097e5763b95aa35560e01b600052602160045260246000fd5b50565b81516001600160a01b0316815260208083015190820152604082015160a08201906109ab81610960565b604083015260608301516109be81610960565b806060840152506080830151608083015292915050565b6000602082840312156109e757600080fd5b5035919050565b60008060408385031215610a0157600080fd5b8235915060208301358015158114610a1857600080fd5b809150509250929050565b803560048110610a3257600080fd5b919050565b600060208284031215610a4957600080fd5b61094382610a23565b6001600160a01b03861681526020810185905260a08101610a7285610960565b846040830152610a8184610960565b6060820193909352608001529392505050565b60008060408385031215610aa757600080fd5b610ab083610a23565b946020939093013593505050565b6000600019821415610ae05763b95aa35560e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b038416602082015260808101610b0684610960565b6040820193909352606001529291505056fea2646970667358221220803f40780cb696cfe25a5354fee25017a0bd8b830aebcc35ad0cf5d33911776d64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"AccountApprovedEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"AccountDeniedEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"AccountRegisteredEvent\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":3,\"slot\":2,\"value\":[0]}],\"inputs\":[{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"\",\"type\":\"uint8\"}],\"name\":\"accountTypeNumbers\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[884465579,2351276581],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":0,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"addressToDid\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[3403841423,3520705944],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":0,\"value\":[0]},{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"}],\"name\":\"approve\",\"outputs\":[],\"selector\":[1321703735,2249971034],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"didToAccount\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"selector\":[3994103627,2660755030],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"getAccountByAddress\",\"outputs\":[{\"components\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"internalType\":\"struct AccountContract.AccountData\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[3102236652,745516914],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"getAccountByDid\",\"outputs\":[{\"components\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"internalType\":\"struct AccountContract.AccountData\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[3978058946,805405820],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":0,\"value\":[0]},{\"kind\":3,\"slot\":2,\"value\":[0]}],\"inputs\":[{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"register\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"selector\":[1762795861,3665687930],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ACCOUNTTYPENUMBERS = "accountTypeNumbers";

    public static final String FUNC_ADDRESSTODID = "addressToDid";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_DIDTOACCOUNT = "didToAccount";

    public static final String FUNC_GETACCOUNTBYADDRESS = "getAccountByAddress";

    public static final String FUNC_GETACCOUNTBYDID = "getAccountByDid";

    public static final String FUNC_REGISTER = "register";

    public static final Event ACCOUNTAPPROVEDEVENT_EVENT = new Event("AccountApprovedEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event ACCOUNTDENIEDEVENT_EVENT = new Event("AccountDeniedEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event ACCOUNTREGISTEREDEVENT_EVENT = new Event("AccountRegisteredEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
    ;

    protected AccountContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<AccountApprovedEventEventResponse> getAccountApprovedEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACCOUNTAPPROVEDEVENT_EVENT, transactionReceipt);
        ArrayList<AccountApprovedEventEventResponse> responses = new ArrayList<AccountApprovedEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountApprovedEventEventResponse typedResponse = new AccountApprovedEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<AccountDeniedEventEventResponse> getAccountDeniedEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACCOUNTDENIEDEVENT_EVENT, transactionReceipt);
        ArrayList<AccountDeniedEventEventResponse> responses = new ArrayList<AccountDeniedEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountDeniedEventEventResponse typedResponse = new AccountDeniedEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<AccountRegisteredEventEventResponse> getAccountRegisteredEventEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ACCOUNTREGISTEREDEVENT_EVENT, transactionReceipt);
        ArrayList<AccountRegisteredEventEventResponse> responses = new ArrayList<AccountRegisteredEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountRegisteredEventEventResponse typedResponse = new AccountRegisteredEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.did = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.accountType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public BigInteger accountTypeNumbers(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_ACCOUNTTYPENUMBERS, 
                Arrays.<Type>asList(new Uint8(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public byte[] addressToDid(String param0) throws ContractException {
        final Function function = new Function(FUNC_ADDRESSTODID, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public TransactionReceipt approve(byte[] did, Boolean agree) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Bytes32(did),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String approve(byte[] did, Boolean agree, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Bytes32(did),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApprove(byte[] did, Boolean agree) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Bytes32(did),
                new Bool(agree)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple2<byte[], Boolean> getApproveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], Boolean>(

                (byte[]) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public Tuple5<String, byte[], BigInteger, BigInteger, byte[]> didToAccount(byte[] param0) throws
            ContractException {
        final Function function = new Function(FUNC_DIDTOACCOUNT, 
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<String, byte[], BigInteger, BigInteger, byte[]>(
                (String) results.get(0).getValue(), 
                (byte[]) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (byte[]) results.get(4).getValue());
    }

    public AccountContract.AccountData getAccountByAddress(String addr) throws ContractException {
        final Function function = new Function(FUNC_GETACCOUNTBYADDRESS, 
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<AccountContract.AccountData>() {}));
        return executeCallWithSingleValueReturn(function, AccountContract.AccountData.class);
    }

    public AccountContract.AccountData getAccountByDid(byte[] did) throws ContractException {
        final Function function = new Function(FUNC_GETACCOUNTBYDID, 
                Arrays.<Type>asList(new Bytes32(did)),
                Arrays.<TypeReference<?>>asList(new TypeReference<AccountContract.AccountData>() {}));
        return executeCallWithSingleValueReturn(function, AccountContract.AccountData.class);
    }

    public TransactionReceipt register(BigInteger accountType, byte[] hash) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Uint8(accountType),
                new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String register(BigInteger accountType, byte[] hash, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Uint8(accountType),
                new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegister(BigInteger accountType, byte[] hash) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Uint8(accountType),
                new Bytes32(hash)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple2<BigInteger, byte[]> getRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<BigInteger, byte[]>(

                (BigInteger) results.get(0).getValue(), 
                (byte[]) results.get(1).getValue()
                );
    }

    public Tuple1<byte[]> getRegisterOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public static AccountContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new AccountContract(contractAddress, client, credential);
    }

    public static AccountContract deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(AccountContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }

    public static class AccountData extends StaticStruct {
        public String addr;

        public byte[] did;

        public BigInteger accountType;

        public BigInteger accountStatus;

        public byte[] hash;

        public AccountData(Address addr, Bytes32 did, Uint8 accountType,
                Uint8 accountStatus, Bytes32 hash) {
            super(addr,did,accountType,accountStatus,hash);
            this.addr = addr.getValue();
            this.did = did.getValue();
            this.accountType = accountType.getValue();
            this.accountStatus = accountStatus.getValue();
            this.hash = hash.getValue();
        }

        public AccountData(String addr, byte[] did, BigInteger accountType,
                BigInteger accountStatus, byte[] hash) {
            super(new Address(addr),new Bytes32(did),new Uint8(accountType),new Uint8(accountStatus),new Bytes32(hash));
            this.addr = addr;
            this.did = did;
            this.accountType = accountType;
            this.accountStatus = accountStatus;
            this.hash = hash;
        }
    }

    public static class AccountApprovedEventEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] did;
    }

    public static class AccountDeniedEventEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] did;
    }

    public static class AccountRegisteredEventEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] did;

        public String addr;

        public BigInteger accountType;

        public byte[] hash;
    }
}
