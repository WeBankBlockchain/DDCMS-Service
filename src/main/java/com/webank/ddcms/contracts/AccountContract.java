package com.webank.ddcms.contracts;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.*;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class AccountContract extends Contract {
  public static final String[] BINARY_ARRAY = {
    "60806040523480156200001157600080fd5b5062000023336003600160006200002a565b5062000254565b60006200003984868462000187565b6001600160a01b03861660008181526020818152604091829020849055815160a0810183529283528201839052919250908101856003811115620000815762000081620001f7565b81526020018460038111156200009b576200009b620001f7565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff191690836003811115620001015762000101620001f7565b0217905550606082015160028201805461ff0019166101008360038111156200012e576200012e620001f7565b0217905550608082015181600301559050507f509fd4a7c7eeb48b99013f5e25a4899d7c8bed600405508511e7845fd25ed4f7818686856040516200017794939291906200020d565b60405180910390a1949350505050565b60008060f0856003811115620001a157620001a1620001f7565b6040516001600160601b0319606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b634e487b7160e01b600052602160045260246000fd5b8481526001600160a01b038416602082015260808101600484106200024257634e487b7160e01b600052602160045260246000fd5b60408201939093526060015292915050565b610b1180620002646000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063b8e85fec1161005b578063b8e85fec146100dd578063cae27f8f146100fd578063ed1c5cc21461011d578063ee112f4b1461013057600080fd5b806334b7dfab146100825780634ec79937146100b557806369122155146100ca575b600080fd5b6100a26100903660046108f1565b60026020526000908152604090205481565b6040519081526020015b60405180910390f35b6100c86100c3366004610913565b610190565b005b6100a26100d8366004610948565b610405565b6100f06100eb366004610972565b6104b5565b6040516100ac91906109d2565b6100a261010b366004610972565b60006020819052908152604090205481565b6100f061012b366004610a26565b6104f6565b61017f61013e366004610a26565b600160208190526000918252604090912080549181015460028201546003909201546001600160a01b0390931692909160ff80821692610100909204169085565b6040516100ac959493929190610a3f565b600061019b336105da565b90506001816060015160038111156101b5576101b561099b565b146102005760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b6003816040015160038111156102185761021861099b565b1461025d5760405162461bcd60e51b815260206004820152601560248201527420b1b1b7bab73a1030b932903737ba1030b236b4b760591b60448201526064016101f7565b600083815260016020526040902080546001600160a01b03166102b65760405162461bcd60e51b81526020600482015260116024820152701058d8dbdd5b9d081b9bdd08195e1a5cdd607a1b60448201526064016101f7565b60006002820154610100900460ff1660038111156102d6576102d661099b565b1461031c5760405162461bcd60e51b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064016101f7565b82156103ba576002818101805461010061ff001982161790915560009060ff16600381111561034d5761034d61099b565b600381111561035e5761035e61099b565b8152602001908152602001600020600081548092919061037d90610a81565b90915550506040518481527f8417a6bd6d60e8496c1139845a4c1956016465c032bbfaf5753ab54dbf6637089060200160405180910390a16103ff565b60028101805461ff0019166102001790556040805185815290517fea990d91318294cd03b1c8501bb7fa1debbb22d26a0ecbab6c2278e2fd75891d9181900360200190a15b50505050565b6000816104435760405162461bcd60e51b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b60448201526064016101f7565b33600081815260208190526040902054156104a05760405162461bcd60e51b815260206004820152601a60248201527f6164647265737320616c7265616479207265676973746572656400000000000060448201526064016101f7565b6104ad818560008661071e565b949350505050565b6104e76040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6104f0826105da565b92915050565b6105286040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b600082815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff16600381111561057b5761057b61099b565b600381111561058c5761058c61099b565b81526020016002820160019054906101000a900460ff1660038111156105b4576105b461099b565b60038111156105c5576105c561099b565b81526020016003820154815250509050919050565b61060c6040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6001600160a01b0382166000908152602081905260409020548061066b5760405162461bcd60e51b81526020600482015260166024820152751859191c995cdcc81b9bdd081c9959da5cdd195c995960521b60448201526064016101f7565b600081815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff1660038111156106be576106be61099b565b60038111156106cf576106cf61099b565b81526020016002820160019054906101000a900460ff1660038111156106f7576106f761099b565b60038111156107085761070861099b565b8152602001600382015481525050915050919050565b600061072b84868461086b565b6001600160a01b03861660008181526020818152604091829020849055815160a08101835292835282018390529192509081018560038111156107705761077061099b565b81526020018460038111156107875761078761099b565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff1916908360038111156107ea576107ea61099b565b0217905550606082015160028201805461ff0019166101008360038111156108145761081461099b565b0217905550608082015181600301559050507f509fd4a7c7eeb48b99013f5e25a4899d7c8bed600405508511e7845fd25ed4f78186868560405161085b9493929190610aaa565b60405180910390a1949350505050565b60008060f08560038111156108825761088261099b565b6040516bffffffffffffffffffffffff19606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b8035600481106108ec57600080fd5b919050565b60006020828403121561090357600080fd5b61090c826108dd565b9392505050565b6000806040838503121561092657600080fd5b823591506020830135801515811461093d57600080fd5b809150509250929050565b6000806040838503121561095b57600080fd5b610964836108dd565b946020939093013593505050565b60006020828403121561098457600080fd5b81356001600160a01b038116811461090c57600080fd5b634e487b7160e01b600052602160045260246000fd5b600481106109cf57634e487b7160e01b600052602160045260246000fd5b50565b81516001600160a01b0316815260208083015190820152604082015160a08201906109fc816109b1565b60408301526060830151610a0f816109b1565b806060840152506080830151608083015292915050565b600060208284031215610a3857600080fd5b5035919050565b6001600160a01b03861681526020810185905260a08101610a5f856109b1565b846040830152610a6e846109b1565b6060820193909352608001529392505050565b6000600019821415610aa357634e487b7160e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b038416602082015260808101610ac9846109b1565b6040820193909352606001529291505056fea26469706673582212201d953a41436c84ebd32532447d0be8fd139c3f8eb25c7c4aa7b36833cc6448ac64736f6c634300080b0033"
  };

  public static final String BINARY =
      org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

  public static final String[] SM_BINARY_ARRAY = {
    "60806040523480156200001157600080fd5b5062000023336003600160006200002a565b5062000254565b60006200003984868462000187565b6001600160a01b03861660008181526020818152604091829020849055815160a0810183529283528201839052919250908101856003811115620000815762000081620001f7565b81526020018460038111156200009b576200009b620001f7565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff191690836003811115620001015762000101620001f7565b0217905550606082015160028201805461ff0019166101008360038111156200012e576200012e620001f7565b0217905550608082015181600301559050507fe107be78c95f4c23dfc84b3d95c3af239c828961144dd74c17eadc85f15670c5818686856040516200017794939291906200020d565b60405180910390a1949350505050565b60008060f0856003811115620001a157620001a1620001f7565b6040516001600160601b0319606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b63b95aa35560e01b600052602160045260246000fd5b8481526001600160a01b03841660208201526080810160048410620002425763b95aa35560e01b600052602160045260246000fd5b60408201939093526060015292915050565b610b1c80620002646000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80638c25a2251161005b5780638c25a225146100d35780639e97e65614610101578063d1d9b59814610161578063da7df57a1461018157600080fd5b80632c6faf72146100825780633001847c146100ab578063861bd55a146100be575b600080fd5b6100956100903660046108e8565b610194565b6040516100a2919061094f565b60405180910390f35b6100956100b93660046109a3565b6101d5565b6100d16100cc3660046109bc565b6102b9565b005b6100f36100e1366004610a05565b60026020526000908152604090205481565b6040519081526020016100a2565b61015061010f3660046109a3565b600160208190526000918252604090912080549181015460028201546003909201546001600160a01b0390931692909160ff80821692610100909204169085565b6040516100a2959493929190610a20565b6100f361016f3660046108e8565b60006020819052908152604090205481565b6100f361018f366004610a62565b610532565b6101c66040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6101cf826105e4565b92915050565b6102076040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b600082815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff16600381111561025a5761025a610918565b600381111561026b5761026b610918565b81526020016002820160019054906101000a900460ff16600381111561029357610293610918565b60038111156102a4576102a4610918565b81526020016003820154815250509050919050565b60006102c4336105e4565b90506001816060015160038111156102de576102de610918565b1461032a57604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b60448201526064015b60405180910390fd5b60038160400151600381111561034257610342610918565b1461038857604051636381e58960e11b815260206004820152601560248201527420b1b1b7bab73a1030b932903737ba1030b236b4b760591b6044820152606401610321565b600083815260016020526040902080546001600160a01b03166103e257604051636381e58960e11b81526020600482015260116024820152701058d8dbdd5b9d081b9bdd08195e1a5cdd607a1b6044820152606401610321565b60006002820154610100900460ff16600381111561040257610402610918565b1461044957604051636381e58960e11b8152602060048201526016602482015275496e76616c6964206163636f756e742073746174757360501b6044820152606401610321565b82156104e7576002818101805461010061ff001982161790915560009060ff16600381111561047a5761047a610918565b600381111561048b5761048b610918565b815260200190815260200160002060008154809291906104aa90610a8c565b90915550506040518481527fabedbd59572657258f3fe6088f4b6df89d49c9875645e9eecc9ad08c61185c2c9060200160405180910390a161052c565b60028101805461ff0019166102001790556040805185815290517f1c57d3f08b142c5f7dbda71ebc0333d98cbd6fcf4621d0969b780f43e240fe289181900360200190a15b50505050565b60008161057157604051636381e58960e11b815260206004820152600c60248201526b092dcecc2d8d2c840d0c2e6d60a31b6044820152606401610321565b33600081815260208190526040902054156105cf57604051636381e58960e11b815260206004820152601a60248201527f6164647265737320616c726561647920726567697374657265640000000000006044820152606401610321565b6105dc8185600086610729565b949350505050565b6106166040805160a0810182526000808252602082018190529091820190815260200160008152600060209091015290565b6001600160a01b0382166000908152602081905260409020548061067657604051636381e58960e11b81526020600482015260166024820152751859191c995cdcc81b9bdd081c9959da5cdd195c995960521b6044820152606401610321565b600081815260016020818152604092839020835160a08101855281546001600160a01b0316815292810154918301919091526002810154919290919083019060ff1660038111156106c9576106c9610918565b60038111156106da576106da610918565b81526020016002820160019054906101000a900460ff16600381111561070257610702610918565b600381111561071357610713610918565b8152602001600382015481525050915050919050565b6000610736848684610876565b6001600160a01b03861660008181526020818152604091829020849055815160a081018352928352820183905291925090810185600381111561077b5761077b610918565b815260200184600381111561079257610792610918565b8152602090810184905260008381526001808352604091829020845181546001600160a01b0319166001600160a01b039091161781559284015183820155908301516002830180549192909160ff1916908360038111156107f5576107f5610918565b0217905550606082015160028201805461ff00191661010083600381111561081f5761081f610918565b0217905550608082015181600301559050507fe107be78c95f4c23dfc84b3d95c3af239c828961144dd74c17eadc85f15670c5818686856040516108669493929190610ab5565b60405180910390a1949350505050565b60008060f085600381111561088d5761088d610918565b6040516bffffffffffffffffffffffff19606088901b16602082015260348101869052911b9150600090601090819060540160408051601f198184030181529190528051602090910120901b901c9190911795945050505050565b6000602082840312156108fa57600080fd5b81356001600160a01b038116811461091157600080fd5b9392505050565b63b95aa35560e01b600052602160045260246000fd5b6004811061094c5763b95aa35560e01b600052602160045260246000fd5b50565b81516001600160a01b0316815260208083015190820152604082015160a08201906109798161092e565b6040830152606083015161098c8161092e565b806060840152506080830151608083015292915050565b6000602082840312156109b557600080fd5b5035919050565b600080604083850312156109cf57600080fd5b82359150602083013580151581146109e657600080fd5b809150509250929050565b803560048110610a0057600080fd5b919050565b600060208284031215610a1757600080fd5b610911826109f1565b6001600160a01b03861681526020810185905260a08101610a408561092e565b846040830152610a4f8461092e565b6060820193909352608001529392505050565b60008060408385031215610a7557600080fd5b610a7e836109f1565b946020939093013593505050565b6000600019821415610aae5763b95aa35560e01b600052601160045260246000fd5b5060010190565b8481526001600160a01b038416602082015260808101610ad48461092e565b6040820193909352606001529291505056fea2646970667358221220706d2cfb7fc640b3b8e8400fa1d84854aac5b73a3c27d1d07c3773fa7aac23a264736f6c634300080b0033"
  };

  public static final String SM_BINARY =
      org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

  public static final String[] ABI_ARRAY = {
    "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"AccountApprovedEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"AccountDeniedEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"AccountRegisteredEvent\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":3,\"slot\":2,\"value\":[0]}],\"inputs\":[{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"\",\"type\":\"uint8\"}],\"name\":\"accountTypeNumbers\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[884465579,2351276581],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":0,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"addressToDid\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[3403841423,3520705944],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":0,\"value\":[0]},{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"bool\",\"name\":\"agree\",\"type\":\"bool\"}],\"name\":\"approve\",\"outputs\":[],\"selector\":[1321703735,2249971034],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"didToAccount\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"selector\":[3994103627,2660755030],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"getAccountByAddress\",\"outputs\":[{\"components\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"internalType\":\"struct AccountContract.AccountData\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[3102236652,745516914],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":1,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"name\":\"getAccountByDid\",\"outputs\":[{\"components\":[{\"internalType\":\"address\",\"name\":\"addr\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"},{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"enum AccountContract.AccountStatus\",\"name\":\"accountStatus\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"internalType\":\"struct AccountContract.AccountData\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[3978058946,805405820],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":0,\"value\":[0]}],\"inputs\":[{\"internalType\":\"enum AccountContract.AccountType\",\"name\":\"accountType\",\"type\":\"uint8\"},{\"internalType\":\"bytes32\",\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"register\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"did\",\"type\":\"bytes32\"}],\"selector\":[1762795861,3665687930],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"
  };

  public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

  public static final String FUNC_ACCOUNTTYPENUMBERS = "accountTypeNumbers";

  public static final String FUNC_ADDRESSTODID = "addressToDid";

  public static final String FUNC_APPROVE = "approve";

  public static final String FUNC_DIDTOACCOUNT = "didToAccount";

  public static final String FUNC_GETACCOUNTBYADDRESS = "getAccountByAddress";

  public static final String FUNC_GETACCOUNTBYDID = "getAccountByDid";

  public static final String FUNC_REGISTER = "register";

  public static final Event ACCOUNTAPPROVEDEVENT_EVENT =
      new Event("AccountApprovedEvent", Arrays.asList(new TypeReference<Bytes32>() {}));

  public static final Event ACCOUNTDENIEDEVENT_EVENT =
      new Event("AccountDeniedEvent", Arrays.asList(new TypeReference<Bytes32>() {}));

  public static final Event ACCOUNTREGISTEREDEVENT_EVENT =
      new Event(
          "AccountRegisteredEvent",
          Arrays.asList(
              new TypeReference<Bytes32>() {},
              new TypeReference<Address>() {},
              new TypeReference<Uint8>() {},
              new TypeReference<Bytes32>() {}));

  protected AccountContract(String contractAddress, Client client, CryptoKeyPair credential) {
    super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
  }

  public static String getBinary(CryptoSuite cryptoSuite) {
    return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
  }

  public static String getABI() {
    return ABI;
  }

  public static AccountContract load(
      String contractAddress, Client client, CryptoKeyPair credential) {
    return new AccountContract(contractAddress, client, credential);
  }

  public static AccountContract deploy(Client client, CryptoKeyPair credential)
      throws ContractException {
    return deploy(
        AccountContract.class,
        client,
        credential,
        getBinary(client.getCryptoSuite()),
        getABI(),
        null,
        null);
  }

  public List<AccountApprovedEventEventResponse> getAccountApprovedEventEvents(
      TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(ACCOUNTAPPROVEDEVENT_EVENT, transactionReceipt);
    ArrayList<AccountApprovedEventEventResponse> responses =
        new ArrayList<AccountApprovedEventEventResponse>(valueList.size());
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
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(ACCOUNTDENIEDEVENT_EVENT, transactionReceipt);
    ArrayList<AccountDeniedEventEventResponse> responses =
        new ArrayList<AccountDeniedEventEventResponse>(valueList.size());
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
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(ACCOUNTREGISTEREDEVENT_EVENT, transactionReceipt);
    ArrayList<AccountRegisteredEventEventResponse> responses =
        new ArrayList<AccountRegisteredEventEventResponse>(valueList.size());
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
    final Function function =
        new Function(
            FUNC_ACCOUNTTYPENUMBERS,
            Arrays.asList(new Uint8(param0)),
            Arrays.asList(new TypeReference<Uint256>() {}));
    return executeCallWithSingleValueReturn(function, BigInteger.class);
  }

  public byte[] addressToDid(String param0) throws ContractException {
    final Function function =
        new Function(
            FUNC_ADDRESSTODID,
            Arrays.asList(new Address(param0)),
            Arrays.asList(new TypeReference<Bytes32>() {}));
    return executeCallWithSingleValueReturn(function, byte[].class);
  }

  public TransactionReceipt approve(byte[] did, Boolean agree) {
    final Function function =
        new Function(
            FUNC_APPROVE,
            Arrays.asList(new Bytes32(did), new Bool(agree)),
            Collections.emptyList(),
            0);
    return executeTransaction(function);
  }

  public String approve(byte[] did, Boolean agree, TransactionCallback callback) {
    final Function function =
        new Function(
            FUNC_APPROVE,
            Arrays.asList(new Bytes32(did), new Bool(agree)),
            Collections.emptyList(),
            0);
    return asyncExecuteTransaction(function, callback);
  }

  public String getSignedTransactionForApprove(byte[] did, Boolean agree) {
    final Function function =
        new Function(
            FUNC_APPROVE,
            Arrays.asList(new Bytes32(did), new Bool(agree)),
            Collections.emptyList(),
            0);
    return createSignedTransaction(function);
  }

  public Tuple2<byte[], Boolean> getApproveInput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getInput().substring(10);
    final Function function =
        new Function(
            FUNC_APPROVE,
            Arrays.asList(),
            Arrays.asList(new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple2<byte[], Boolean>(
        (byte[]) results.get(0).getValue(), (Boolean) results.get(1).getValue());
  }

  public Tuple5<String, byte[], BigInteger, BigInteger, byte[]> didToAccount(byte[] param0)
      throws ContractException {
    final Function function =
        new Function(
            FUNC_DIDTOACCOUNT,
            Arrays.asList(new Bytes32(param0)),
            Arrays.asList(
                new TypeReference<Address>() {},
                new TypeReference<Bytes32>() {},
                new TypeReference<Uint8>() {},
                new TypeReference<Uint8>() {},
                new TypeReference<Bytes32>() {}));
    List<Type> results = executeCallWithMultipleValueReturn(function);
    return new Tuple5<String, byte[], BigInteger, BigInteger, byte[]>(
        (String) results.get(0).getValue(),
        (byte[]) results.get(1).getValue(),
        (BigInteger) results.get(2).getValue(),
        (BigInteger) results.get(3).getValue(),
        (byte[]) results.get(4).getValue());
  }

  public AccountContract.AccountData getAccountByAddress(String addr) throws ContractException {
    final Function function =
        new Function(
            FUNC_GETACCOUNTBYADDRESS,
            Arrays.asList(new Address(addr)),
            Arrays.asList(new TypeReference<AccountContract.AccountData>() {}));
    return executeCallWithSingleValueReturn(function, AccountContract.AccountData.class);
  }

  public AccountContract.AccountData getAccountByDid(byte[] did) throws ContractException {
    final Function function =
        new Function(
            FUNC_GETACCOUNTBYDID,
            Arrays.asList(new Bytes32(did)),
            Arrays.asList(new TypeReference<AccountContract.AccountData>() {}));
    return executeCallWithSingleValueReturn(function, AccountContract.AccountData.class);
  }

  public TransactionReceipt register(BigInteger accountType, byte[] hash) {
    final Function function =
        new Function(
            FUNC_REGISTER,
            Arrays.asList(new Uint8(accountType), new Bytes32(hash)),
            Collections.emptyList(),
            0);
    return executeTransaction(function);
  }

  public String register(BigInteger accountType, byte[] hash, TransactionCallback callback) {
    final Function function =
        new Function(
            FUNC_REGISTER,
            Arrays.asList(new Uint8(accountType), new Bytes32(hash)),
            Collections.emptyList(),
            0);
    return asyncExecuteTransaction(function, callback);
  }

  public String getSignedTransactionForRegister(BigInteger accountType, byte[] hash) {
    final Function function =
        new Function(
            FUNC_REGISTER,
            Arrays.asList(new Uint8(accountType), new Bytes32(hash)),
            Collections.emptyList(),
            0);
    return createSignedTransaction(function);
  }

  public Tuple2<BigInteger, byte[]> getRegisterInput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getInput().substring(10);
    final Function function =
        new Function(
            FUNC_REGISTER,
            Arrays.asList(),
            Arrays.asList(new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple2<BigInteger, byte[]>(
        (BigInteger) results.get(0).getValue(), (byte[]) results.get(1).getValue());
  }

  public Tuple1<byte[]> getRegisterOutput(TransactionReceipt transactionReceipt) {
    String data = transactionReceipt.getOutput();
    final Function function =
        new Function(
            FUNC_REGISTER, Arrays.asList(), Arrays.asList(new TypeReference<Bytes32>() {}));
    List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
    return new Tuple1<byte[]>((byte[]) results.get(0).getValue());
  }

  public static class AccountData extends StaticStruct {
    public String addr;

    public byte[] did;

    public BigInteger accountType;

    public BigInteger accountStatus;

    public byte[] hash;

    public AccountData(
        Address addr, Bytes32 did, Uint8 accountType, Uint8 accountStatus, Bytes32 hash) {
      super(addr, did, accountType, accountStatus, hash);
      this.addr = addr.getValue();
      this.did = did.getValue();
      this.accountType = accountType.getValue();
      this.accountStatus = accountStatus.getValue();
      this.hash = hash.getValue();
    }

    public AccountData(
        String addr, byte[] did, BigInteger accountType, BigInteger accountStatus, byte[] hash) {
      super(
          new Address(addr),
          new Bytes32(did),
          new Uint8(accountType),
          new Uint8(accountStatus),
          new Bytes32(hash));
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
