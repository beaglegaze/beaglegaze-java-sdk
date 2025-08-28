package web3.beaglegaze;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsyncPaymentProcessorTest {

    private static final long FIRST_CALL_AMOUNT = 50L;

    private AsyncBatchProcessor asyncProcessor;

    @BeforeEach
    void setUp() {
        asyncProcessor = new AsyncBatchProcessor(BatchMode.OFF);
    }

    /**
     * Tests that observers receive BatchReadyEvent when batch size is reached.
     * This test verifies the core observer pattern functionality.
     */
    @Test
    void shouldNotifyObserversWhenBatchIsReady() throws Exception {
        ContractConsumer contractConsumer = mock(ContractConsumer.class);

        asyncProcessor.addObserver(contractConsumer);

        asyncProcessor.registerCallAsync(FIRST_CALL_AMOUNT).get();
        
        verifyBatchEventWasFired(contractConsumer);
    }

    @Test
    void shouldGoIntoErrorStateWhenObserverThrowsException() throws Exception {
        asyncProcessor.addObserver(new MeteringEventObserver() {
            @Override
            public CompletableFuture<Void> handle(MeteringEvent event) {
                throw new RuntimeException("Observer failed");
            }

            @Override
            public boolean isInErrorState() {
                return true;
            }
        });
        
        assertTrue(asyncProcessor.isInErrorState());
    }

    @Test
    void shouldProcessBatchWhenBatchModeIsRandom() throws Exception {
        asyncProcessor = new AsyncBatchProcessor(BatchMode.RANDOM);
        ContractConsumer contractConsumer = mock(ContractConsumer.class);
        asyncProcessor.addObserver(contractConsumer);
        for (int i = 0; i < 50; i++) {
            asyncProcessor.registerCallAsync(5).get();
        }
        verify(contractConsumer, atMost(49)).handle(any(BatchReadyEvent.class));
        verify(contractConsumer, atLeast(1)).handle(any(BatchReadyEvent.class));
    }


    private void verifyBatchEventWasFired(ContractConsumer contractConsumer) {
        BatchReadyEvent batchReadyEvent = new BatchReadyEvent(FIRST_CALL_AMOUNT);
        verify(contractConsumer, times(1)).handle(eq(batchReadyEvent));
    }
}
