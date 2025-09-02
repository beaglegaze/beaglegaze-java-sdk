package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

public class UsageContractIntegrationTest extends IntegrationTestBase {

    private static final String CLIENT_ACCOUNT_PRIV_KEY = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    private static final String SMART_CONTRACT_OWNER_PRIV_KEY = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    /**
     * Tests that the funding is correctly associated with the client account.
     */
    @Test
    void shouldAssociateFundingWithClientAndOnlyPayOutOnConsume() throws Exception {
        UsageContract_sol_UsageContract contract = setupContractEnvironment();
        UsageContract_sol_UsageContract clientContract = loadContractAsClient(contract.getContractAddress());

        verifyInitialFunding(clientContract, BigInteger.valueOf(50));
        triggerPayableMethodCall();
        verifyFundingAfterConsumption(clientContract, BigInteger.valueOf(49));
    }

    private UsageContract_sol_UsageContract setupContractEnvironment() throws Exception {
        UsageContract_sol_UsageContract contract = deployContract(networkAddress);

        AsyncBatchProcessor asyncBatchProcessor = setupAsyncBatchProcessor(contract);
        MicroPaymentAspect.setProcessor(asyncBatchProcessor);

        fundClient(BigInteger.valueOf(50), contract.getContractAddress());

        return contract;
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

    private void verifyInitialFunding(UsageContract_sol_UsageContract contract, BigInteger expectedAmount)
            throws Exception {
        BigInteger clientFunding = contract.getClientFunding().send();
        assertThat(clientFunding, is(expectedAmount));
    }

    private void triggerPayableMethodCall() throws InterruptedException {
        Demo demo = new Demo();
        demo.greet("JUnit");
        Thread.sleep(2000);
    }

    private void verifyFundingAfterConsumption(UsageContract_sol_UsageContract contract, BigInteger expectedAmount)
            throws Exception {
        BigInteger clientFundingAfter = contract.getClientFunding().send();
        assertThat(clientFundingAfter, is(expectedAmount));
    }

    /**
     * Tests that developer's balance is tracked in the smart contract instead of
     * immediate payments, and can withdraw their balances themselves.
     */
    @Test
    void shouldTrackDeveloperBalancesAndAllowWithdrawal() throws Exception {
        UsageContract_sol_UsageContract contract = setupContractEnvironment();
        UsageContract_sol_UsageContract clientContract = loadContractAsClient(contract.getContractAddress());
        UsageContract_sol_UsageContract developerContract = loadContract(networkAddress, contract.getContractAddress(),
                SMART_CONTRACT_OWNER_PRIV_KEY);

        verifyInitialFunding(clientContract, BigInteger.valueOf(50));

        // Get initial developer balance - should be zero
        BigInteger initialDeveloperBalance = developerContract.getDeveloperBalance().send();
        assertThat(initialDeveloperBalance, is(BigInteger.ZERO));

        // Trigger payment
        triggerPayableMethodCall();

        // Verify developer balance is tracked (not paid out immediately)
        BigInteger developerBalance = developerContract.getDeveloperBalance().send();
        assertThat(developerBalance, is(BigInteger.ONE));

        // Verify developer can withdraw their balance
        developerContract.withdrawBalance().send();

        BigInteger finalDeveloperBalance = developerContract.getDeveloperBalance().send();
        assertThat(finalDeveloperBalance, is(BigInteger.ZERO));
    }

    

    private UsageContract_sol_UsageContract deployContract(String networkAddress) throws Exception {
        return UsageContract_sol_UsageContract.deploy(
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(SMART_CONTRACT_OWNER_PRIV_KEY),
                new DefaultGasProvider(), BigInteger.ZERO).send();
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
