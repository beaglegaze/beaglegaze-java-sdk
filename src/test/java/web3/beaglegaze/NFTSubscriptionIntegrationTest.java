package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

public class NFTSubscriptionIntegrationTest extends IntegrationTestBase {

    private static final String CLIENT_ACCOUNT_PRIV_KEY = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    private static final String SMART_CONTRACT_OWNER_PRIV_KEY = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    @DisplayName("Should purchase NFT subscription")
    void shouldPurchaseNFTSubscription() throws Exception {
        UsageContract_sol_UsageContract contract = deployContract(networkAddress);
        UsageContract_sol_UsageContract clientContract = loadContractAsClient(contract.getContractAddress());

        BigInteger paymentAmount = BigInteger.valueOf(1000000000000000000L); // 1 ETH in wei

        TransactionReceipt receipt = clientContract.purchaseSubscription(paymentAmount).send();
        
        assertThat(receipt.isStatusOK(), is(true));
        
        boolean hasValidSubscription = clientContract.hasValidSubscription().send();
        assertThat(hasValidSubscription, is(true));
    }

    @Test
    @DisplayName("Should allow method calls with NFT subscription without client funding")
    void shouldAllowMethodCallsWithNFTSubscriptionWithoutClientFunding() throws Exception {
        UsageContract_sol_UsageContract contract = deployContract(networkAddress);
        UsageContract_sol_UsageContract clientContract = loadContractAsClient(contract.getContractAddress());

        // Setup async processor to handle method calls
        AsyncBatchProcessor asyncProcessor = setupAsyncBatchProcessor(contract);
        MicroPaymentAspect.setProcessor(asyncProcessor);

        // Purchase NFT subscription
        BigInteger paymentAmount = BigInteger.valueOf(1000000000000000000L); // 1 ETH in wei
        clientContract.purchaseSubscription(paymentAmount).send();

        // Verify subscription is active
        boolean hasValidSubscription = clientContract.hasValidSubscription().send();
        assertThat(hasValidSubscription, is(true));

        // Verify client has no funding in account (should be zero)
        BigInteger initialClientFunding = clientContract.getClientFunding().send();
        assertThat(initialClientFunding, is(BigInteger.ZERO));

        // Call Demo methods multiple times - should succeed despite no funding
        Demo demo = new Demo();
        for (int i = 0; i < 5; i++) {
            demo.greet("NFT Call " + i);
            Thread.sleep(1000);
        }

        // Verify client funding is still zero (no deduction from NFT calls)
        BigInteger finalClientFunding = clientContract.getClientFunding().send();
        assertThat(finalClientFunding, is(BigInteger.ZERO));
    }

    private AsyncBatchProcessor setupAsyncBatchProcessor(UsageContract_sol_UsageContract contract) throws Exception {
        AsyncBatchProcessor asyncBatchProcessor = new AsyncBatchProcessor(BatchMode.OFF);
        SmartContract clientContract = new SmartContract(
                contract.getContractAddress(), networkAddress, CLIENT_ACCOUNT_PRIV_KEY, 10);
        ContractConsumer contractConsumer = new ContractConsumer(clientContract);
        asyncBatchProcessor.addObserver(contractConsumer);
        return asyncBatchProcessor;
    }

    private UsageContract_sol_UsageContract loadContractAsClient(String contractAddress) {
        return loadContract(networkAddress, contractAddress, CLIENT_ACCOUNT_PRIV_KEY);
    }

    private UsageContract_sol_UsageContract deployContract(String networkAddress) throws Exception {
        return UsageContract_sol_UsageContract.deploy(
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(SMART_CONTRACT_OWNER_PRIV_KEY),
                new DefaultGasProvider(),
                BigInteger.ZERO).send(); // Default subscription price
    }

    private UsageContract_sol_UsageContract loadContract(String networkAddress, String contractAddress,
            String account) {
        return UsageContract_sol_UsageContract.load(
                contractAddress,
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(account),
                new DefaultGasProvider());
    }
}
