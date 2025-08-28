package web3.beaglegaze;

import java.util.concurrent.CompletableFuture;

/**
 * Observer interface for handling metering events.
 */
public interface MeteringEventObserver {
    CompletableFuture<Void> handle(MeteringEvent event);

    boolean isInErrorState();
}
