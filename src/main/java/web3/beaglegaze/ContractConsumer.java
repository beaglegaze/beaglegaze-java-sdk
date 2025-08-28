package web3.beaglegaze;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractConsumer implements MeteringEventObserver {

    private final SmartContract contract;
    private boolean blocked = false;

    private static final Logger LOG = LoggerFactory.getLogger(ContractConsumer.class);

    public ContractConsumer(SmartContract smartContract) throws Exception {
        contract = smartContract;
    }

    @Override
    public CompletableFuture<Void> handle(MeteringEvent event) {
        if (isBlockedAndBatchReady(event)) {
            return handleBlockedState((BatchReadyEvent) event);
        }

        if (blocked) {
            throw new IllegalStateException("Refund the smart contract to continue using this library.");
        }

        if (event instanceof BatchReadyEvent batchEvent) {
            consumeFromContract(batchEvent);
        }
        return CompletableFuture.completedFuture(null);
    }

    private boolean isBlockedAndBatchReady(MeteringEvent event) {
        return blocked && event instanceof BatchReadyEvent;
    }

    private CompletableFuture<Void> handleBlockedState(BatchReadyEvent batchEvent) {
        attemptUnblocking(batchEvent.batchSum());
        if (blocked) {
            throw new IllegalStateException("Refund the smart contract to continue using this library.");
        }
        // If we successfully unblocked, consume from the contract
        try {
            consumeFromContract(batchEvent);
        } catch (Exception e) {
            LOG.error("Failed to consume from contract.", e);
            return CompletableFuture.failedFuture(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    private void consumeFromContract(BatchReadyEvent batchEvent) {
        try {
            contract.consume(BigInteger.valueOf(batchEvent.batchSum()));
        } catch (Exception e) {
            blocked = true;
            LOG.error(
                    "Failed to consume from contract, switching to 'blocked' state. Refund the smart contract to continue using this library.",
                    e);
            throw new RuntimeException("Failed to consume from contract", e);
        }
    }

    private void attemptUnblocking(long requiredAmount) {
        LOG.debug("Attempting to unblock contract consumer...");
        try {
            BigInteger availableFunds = contract.getClientFunding();
            BigInteger required = BigInteger.valueOf(requiredAmount);

            logUnblockingAttempt(availableFunds, required);

            if (hasSufficientFunds(availableFunds, required)) {
                unblockConsumer(availableFunds, required);
            } else {
                logInsufficientFunds(availableFunds, required);
            }
        } catch (Exception e) {
            LOG.warn("Failed to check client funding while attempting to unblock.", e);
        }
    }

    private void logUnblockingAttempt(BigInteger availableFunds, BigInteger required) {
        LOG.debug("Available funds: {}, Required amount: {}", availableFunds, required);
    }

    private boolean hasSufficientFunds(BigInteger availableFunds, BigInteger required) {
        return availableFunds.compareTo(required) >= 0;
    }

    private void unblockConsumer(BigInteger availableFunds, BigInteger required) {
        blocked = false;
        LOG.info("Contract consumer unblocked with sufficient funds: {} (required: {})", availableFunds, required);
    }

    private void logInsufficientFunds(BigInteger availableFunds, BigInteger required) {
        LOG.warn("Not enough funding available, remaining blocked. Available funds: {}, Required: {}", availableFunds,
                required);
    }

    @Override
    public boolean isInErrorState() {
        return blocked;
    }

}
