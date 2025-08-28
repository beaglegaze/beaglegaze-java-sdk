package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

/**
 * Integration test for the async PaymentProcessor using observer pattern.
 * This test verifies that the payment processor correctly handles asynchronous
 * batch processing and unblocking through event observers.
 */
public class AsyncPaymentProcessorIntegrationTest extends IntegrationTestBase {

    private AsyncBatchProcessor asyncProcessor;
    private UsageContract_sol_UsageContract ownerLoadedContract;

    @BeforeEach
    void setUp() throws Exception {
        ownerLoadedContract = UsageContract_sol_UsageContract.deploy(
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(SMART_CONTRACT_OWNER),
                new DefaultGasProvider()).send();
        asyncProcessor = new AsyncBatchProcessor(BatchMode.OFF);
    }

    /**
     * Tests that batch processing events are handled asynchronously by observers.
     */
    @Test
    void shouldProcessBatchAsynchronouslyThroughObserver() throws Exception {
        final int INITIAL_BALANCE = 200;
        fundClient(BigInteger.valueOf(INITIAL_BALANCE), ownerLoadedContract.getContractAddress());

        SmartContract contract = setupAsyncProcessor();

        CompletableFuture<Void> call1 = asyncProcessor.registerCallAsync(50);
        CompletableFuture<Void> call2 = asyncProcessor.registerCallAsync(50);

        CompletableFuture.allOf(call1, call2).get(5, TimeUnit.SECONDS);

        final int EXPECTED_BALANCE = 100;
        assertThat(contract.getClientFunding(), is(BigInteger.valueOf(EXPECTED_BALANCE)));
    }

    private SmartContract setupAsyncProcessor() throws Exception {
        SmartContract contract = new SmartContract(
                ownerLoadedContract.getContractAddress(), networkAddress, CLIENT_ACCOUNT, 10);
        ContractConsumer contractConsumer = new ContractConsumer(contract);
        asyncProcessor.addObserver(contractConsumer);
        return contract;
    }
}
