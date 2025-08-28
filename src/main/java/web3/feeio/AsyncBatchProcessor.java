package web3.beaglegaze;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Async PaymentProcessor class that uses observer pattern for event-driven
 * processing.
 */
public class AsyncBatchProcessor {

    private final List<MeteringEventObserver> observers = new ArrayList<>();
    private final BatchMode batchMode;
    private final ExecutorService asyncExecutor = ForkJoinPool.commonPool();

    private static final Logger LOG = LoggerFactory.getLogger(AsyncBatchProcessor.class);

    private long batchSum = 0;

    public AsyncBatchProcessor(BatchMode batchMode) {
        this.batchMode = batchMode;
    }

    /**
     * Adds an observer to receive metering events.
     */
    public void addObserver(MeteringEventObserver observer) {
        observers.add(observer);
    }

    /**
     * Registers a call asynchronously and returns a CompletableFuture.
     */
    public CompletableFuture<Void> registerCallAsync(long pricePerInvocation) {
        addToCurrentBatch(pricePerInvocation);

        if (shouldProcessBatch()) {
            return processBatchAsync();
        }

        return CompletableFuture.completedFuture(null);
    }

    private synchronized void addToCurrentBatch(long pricePerInvocation) {
        batchSum += pricePerInvocation;
    }

    private synchronized boolean shouldProcessBatch() {
        return batchMode.hit();
    }

    private CompletableFuture<Void> processBatchAsync() {
        LOG.info("Processing batch with sum {}...", batchSum);
        long currentBatchSum = getCurrentBatchSum();
        resetBatch();
        return notifyObserversAsync(new BatchReadyEvent(currentBatchSum));

    }

    private synchronized long getCurrentBatchSum() {
        return batchSum;
    }

    private synchronized void resetBatch() {
        batchSum = 0;
    }

    private CompletableFuture<Void> notifyObserversAsync(MeteringEvent event) {

        return CompletableFuture.runAsync(() -> {
            for (MeteringEventObserver observer : observers) {
                observer.handle(event);
            }
        }, asyncExecutor);
    }

    public boolean isInErrorState() {
        return observers.stream().anyMatch(MeteringEventObserver::isInErrorState);
    }
}
