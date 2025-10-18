package web3.beaglegaze.api;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import web3.beaglegaze.Beaglegaze_sol_Beaglegaze;

public class EthereumTestnet {

    private static final Logger LOG = Logger.getLogger(EthereumTestnet.class.getName());

    private String ethereumNetworkUrl = "http://localhost:8545";
    private String privateKey;
    
    public EthereumTestnet(String url, String privateKey) {
        ethereumNetworkUrl = url;
        this.privateKey = privateKey;
    }

    public void fund(BigInteger amount, String address) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(ethereumNetworkUrl));
        Credentials credentials = Credentials.create(privateKey);

        Beaglegaze_sol_Beaglegaze contract = Beaglegaze_sol_Beaglegaze.load(
                address, web3j, credentials, new DefaultGasProvider());

        contract.fund(amount).send();
        BigInteger funding = contract.getClientFunding().send();
        LOG.info(String.format("Client funding for address %s: %s", credentials.getAddress(), funding));
    }

    public void fundAsClient(String privateKey, String address, BigInteger amount){
        try {
            Web3j web3j = Web3j.build(new HttpService(ethereumNetworkUrl));
            Credentials credentials = Credentials.create(privateKey);

            Beaglegaze_sol_Beaglegaze contract = Beaglegaze_sol_Beaglegaze.load(
                    address, web3j, credentials, new DefaultGasProvider());

            contract.fund(amount).send();
            LOG.info(String.format("Client funding for address %s: %s", credentials.getAddress(), amount));
        } catch (Exception e) {
            LOG.severe("Error funding client: " + e.getMessage());
        }
    }

    public String getAddress() {
        return Credentials.create(privateKey).getAddress();
    }

    public BigInteger getFunds() {
        try {
            Web3j web3j = Web3j.build(new HttpService(ethereumNetworkUrl));
            Credentials credentials = Credentials.create(privateKey);

            return web3j.ethGetBalance(credentials.getAddress(), org.web3j.protocol.core.DefaultBlockParameterName.LATEST)
                    .send()
                    .getBalance();
        } catch (Exception e) {
            LOG.severe("Error getting funds: " + e.getMessage());
            return BigInteger.ZERO;
        }
    }
}
