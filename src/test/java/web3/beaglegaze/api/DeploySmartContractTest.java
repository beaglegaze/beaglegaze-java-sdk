package web3.beaglegaze.api;

import java.math.BigInteger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.Beaglegaze_sol_Beaglegaze;

@Disabled("This test deploys the smart contract and should not be run in a CI environment.")
public class DeploySmartContractTest {

        private String smartContractAddress = "0x289b72ceeab48832261626d62e3daa87fd90b024";

        /**
         * This test is used for setup a local test environment running on
         * http://localhost:8545. Make sure to have a local Ethereum node running before
         * executing this test.
         * It deploys the smart contract, registers a developer, and funds the smart
         * contract. You can then use the private key to test the pa4j-tracker library.
         */
        @Test
        void setupLocalEthNode() throws Exception {
                deploySmartContract();
                fundAsClient();
        }

        private void deploySmartContract() {
                Beaglegaze_sol_Beaglegaze.deploy(
                                Web3j.build(new HttpService("http://localhost:8545")),
                                Credentials.create(
                                                "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                                new DefaultGasProvider(), BigInteger.ZERO).sendAsync().thenAccept(contract -> {
                                        smartContractAddress = contract.getContractAddress();
                                }).join();
                System.out.println("Smart contract deployed successfully.");
        }

        /**
         * This method funds the smart contract with a specific amount of Ether.
         */
        @Test
        void fundAddress() throws Exception {
                Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
                Credentials credentials = Credentials
                                .create("0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                org.web3j.protocol.core.methods.response.EthSendTransaction response = web3j.ethSendTransaction(
                                org.web3j.protocol.core.methods.request.Transaction.createEtherTransaction(
                                                credentials.getAddress(),
                                                null,
                                                DefaultGasProvider.GAS_PRICE,
                                                DefaultGasProvider.GAS_LIMIT,
                                                "0x88f9B82462f6C4bf4a0Fb15e5c3971559a316e7f",
                                                BigInteger.valueOf(10000000)))
                                .send();
                System.out.println("Transaction hash: " + response.getTransactionHash());
        }

        @Test
        void fundAsClient() throws Exception {
                Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
                Credentials credentials = Credentials
                                .create("0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                Beaglegaze_sol_Beaglegaze contract = Beaglegaze_sol_Beaglegaze.load(
                                smartContractAddress,
                                web3j,
                                credentials,
                                new DefaultGasProvider());
                org.web3j.protocol.core.methods.response.TransactionReceipt response = contract
                                .fund(BigInteger.valueOf(2000)).send();
                System.out.println("Transaction hash: " + response.getTransactionHash());
        }

        @Test
        public void getClientFunds() {
                // EthereumTestnet ethereumTestnet = new EthereumTestnet("http://localhost:8545",
                //                 "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                EthereumTestnet ethereumTestnet = new EthereumTestnet("http://localhost:8545",
                                "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                BigInteger balance = ethereumTestnet.getFunds();
                System.out.println("Balance: " + balance);
        }

        @Test
        public void getSmartContractClientFunding() throws Exception {
                Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
                Credentials credentials = Credentials
                                .create("0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                Beaglegaze_sol_Beaglegaze contract = Beaglegaze_sol_Beaglegaze
                                .load(smartContractAddress,
                                                web3j,
                                                credentials,
                                                new DefaultGasProvider());
                BigInteger balance = contract.getClientFunding().send();
                System.out.println("Client balance: " + balance);
        }
}
