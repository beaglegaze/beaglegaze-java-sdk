package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

public class NFTSubscriptionIntegrationTest extends IntegrationTestBase {

    private static final String CLIENT_ACCOUNT_PRIV_KEY = "0xbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    private static final String SMART_CONTRACT_OWNER_PRIV_KEY = "0xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    void shouldPurchaseNFTSubscription() throws Exception {
        UsageContract_sol_UsageContract contract = deployContract(networkAddress);
        UsageContract_sol_UsageContract clientContract = loadContractAsClient(contract.getContractAddress());

        BigInteger tierIndex = BigInteger.ZERO;
        BigInteger paymentAmount = BigInteger.valueOf(1000000000000000000L); // 1 ETH in wei

        TransactionReceipt receipt = clientContract.purchaseSubscription(tierIndex, paymentAmount).send();
        
        assertThat(receipt.isStatusOK(), is(true));
        
        boolean hasValidSubscription = clientContract.hasValidSubscription().send();
        assertThat(hasValidSubscription, is(true));
    }

    private UsageContract_sol_UsageContract loadContractAsClient(String contractAddress) {
        return loadContract(networkAddress, contractAddress, CLIENT_ACCOUNT_PRIV_KEY);
    }

    private UsageContract_sol_UsageContract deployContract(String networkAddress) throws Exception {
        return UsageContract_sol_UsageContract.deploy(
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(SMART_CONTRACT_OWNER_PRIV_KEY),
                new DefaultGasProvider()).send();
    }

    private UsageContract_sol_UsageContract loadContract(String networkAddress, String contractAddress,
            String account) {
        return UsageContract_sol_UsageContract.load(
                contractAddress,
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(account),
                new DefaultGasProvider());
    }
}
