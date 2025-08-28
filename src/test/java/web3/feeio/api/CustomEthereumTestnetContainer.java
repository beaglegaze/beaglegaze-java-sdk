package web3.beaglegaze.api;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class CustomEthereumTestnetContainer extends GenericContainer<CustomEthereumTestnetContainer> {

    public CustomEthereumTestnetContainer(final String image) {
        super(image);
        this.withExposedPorts(8545)
                .waitingFor(Wait.forListeningPort())
                .withCreateContainerCmdModifier(
                        cmd -> cmd.withName("hardhat-testnet"));
    }

    public String getNetworkAddress() {
        return "http://" + getHost() + ":" + getMappedPort(8545);
    }

}