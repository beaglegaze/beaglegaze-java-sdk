package web3.beaglegaze;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.api.IntegrationTestBase;

/**
 * Integration test for client payout functionality.
 * Tests that clients can request a payout of their non-consumed funds.
 */
public class ClientPayoutIntegrationTest extends IntegrationTestBase {

    private UsageContract_sol_UsageContract contract;
    private String contractAddress;

    @BeforeEach
    void setUp() throws Exception {
        contract = deploySmartContract();
        contractAddress = contract.getContractAddress();
    }

    private UsageContract_sol_UsageContract deploySmartContract() throws Exception {
        return UsageContract_sol_UsageContract.deploy(
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(SMART_CONTRACT_OWNER),
                new DefaultGasProvider(), BigInteger.ZERO).send();
    }

    @Test
    void shouldPayoutNonConsumedFundsToClient() throws Exception {
        BigInteger fundingAmount = BigInteger.valueOf(1000);
        fundClient(fundingAmount, contractAddress);
        
        UsageContract_sol_UsageContract clientContract = loadClientContract();
        assertThat(clientContract.getClientFunding().send(), is(fundingAmount));

        clientContract.requestPayout().send();
        
        assertThat(clientContract.getClientFunding().send(), is(BigInteger.ZERO));
    }

    @Test
    void shouldFailPayoutWhenClientHasNoFunds() throws Exception {
        UsageContract_sol_UsageContract clientContract = loadClientContract();
        
        assertThrows(Exception.class, () -> {
            clientContract.requestPayout().send();
        });
    }

    @Test
    void shouldPayoutPartialFundsAfterConsumption() throws Exception {
        BigInteger fundingAmount = BigInteger.valueOf(1000);
        BigInteger consumeAmount = BigInteger.valueOf(300);
        BigInteger expectedRemaining = fundingAmount.subtract(consumeAmount);
        
        fundClient(fundingAmount, contractAddress);
        
        UsageContract_sol_UsageContract clientContract = loadClientContract();
        clientContract.consume(consumeAmount).send();
        
        assertThat(clientContract.getClientFunding().send(), is(expectedRemaining));

        clientContract.requestPayout().send();
        
        assertThat(clientContract.getClientFunding().send(), is(BigInteger.ZERO));
    }

    private UsageContract_sol_UsageContract loadClientContract() {
        return UsageContract_sol_UsageContract.load(
                contractAddress,
                Web3j.build(new HttpService(networkAddress)),
                Credentials.create(CLIENT_ACCOUNT),
                new DefaultGasProvider());
    }
}
