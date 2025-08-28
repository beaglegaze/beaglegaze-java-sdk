package web3.beaglegaze;

/**
 * Event fired when a batch is ready to be processed.
 */
public record BatchReadyEvent(long batchSum) implements MeteringEvent {

}
