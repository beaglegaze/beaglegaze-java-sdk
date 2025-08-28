package web3.beaglegaze.api;

import java.math.BigInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for integration tests that require a running Ethereum testnet.
 * Provides setup and teardown methods for the test environment.
 * 
 * @author steffenboe
 */
public abstract class IntegrationTestBase {

    protected static final String CLIENT_ACCOUNT = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    protected static final String SMART_CONTRACT_OWNER = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    protected static final String DEVELOPER_ACCOUNT = "0xcccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc";

    protected CustomEthereumTestnetContainer hardhatContainer;
    protected String networkAddress;
    protected EthereumTestnet ethereumTestnet;

    @BeforeEach
    void setUpBase() throws Exception {
        hardhatContainer = new web3.beaglegaze.api.CustomEthereumTestnetContainer("hardhat-testnet");
        hardhatContainer.start();
        networkAddress = hardhatContainer.getNetworkAddress();
        ethereumTestnet = new EthereumTestnet(networkAddress, "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }

    @AfterEach
    void tearDownBase() throws InterruptedException {
        if (hardhatContainer != null) {
            hardhatContainer.stop();
        }
    }

    protected void fundClient(BigInteger amount, String contractAddress) throws Exception {
        ethereumTestnet.fund(amount, contractAddress);
    }

    protected void fundAsClient(String privateKey, String contractAddress, BigInteger amount) {
        ethereumTestnet.fundAsClient(privateKey, contractAddress, amount);
    }
}
