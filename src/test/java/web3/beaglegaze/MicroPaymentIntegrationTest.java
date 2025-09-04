package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

/**
 * Integration test for the micro-payment functionality using a smart contract.
 * This test verifies that the micro-payment system correctly consumes funds
 * from the client account based on method annotations and handles batching.
 */
class MicroPaymentIntegrationTest extends IntegrationTestBase {

    private UsageContract_sol_UsageContract contract;

    @BeforeEach
    void setupEach() throws Exception {
        contract = deployContract();
        AsyncBatchProcessor asyncProcessor = setupProcessor();
        MicroPaymentAspect.setProcessor(asyncProcessor);
    }

    /**
     * Tests that the tracked method consumes the correct price from the
     * annotation.
     */
    @Test
    void shouldConsumeCorrectPriceFromAnnotation() throws Exception {
        fundClient(BigInteger.valueOf(10), contract.getContractAddress());

        Demo trackedClass = new Demo();
        call(trackedClass, 10);

        Thread.sleep(5000);

        assertThat(getClientFunding(), is(BigInteger.valueOf(0)));
    }

    /**
     * Tests that the tracked method is blocked after a consume failure.
     * This simulates a scenario where the client does not have enough funds
     * to pay for the method call, and the method should throw an exception.
     * 
     * But, although the client has not enough funding from the beginning, the first
     * call will succeed
     * due to the async nature or processing. In a sense, payments are served on a
     * "best effort" basis.
     * The second call will fail.
     */
    @Test
    void shouldBlockTrackedMethodAfterConsumeFailure() throws Exception {
        Demo trackedClass = new Demo();

        trackedClass.greet("Success");
        Thread.sleep(1000);

        assertExceptionOnInvocation(trackedClass);
    }

    /**
     * Tests that the tracked method can be unblocked after a refund.
     * This simulates a scenario where the client adds funds to their account
     * after a failed payment attempt, allowing the method to be called again.n
     */
    @Test
    void shouldUnblockTrackedMethodAfterRefunding() throws Exception {
        Demo trackedClass = new Demo();
        trackedClass.greet("Success");
        Thread.sleep(1000);

        assertExceptionOnInvocation(trackedClass);

        fundClient(BigInteger.valueOf(10), contract.getContractAddress());
        Thread.sleep(1000);

        // the next invocation will still fail, because the async processor
        // has not yet processed the refund
        // and the method is still blocked
        assertExceptionOnInvocation(trackedClass);

        Thread.sleep(1000);

        // ... but eventually the method will be unblocked
        // and the next invocation will succeed
        trackedClass.greet("Unblocked");
    }

    private void assertExceptionOnInvocation(Demo trackedClass) {
        Assertions.assertThrows(RuntimeException.class, () -> {
            trackedClass.greet("Failure");
        });
    }

    private AsyncBatchProcessor setupProcessor() throws Exception {
        SmartContract smartContract = new SmartContract(
                contract.getContractAddress(), networkAddress, CLIENT_ACCOUNT, 10);
        ContractConsumer contractConsumer = new ContractConsumer(smartContract);
        AsyncBatchProcessor asyncProcessor = new AsyncBatchProcessor(BatchMode.OFF);
        asyncProcessor.addObserver(contractConsumer);
        return asyncProcessor;
    }

    private UsageContract_sol_UsageContract deployContract() throws Exception {
        return UsageContract_sol_UsageContract
                .deploy(Web3j.build(new HttpService(networkAddress)),
                        Credentials.create(SMART_CONTRACT_OWNER),
                        new DefaultGasProvider(), BigInteger.ZERO)
                .send();
    }

    private UsageContract_sol_UsageContract loadContractForClient() {
        return UsageContract_sol_UsageContract.load(
                contract.getContractAddress(),
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(CLIENT_ACCOUNT),
                new DefaultGasProvider());
    }

    private BigInteger getClientFunding() throws Exception {
        return loadContractForClient().getClientFunding().send();
    }

    private void call(Demo trackedClass, int numberOfInvocations) {
        for (int i = 0; i < numberOfInvocations; i++) {
            trackedClass.greet("JUnit" + i);
        }
    }
}
