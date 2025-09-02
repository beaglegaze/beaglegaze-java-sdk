package web3.beaglegaze;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContractConsumerNFTTest {

    private static final long BATCH_AMOUNT = 100L;

    @Mock
    private SmartContract mockContract;

    private ContractConsumer contractConsumer;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.contractConsumer = new ContractConsumer(mockContract);
    }

    /**
     * Test that the contract consumer checks for a valid NFT subscription before
     * consuming from the contract.
     */
    @Test
    void shouldCheckForNFTSubscriptionBeforeConsumingFromContract() throws Exception {
        when(mockContract.hasValidSubscription()).thenReturn(true);
        when(mockContract.consume(any(BigInteger.class))).thenReturn(true);

        BatchReadyEvent batchEvent = new BatchReadyEvent(BATCH_AMOUNT);

        CompletableFuture<Void> result = contractConsumer.handle(batchEvent);

        result.join();
        verify(mockContract, times(1)).hasValidSubscription();
        verify(mockContract, times(0)).consume(BigInteger.valueOf(BATCH_AMOUNT));

        assertFalse(contractConsumer.isInErrorState());
    }
}
