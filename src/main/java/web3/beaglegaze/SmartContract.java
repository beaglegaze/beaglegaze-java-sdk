package web3.beaglegaze;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 * SmartContract class that interacts with the deployed UsageContract smart
 * contract.
 * It provides methods to consume funds and retrieve client funding.
 * This class is thread-safe and ensures that transactions are processed in a
 * synchronized manner.
 */
public class SmartContract {

    private final UsageContract_sol_UsageContract contract;
    private final Object transactionLock = new Object();

    private static final Logger LOG = LoggerFactory.getLogger(SmartContract.class);
    private int lowFundingThreshold;

    public SmartContract(String contractAddress, String networkAddress, String clientPrivateKey,
            int lowFundingThreshold) throws Exception {
        this.lowFundingThreshold = lowFundingThreshold;
        contract = UsageContract_sol_UsageContract.load(
                contractAddress,
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(clientPrivateKey),
                new DefaultGasProvider());
    }

    /**
     * Consumes funds from the smart contract based on the provided value.
     * This method is synchronized to ensure thread safety during transactions.
     */
    public boolean consume(BigInteger valueOf) {
        synchronized (transactionLock) {
            try {
                TransactionReceipt receipt = contract.consume(valueOf).send();
                logClientFundingIfLow();
                return receipt.isStatusOK();
            } catch (Exception e) {
                LOG.error("Failed to consume from contract: {}", e.getMessage());
                throw new RuntimeException("Failed to consume from contract", e);
            }
        }
    }

    private void logClientFundingIfLow() {
        contract.getClientFunding().sendAsync().thenAccept(value -> {
            if (value.compareTo(BigInteger.ZERO) < lowFundingThreshold) {
                LOG.warn("Client funding is low. Consider refunding to avoid interruptions.");
            }
        });
    }

    public BigInteger getClientFunding() {
        try {
            return contract.getClientFunding().send();
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }

    public boolean hasValidSubscription() {
        try {
            return contract.hasValidSubscription().send();
        } catch (Exception e) {
            LOG.error("Failed to check subscription status: {}", e.getMessage());
            return false;
        }
    }
}
