package web3.beaglegaze;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class UsageContract_sol_UsageContract extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b50335f81815260208190526040808220805460ff19166001908117909155805480820182559083527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b03191684179055517f64432bf33d97c6e03519a3a2880f032d7d261b5a7e3119fbfeaef601431d27499190a2610ef08061009a5f395ff3fe6080604052600436106100bf575f3560e01c80636d9fb2871161007c57806383c67d9c1161005757806383c67d9c146101d557806384b7964f146101f4578063b60d42881461022b578063c26d613f14610233575f5ffd5b80636d9fb2871461017e57806377d626da1461019257806379a8945c146101a6575f5ffd5b806304fc3707146100c35780631f7692f514610101578063483f31ab146101155780635b01c76c146101345780635fd8c7101461015457806365058f4c1461016a575b5f5ffd5b3480156100ce575f5ffd5b506100ee6100dd366004610d8d565b60036020525f908152604090205481565b6040519081526020015b60405180910390f35b34801561010c575f5ffd5b506100ee61026a565b348015610120575f5ffd5b506100ee61012f366004610dad565b6102f0565b34801561013f575f5ffd5b50335f908152600360205260409020546100ee565b34801561015f575f5ffd5b506101686104f9565b005b348015610175575f5ffd5b50610168610654565b348015610189575f5ffd5b506101686107bb565b34801561019d575f5ffd5b506101686107fc565b3480156101b1575f5ffd5b50335f9081526020819052604090205460ff165b60405190151581526020016100f8565b3480156101e0575f5ffd5b506101686101ef366004610dc4565b6108d5565b3480156101ff575f5ffd5b506101c561020e366004610d8d565b6001600160a01b03165f9081526002602052604090205460ff1690565b610168610ab4565b34801561023e575f5ffd5b506101c561024d366004610d8d565b6001600160a01b03165f9081526005602052604090205460ff1690565b335f9081526020819052604081205460ff166102dd5760405162461bcd60e51b815260206004820152602760248201527f4f6e6c7920646576656c6f706572732063616e20636865636b2074686569722060448201526662616c616e636560c81b60648201526084015b60405180910390fd5b50335f9081526004602052604090205490565b335f8181526003602090815260408083205490519081529192917f82f83cec6dcf1c03c768bb7cbf83b5be007525fd3b9b7471d309e2017ac040f4910160405180910390a2335f908152600360205260409020548211156103935760405162461bcd60e51b815260206004820152601b60248201527f496e73756666696369656e7420636c69656e742066756e64696e67000000000060448201526064016102d4565b6001546103e25760405162461bcd60e51b815260206004820152601860248201527f4e6f20646576656c6f706572732072656769737465726564000000000000000060448201526064016102d4565b335f9081526003602052604081208054849290610400908490610e11565b90915550506001545f906104149084610e3e565b6001549091505f906104269085610e51565b90505f5b60015481101561048b578260045f6001848154811061044b5761044b610e64565b5f9182526020808320909101546001600160a01b031683528201929092526040018120805490919061047e908490610e78565b909155505060010161042a565b5080156104e2578060045f60015f815481106104a9576104a9610e64565b5f9182526020808320909101546001600160a01b03168352820192909252604001812080549091906104dc908490610e78565b90915550505b5050335f9081526003602052604090205492915050565b335f9081526020819052604090205460ff166105575760405162461bcd60e51b815260206004820152601c60248201527f4f6e6c7920646576656c6f706572732063616e2077697468647261770000000060448201526064016102d4565b335f90815260046020526040902054806105ac5760405162461bcd60e51b81526020600482015260166024820152754e6f2062616c616e636520746f20776974686472617760501b60448201526064016102d4565b335f818152600460205260408082208290555190919083908381818185875af1925050503d805f81146105fa576040519150601f19603f3d011682016040523d82523d5f602084013e6105ff565b606091505b50509050806106505760405162461bcd60e51b815260206004820152601a60248201527f4661696c656420746f2077697468647261772062616c616e636500000000000060448201526064016102d4565b5050565b335f9081526002602052604090205460ff166106b25760405162461bcd60e51b815260206004820152601f60248201527f4f6e6c7920636c69656e74732063616e2072657175657374207061796f75740060448201526064016102d4565b335f9081526003602052604090205461070d5760405162461bcd60e51b815260206004820152601d60248201527f4e6f2066756e647320617661696c61626c6520666f72207061796f757400000060448201526064016102d4565b335f8181526003602052604080822054905190929083908381818185875af1925050503d805f811461075a576040519150601f19603f3d011682016040523d82523d5f602084013e61075f565b606091505b50509050806107a85760405162461bcd60e51b815260206004820152601560248201527411985a5b1959081d1bc81cd95b99081c185e5bdd5d605a1b60448201526064016102d4565b5050335f90815260036020526040812055565b335f81815260026020526040808220805460ff19166001179055517f2ce5ee634dc8e65bbbad43d4ca6b4413f261b6a5dd89d79f5db67892e4512d1a9190a2565b335f9081526020819052604090205460ff161561085b5760405162461bcd60e51b815260206004820152601f60248201527f416c7265616479207265676973746572656420617320646576656c6f7065720060448201526064016102d4565b335f9081526005602052604090205460ff16156108ba5760405162461bcd60e51b815260206004820152601c60248201527f526567697374726174696f6e20616c72656164792070656e64696e670000000060448201526064016102d4565b335f908152600560205260409020805460ff19166001179055565b335f9081526020819052604090205460ff166109335760405162461bcd60e51b815260206004820152601860248201527f4f6e6c7920646576656c6f706572732063616e20766f7465000000000000000060448201526064016102d4565b6001600160a01b0382165f9081526005602052604090205460ff166109ad5760405162461bcd60e51b815260206004820152602a60248201527f4e6f2070656e64696e6720726567697374726174696f6e20666f722074686973604482015269103232bb32b637b832b960b11b60648201526084016102d4565b6001600160a01b0382165f90815260076020908152604080832033845290915290205460ff1615610a205760405162461bcd60e51b815260206004820181905260248201527f416c726561647920766f74656420666f72207468697320646576656c6f70657260448201526064016102d4565b6001600160a01b0382165f81815260066020908152604080832033808552908352818420805487151560ff1991821617909155858552600784528285209185529083528184208054909116600117905592825260089052908120805491610a8683610e8b565b90915550506001546001600160a01b0383165f90815260086020526040902054036106505761065082610b62565b5f3411610af35760405162461bcd60e51b815260206004820152600d60248201526c139bc8199d5b991cc81cd95b9d609a1b60448201526064016102d4565b335f908152600260209081526040808320805460ff19166001179055600390915281208054349290610b26908490610e78565b909155505060405134815233907f5af8184bef8e4b45eb9f6ed7734d04da38ced226495548f46e0c8ff8d7d9a5249060200160405180910390a2565b5f805b600154811015610bdf576001600160a01b0383165f9081526006602052604081206001805491929184908110610b9d57610b9d610e64565b5f9182526020808320909101546001600160a01b0316835282019290925260400190205460ff1615610bd75781610bd381610e8b565b9250505b600101610b65565b506001545f90610bf0836002610ea3565b1190508015610c81576001600160a01b0383165f81815260208190526040808220805460ff19166001908117909155805480820182559083527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b03191684179055517f64432bf33d97c6e03519a3a2880f032d7d261b5a7e3119fbfeaef601431d27499190a25b6001600160a01b0383165f908152600560209081526040808320805460ff19169055600890915281208190555b600154811015610d6c576001600160a01b0384165f9081526006602052604081206001805491929184908110610ce657610ce6610e64565b5f9182526020808320909101546001600160a01b0390811684528382019490945260409283018220805460ff191690559287168152600790925281206001805491929184908110610d3957610d39610e64565b5f9182526020808320909101546001600160a01b031683528201929092526040019020805460ff19169055600101610cae565b50505050565b80356001600160a01b0381168114610d88575f5ffd5b919050565b5f60208284031215610d9d575f5ffd5b610da682610d72565b9392505050565b5f60208284031215610dbd575f5ffd5b5035919050565b5f5f60408385031215610dd5575f5ffd5b610dde83610d72565b915060208301358015158114610df2575f5ffd5b809150509250929050565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610e2457610e24610dfd565b92915050565b634e487b7160e01b5f52601260045260245ffd5b5f82610e4c57610e4c610e2a565b500490565b5f82610e5f57610e5f610e2a565b500690565b634e487b7160e01b5f52603260045260245ffd5b80820180821115610e2457610e24610dfd565b5f60018201610e9c57610e9c610dfd565b5060010190565b8082028115828204841417610e2457610e24610dfd56fea2646970667358221220309c335a5e2b7fd88b7c8c29d3856bb94922c13f511edb952708c85d3f9bd2b864736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CLIENTFUNDING = "clientFunding";

    public static final String FUNC_CONSUME = "consume";

    public static final String FUNC_FUND = "fund";

    public static final String FUNC_GETCLIENTFUNDING = "getClientFunding";

    public static final String FUNC_GETDEVELOPERBALANCE = "getDeveloperBalance";

    public static final String FUNC_HASPENDINGREGISTRATIONREQUEST = "hasPendingRegistrationRequest";

    public static final String FUNC_ISCLIENT = "isClient";

    public static final String FUNC_ISDEVELOPER = "isDeveloper";

    public static final String FUNC_REGISTERCLIENT = "registerClient";

    public static final String FUNC_REQUESTDEVELOPERREGISTRATION = "requestDeveloperRegistration";

    public static final String FUNC_REQUESTPAYOUT = "requestPayout";

    public static final String FUNC_VOTEFORDEVELOPER = "voteForDeveloper";

    public static final String FUNC_WITHDRAWBALANCE = "withdrawBalance";

    public static final Event CLIENTREGISTERED_EVENT = new Event("ClientRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event CONSUMED_EVENT = new Event("Consumed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEVELOPERREGISTERED_EVENT = new Event("DeveloperRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event FUNDED_EVENT = new Event("Funded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected UsageContract_sol_UsageContract(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UsageContract_sol_UsageContract(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UsageContract_sol_UsageContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UsageContract_sol_UsageContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ClientRegisteredEventResponse> getClientRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLIENTREGISTERED_EVENT, transactionReceipt);
        ArrayList<ClientRegisteredEventResponse> responses = new ArrayList<ClientRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClientRegisteredEventResponse typedResponse = new ClientRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClientRegisteredEventResponse getClientRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLIENTREGISTERED_EVENT, log);
        ClientRegisteredEventResponse typedResponse = new ClientRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClientRegisteredEventResponse> clientRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClientRegisteredEventFromLog(log));
    }

    public Flowable<ClientRegisteredEventResponse> clientRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLIENTREGISTERED_EVENT));
        return clientRegisteredEventFlowable(filter);
    }

    public static List<ConsumedEventResponse> getConsumedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONSUMED_EVENT, transactionReceipt);
        ArrayList<ConsumedEventResponse> responses = new ArrayList<ConsumedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ConsumedEventResponse typedResponse = new ConsumedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ConsumedEventResponse getConsumedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONSUMED_EVENT, log);
        ConsumedEventResponse typedResponse = new ConsumedEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ConsumedEventResponse> consumedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getConsumedEventFromLog(log));
    }

    public Flowable<ConsumedEventResponse> consumedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONSUMED_EVENT));
        return consumedEventFlowable(filter);
    }

    public static List<DeveloperRegisteredEventResponse> getDeveloperRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEVELOPERREGISTERED_EVENT, transactionReceipt);
        ArrayList<DeveloperRegisteredEventResponse> responses = new ArrayList<DeveloperRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DeveloperRegisteredEventResponse typedResponse = new DeveloperRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.developer = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DeveloperRegisteredEventResponse getDeveloperRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEVELOPERREGISTERED_EVENT, log);
        DeveloperRegisteredEventResponse typedResponse = new DeveloperRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.developer = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DeveloperRegisteredEventResponse> developerRegisteredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDeveloperRegisteredEventFromLog(log));
    }

    public Flowable<DeveloperRegisteredEventResponse> developerRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEVELOPERREGISTERED_EVENT));
        return developerRegisteredEventFlowable(filter);
    }

    public static List<FundedEventResponse> getFundedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNDED_EVENT, transactionReceipt);
        ArrayList<FundedEventResponse> responses = new ArrayList<FundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundedEventResponse typedResponse = new FundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FundedEventResponse getFundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNDED_EVENT, log);
        FundedEventResponse typedResponse = new FundedEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FundedEventResponse> fundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFundedEventFromLog(log));
    }

    public Flowable<FundedEventResponse> fundedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDED_EVENT));
        return fundedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> clientFunding(String param0) {
        final Function function = new Function(FUNC_CLIENTFUNDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> consume(BigInteger amount) {
        final Function function = new Function(
                FUNC_CONSUME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> fund(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_FUND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> getClientFunding() {
        final Function function = new Function(FUNC_GETCLIENTFUNDING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getDeveloperBalance() {
        final Function function = new Function(FUNC_GETDEVELOPERBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> hasPendingRegistrationRequest(String developer) {
        final Function function = new Function(FUNC_HASPENDINGREGISTRATIONREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, developer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isClient(String user) {
        final Function function = new Function(FUNC_ISCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isDeveloper() {
        final Function function = new Function(FUNC_ISDEVELOPER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerClient() {
        final Function function = new Function(
                FUNC_REGISTERCLIENT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestDeveloperRegistration() {
        final Function function = new Function(
                FUNC_REQUESTDEVELOPERREGISTRATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestPayout() {
        final Function function = new Function(
                FUNC_REQUESTPAYOUT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> voteForDeveloper(String developer,
            Boolean approve) {
        final Function function = new Function(
                FUNC_VOTEFORDEVELOPER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, developer), 
                new org.web3j.abi.datatypes.Bool(approve)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawBalance() {
        final Function function = new Function(
                FUNC_WITHDRAWBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static UsageContract_sol_UsageContract load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UsageContract_sol_UsageContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UsageContract_sol_UsageContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UsageContract_sol_UsageContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UsageContract_sol_UsageContract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UsageContract_sol_UsageContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UsageContract_sol_UsageContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UsageContract_sol_UsageContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UsageContract_sol_UsageContract> deploy(Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UsageContract_sol_UsageContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<UsageContract_sol_UsageContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UsageContract_sol_UsageContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<UsageContract_sol_UsageContract> deploy(Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UsageContract_sol_UsageContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<UsageContract_sol_UsageContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UsageContract_sol_UsageContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ClientRegisteredEventResponse extends BaseEventResponse {
        public String client;
    }

    public static class ConsumedEventResponse extends BaseEventResponse {
        public String client;

        public BigInteger amount;
    }

    public static class DeveloperRegisteredEventResponse extends BaseEventResponse {
        public String developer;
    }

    public static class FundedEventResponse extends BaseEventResponse {
        public String client;

        public BigInteger amount;
    }
}
