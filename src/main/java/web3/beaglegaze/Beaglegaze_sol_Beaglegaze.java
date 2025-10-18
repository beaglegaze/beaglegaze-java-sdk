package web3.beaglegaze;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class Beaglegaze_sol_Beaglegaze extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b506040516126f73803806126f78339818101604052810190610031919061016c565b60015f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550600133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff167f64432bf33d97c6e03519a3a2880f032d7d261b5a7e3119fbfeaef601431d274960405160405180910390a280600b8190555050610197565b5f5ffd5b5f819050919050565b61014b81610139565b8114610155575f5ffd5b50565b5f8151905061016681610142565b92915050565b5f6020828403121561018157610180610135565b5b5f61018e84828501610158565b91505092915050565b612553806101a45f395ff3fe608060405260043610610108575f3560e01c80636d9fb2871161009457806383c67d9c1161006357806383c67d9c1461031457806384b7964f1461033c578063b60d428814610378578063c26d613f14610382578063db1bed22146103be57610108565b80636d9fb2871461029457806371a71393146102aa57806377d626da146102d457806379a8945c146102ea57610108565b8063483f31ab116100db578063483f31ab146101d85780635b01c76c146102145780635fd8c7101461023e57806365058f4c146102545780636a5691cb1461026a57610108565b806304fc37071461010c5780631efb3ba4146101485780631f7692f514610184578063417528f2146101ae575b5f5ffd5b348015610117575f5ffd5b50610132600480360381019061012d9190611b47565b6103c8565b60405161013f9190611b8a565b60405180910390f35b348015610153575f5ffd5b5061016e60048036038101906101699190611b47565b6103dd565b60405161017b9190611b8a565b60405180910390f35b34801561018f575f5ffd5b50610198610423565b6040516101a59190611b8a565b60405180910390f35b3480156101b9575f5ffd5b506101c26104ef565b6040516101cf9190611bbd565b60405180910390f35b3480156101e3575f5ffd5b506101fe60048036038101906101f99190611c00565b61053f565b60405161020b9190611b8a565b60405180910390f35b34801561021f575f5ffd5b50610228610786565b6040516102359190611b8a565b60405180910390f35b348015610249575f5ffd5b506102526107ca565b005b34801561025f575f5ffd5b50610268610924565b005b348015610275575f5ffd5b5061027e610abc565b60405161028b9190611b8a565b60405180910390f35b34801561029f575f5ffd5b506102a8610b00565b005b3480156102b5575f5ffd5b506102be610b9a565b6040516102cb9190611b8a565b60405180910390f35b3480156102df575f5ffd5b506102e8610ba0565b005b3480156102f5575f5ffd5b506102fe610d0a565b60405161030b9190611bbd565b60405180910390f35b34801561031f575f5ffd5b5061033a60048036038101906103359190611c55565b610d59565b005b348015610347575f5ffd5b50610362600480360381019061035d9190611b47565b6110f7565b60405161036f9190611bbd565b60405180910390f35b610380611149565b005b34801561038d575f5ffd5b506103a860048036038101906103a39190611b47565b611237565b6040516103b59190611bbd565b60405180910390f35b6103c6611289565b005b6003602052805f5260405f205f915090505481565b5f60055f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20549050919050565b5f5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166104ac576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104a390611d13565b60405180910390fd5b60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054905090565b5f600a5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16905090565b5f3373ffffffffffffffffffffffffffffffffffffffff167f82f83cec6dcf1c03c768bb7cbf83b5be007525fd3b9b7471d309e2017ac040f460035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20546040516105c39190611b8a565b60405180910390a28160035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054101561064b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161064290611d7b565b60405180910390fd5b5f60018054905011610692576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161068990611de3565b60405180910390fd5b8160035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546106de9190611e2e565b925050819055508160055f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546107319190611e61565b925050819055506107418261132e565b60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20549050919050565b5f60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054905090565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610852576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161084990611ede565b60405180910390fd5b5f60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205490505f81116108d5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108cc90611f46565b60405180910390fd5b5f60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f208190555061092133826114ae565b50565b60025f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff166109ad576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109a490611fae565b60405180910390fd5b5f60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205411610a2c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a2390612016565b60405180910390fd5b5f60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205490505f60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2081905550610ab933826114ae565b50565b5f60055f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054905090565b600160025f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055503373ffffffffffffffffffffffffffffffffffffffff167f2ce5ee634dc8e65bbbad43d4ca6b4413f261b6a5dd89d79f5db67892e4512d1a60405160405180910390a2565b600b5481565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff1615610c29576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c209061207e565b60405180910390fd5b60065f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff1615610cb3576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610caa906120e6565b60405180910390fd5b600160065f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550565b5f5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16905090565b5f5f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610de1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610dd89061214e565b60405180910390fd5b60065f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16610e6a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e61906121dc565b60405180910390fd5b60085f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff1615610f2f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f2690612244565b60405180910390fd5b8060075f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550600160085f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555060095f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f81548092919061109b90612262565b919050555060018054905060095f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054036110f3576110f28261155b565b5b5050565b5f60025f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff169050919050565b5f341161118b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611182906122f3565b60405180910390fd5b611194336119a7565b3460035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546111e09190611e61565b925050819055503373ffffffffffffffffffffffffffffffffffffffff167f5af8184bef8e4b45eb9f6ed7734d04da38ced226495548f46e0c8ff8d7d9a5243460405161122d9190611b8a565b60405180910390a2565b5f60065f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff169050919050565b600b543410156112ce576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016112c59061235b565b60405180910390fd5b6112d7336119a7565b6112e033611a91565b6112e93461132e565b3373ffffffffffffffffffffffffffffffffffffffff167f5671526f14382102dd869527930595699f52feafde5a004f82d18b4c5cdc3ab960405160405180910390a2565b5f60018054905011156114ab575f6001805490508261134d91906123a6565b90505f6001805490508361136191906123d6565b90505f5f90505b600180549050811015611410578260045f6001848154811061138d5761138c612406565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546113fc9190611e61565b925050819055508080600101915050611368565b505f8111156114a8578060045f60015f8154811061143157611430612406565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546114a09190611e61565b925050819055505b50505b50565b5f8273ffffffffffffffffffffffffffffffffffffffff16826040516114d390612460565b5f6040518083038185875af1925050503d805f811461150d576040519150601f19603f3d011682016040523d82523d5f602084013e611512565b606091505b5050905080611556576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161154d906124be565b60405180910390fd5b505050565b5f5f90505f5f90505b6001805490508110156116525760075f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f600183815481106115c3576115c2612406565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff161561164557818061164190612262565b9250505b8080600101915050611564565b505f60018054905060028361166791906124dc565b11905080156117685760015f5f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff021916908315150217905550600183908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508273ffffffffffffffffffffffffffffffffffffffff167f64432bf33d97c6e03519a3a2880f032d7d261b5a7e3119fbfeaef601431d274960405160405180910390a25b5f60065f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055505f60095f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20819055505f5f90505b6001805490508110156119a15760075f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6001838154811061186257611861612406565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81549060ff021916905560085f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6001838154811061192457611923612406565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81549060ff02191690558080600101915050611803565b50505050565b60025f8273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16611a8e57600160025f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055508073ffffffffffffffffffffffffffffffffffffffff167f2ce5ee634dc8e65bbbad43d4ca6b4413f261b6a5dd89d79f5db67892e4512d1a60405160405180910390a25b50565b6001600a5f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f611b1682611aed565b9050919050565b611b2681611b0c565b8114611b30575f5ffd5b50565b5f81359050611b4181611b1d565b92915050565b5f60208284031215611b5c57611b5b611ae9565b5b5f611b6984828501611b33565b91505092915050565b5f819050919050565b611b8481611b72565b82525050565b5f602082019050611b9d5f830184611b7b565b92915050565b5f8115159050919050565b611bb781611ba3565b82525050565b5f602082019050611bd05f830184611bae565b92915050565b611bdf81611b72565b8114611be9575f5ffd5b50565b5f81359050611bfa81611bd6565b92915050565b5f60208284031215611c1557611c14611ae9565b5b5f611c2284828501611bec565b91505092915050565b611c3481611ba3565b8114611c3e575f5ffd5b50565b5f81359050611c4f81611c2b565b92915050565b5f5f60408385031215611c6b57611c6a611ae9565b5b5f611c7885828601611b33565b9250506020611c8985828601611c41565b9150509250929050565b5f82825260208201905092915050565b7f4f6e6c7920646576656c6f706572732063616e20636865636b207468656972205f8201527f62616c616e636500000000000000000000000000000000000000000000000000602082015250565b5f611cfd602783611c93565b9150611d0882611ca3565b604082019050919050565b5f6020820190508181035f830152611d2a81611cf1565b9050919050565b7f496e73756666696369656e7420636c69656e742066756e64696e6700000000005f82015250565b5f611d65601b83611c93565b9150611d7082611d31565b602082019050919050565b5f6020820190508181035f830152611d9281611d59565b9050919050565b7f4e6f20646576656c6f70657273207265676973746572656400000000000000005f82015250565b5f611dcd601883611c93565b9150611dd882611d99565b602082019050919050565b5f6020820190508181035f830152611dfa81611dc1565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611e3882611b72565b9150611e4383611b72565b9250828203905081811115611e5b57611e5a611e01565b5b92915050565b5f611e6b82611b72565b9150611e7683611b72565b9250828201905080821115611e8e57611e8d611e01565b5b92915050565b7f4f6e6c7920646576656c6f706572732063616e207769746864726177000000005f82015250565b5f611ec8601c83611c93565b9150611ed382611e94565b602082019050919050565b5f6020820190508181035f830152611ef581611ebc565b9050919050565b7f4e6f2062616c616e636520746f207769746864726177000000000000000000005f82015250565b5f611f30601683611c93565b9150611f3b82611efc565b602082019050919050565b5f6020820190508181035f830152611f5d81611f24565b9050919050565b7f4f6e6c7920636c69656e74732063616e2072657175657374207061796f7574005f82015250565b5f611f98601f83611c93565b9150611fa382611f64565b602082019050919050565b5f6020820190508181035f830152611fc581611f8c565b9050919050565b7f4e6f2066756e647320617661696c61626c6520666f72207061796f75740000005f82015250565b5f612000601d83611c93565b915061200b82611fcc565b602082019050919050565b5f6020820190508181035f83015261202d81611ff4565b9050919050565b7f416c7265616479207265676973746572656420617320646576656c6f706572005f82015250565b5f612068601f83611c93565b915061207382612034565b602082019050919050565b5f6020820190508181035f8301526120958161205c565b9050919050565b7f526567697374726174696f6e20616c72656164792070656e64696e67000000005f82015250565b5f6120d0601c83611c93565b91506120db8261209c565b602082019050919050565b5f6020820190508181035f8301526120fd816120c4565b9050919050565b7f4f6e6c7920646576656c6f706572732063616e20766f746500000000000000005f82015250565b5f612138601883611c93565b915061214382612104565b602082019050919050565b5f6020820190508181035f8301526121658161212c565b9050919050565b7f4e6f2070656e64696e6720726567697374726174696f6e20666f7220746869735f8201527f20646576656c6f70657200000000000000000000000000000000000000000000602082015250565b5f6121c6602a83611c93565b91506121d18261216c565b604082019050919050565b5f6020820190508181035f8301526121f3816121ba565b9050919050565b7f416c726561647920766f74656420666f72207468697320646576656c6f7065725f82015250565b5f61222e602083611c93565b9150612239826121fa565b602082019050919050565b5f6020820190508181035f83015261225b81612222565b9050919050565b5f61226c82611b72565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361229e5761229d611e01565b5b600182019050919050565b7f4e6f2066756e64732073656e74000000000000000000000000000000000000005f82015250565b5f6122dd600d83611c93565b91506122e8826122a9565b602082019050919050565b5f6020820190508181035f83015261230a816122d1565b9050919050565b7f496e73756666696369656e74207061796d656e740000000000000000000000005f82015250565b5f612345601483611c93565b915061235082612311565b602082019050919050565b5f6020820190508181035f83015261237281612339565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffd5b5f6123b082611b72565b91506123bb83611b72565b9250826123cb576123ca612379565b5b828204905092915050565b5f6123e082611b72565b91506123eb83611b72565b9250826123fb576123fa612379565b5b828206905092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f81905092915050565b50565b5f61244b5f83612433565b91506124568261243d565b5f82019050919050565b5f61246a82612440565b9150819050919050565b7f4661696c656420746f2073656e642045746865720000000000000000000000005f82015250565b5f6124a8601483611c93565b91506124b382612474565b602082019050919050565b5f6020820190508181035f8301526124d58161249c565b9050919050565b5f6124e682611b72565b91506124f183611b72565b92508282026124ff81611b72565b9150828204841483151761251657612515611e01565b5b509291505056fea2646970667358221220a577489bd5150693abbd50ea947499866e4d2bb60f21230424fb3d86d01b705f64736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CLIENTFUNDING = "clientFunding";

    public static final String FUNC_CONSUME = "consume";

    public static final String FUNC_FUND = "fund";

    public static final String FUNC_GETCLIENTFUNDING = "getClientFunding";

    public static final String FUNC_getClientReputation = "getClientReputation";

    public static final String FUNC_GETDEVELOPERBALANCE = "getDeveloperBalance";

    public static final String FUNC_HASPENDINGREGISTRATIONREQUEST = "hasPendingRegistrationRequest";

    public static final String FUNC_HASVALIDSUBSCRIPTION = "hasValidSubscription";

    public static final String FUNC_ISCLIENT = "isClient";

    public static final String FUNC_ISDEVELOPER = "isDeveloper";

    public static final String FUNC_PURCHASESUBSCRIPTION = "purchaseSubscription";

    public static final String FUNC_REGISTERCLIENT = "registerClient";

    public static final String FUNC_REQUESTDEVELOPERREGISTRATION = "requestDeveloperRegistration";

    public static final String FUNC_REQUESTPAYOUT = "requestPayout";

    public static final String FUNC_SUBSCRIPTIONPRICEINWEI = "subscriptionPriceInWei";

    public static final String FUNC_VOTEFORDEVELOPER = "voteForDeveloper";

    public static final String FUNC_WITHDRAWBALANCE = "withdrawBalance";

    public static final Event CLIENTREGISTERED_EVENT = new Event("ClientRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event CONSUMED_EVENT = new Event("Consumed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEVELOPERREGISTERED_EVENT = new Event("DeveloperRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event FUNDED_EVENT = new Event("Funded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SUBSCRIPTIONPURCHASED_EVENT = new Event("SubscriptionPurchased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Beaglegaze_sol_Beaglegaze(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Beaglegaze_sol_Beaglegaze(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Beaglegaze_sol_Beaglegaze(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Beaglegaze_sol_Beaglegaze(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ClientRegisteredEventResponse> getClientRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLIENTREGISTERED_EVENT, transactionReceipt);
        ArrayList<ClientRegisteredEventResponse> responses = new ArrayList<ClientRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClientRegisteredEventResponse typedResponse = new ClientRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClientRegisteredEventResponse getClientRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLIENTREGISTERED_EVENT, log);
        ClientRegisteredEventResponse typedResponse = new ClientRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClientRegisteredEventResponse> clientRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClientRegisteredEventFromLog(log));
    }

    public Flowable<ClientRegisteredEventResponse> clientRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLIENTREGISTERED_EVENT));
        return clientRegisteredEventFlowable(filter);
    }

    public static List<ConsumedEventResponse> getConsumedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONSUMED_EVENT, transactionReceipt);
        ArrayList<ConsumedEventResponse> responses = new ArrayList<ConsumedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ConsumedEventResponse typedResponse = new ConsumedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ConsumedEventResponse getConsumedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONSUMED_EVENT, log);
        ConsumedEventResponse typedResponse = new ConsumedEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ConsumedEventResponse> consumedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getConsumedEventFromLog(log));
    }

    public Flowable<ConsumedEventResponse> consumedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONSUMED_EVENT));
        return consumedEventFlowable(filter);
    }

    public static List<DeveloperRegisteredEventResponse> getDeveloperRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEVELOPERREGISTERED_EVENT, transactionReceipt);
        ArrayList<DeveloperRegisteredEventResponse> responses = new ArrayList<DeveloperRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DeveloperRegisteredEventResponse typedResponse = new DeveloperRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.developer = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DeveloperRegisteredEventResponse getDeveloperRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEVELOPERREGISTERED_EVENT, log);
        DeveloperRegisteredEventResponse typedResponse = new DeveloperRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.developer = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DeveloperRegisteredEventResponse> developerRegisteredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDeveloperRegisteredEventFromLog(log));
    }

    public Flowable<DeveloperRegisteredEventResponse> developerRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEVELOPERREGISTERED_EVENT));
        return developerRegisteredEventFlowable(filter);
    }

    public static List<FundedEventResponse> getFundedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNDED_EVENT, transactionReceipt);
        ArrayList<FundedEventResponse> responses = new ArrayList<FundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundedEventResponse typedResponse = new FundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FundedEventResponse getFundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNDED_EVENT, log);
        FundedEventResponse typedResponse = new FundedEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FundedEventResponse> fundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFundedEventFromLog(log));
    }

    public Flowable<FundedEventResponse> fundedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDED_EVENT));
        return fundedEventFlowable(filter);
    }

    public static List<SubscriptionPurchasedEventResponse> getSubscriptionPurchasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SUBSCRIPTIONPURCHASED_EVENT, transactionReceipt);
        ArrayList<SubscriptionPurchasedEventResponse> responses = new ArrayList<SubscriptionPurchasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SubscriptionPurchasedEventResponse typedResponse = new SubscriptionPurchasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SubscriptionPurchasedEventResponse getSubscriptionPurchasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SUBSCRIPTIONPURCHASED_EVENT, log);
        SubscriptionPurchasedEventResponse typedResponse = new SubscriptionPurchasedEventResponse();
        typedResponse.log = log;
        typedResponse.client = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<SubscriptionPurchasedEventResponse> subscriptionPurchasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSubscriptionPurchasedEventFromLog(log));
    }

    public Flowable<SubscriptionPurchasedEventResponse> subscriptionPurchasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBSCRIPTIONPURCHASED_EVENT));
        return subscriptionPurchasedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> clientFunding(String param0) {
        final Function function = new Function(FUNC_CLIENTFUNDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> consume(BigInteger amount) {
        final Function function = new Function(
                FUNC_CONSUME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> fund(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_FUND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> getClientFunding() {
        final Function function = new Function(FUNC_GETCLIENTFUNDING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getClientReputation(String client) {
        final Function function = new Function(FUNC_getClientReputation, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, client)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getClientReputation() {
        final Function function = new Function(FUNC_getClientReputation, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getDeveloperBalance() {
        final Function function = new Function(FUNC_GETDEVELOPERBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> hasPendingRegistrationRequest(String developer) {
        final Function function = new Function(FUNC_HASPENDINGREGISTRATIONREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, developer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> hasValidSubscription() {
        final Function function = new Function(FUNC_HASVALIDSUBSCRIPTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isClient(String user) {
        final Function function = new Function(FUNC_ISCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isDeveloper() {
        final Function function = new Function(FUNC_ISDEVELOPER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> purchaseSubscription(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PURCHASESUBSCRIPTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> registerClient() {
        final Function function = new Function(
                FUNC_REGISTERCLIENT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestDeveloperRegistration() {
        final Function function = new Function(
                FUNC_REQUESTDEVELOPERREGISTRATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestPayout() {
        final Function function = new Function(
                FUNC_REQUESTPAYOUT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> subscriptionPriceInWei() {
        final Function function = new Function(FUNC_SUBSCRIPTIONPRICEINWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> voteForDeveloper(String developer,
            Boolean approve) {
        final Function function = new Function(
                FUNC_VOTEFORDEVELOPER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, developer), 
                new org.web3j.abi.datatypes.Bool(approve)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawBalance() {
        final Function function = new Function(
                FUNC_WITHDRAWBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Beaglegaze_sol_Beaglegaze load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Beaglegaze_sol_Beaglegaze(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Beaglegaze_sol_Beaglegaze load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Beaglegaze_sol_Beaglegaze(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Beaglegaze_sol_Beaglegaze load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Beaglegaze_sol_Beaglegaze(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Beaglegaze_sol_Beaglegaze load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Beaglegaze_sol_Beaglegaze(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Beaglegaze_sol_Beaglegaze> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger _subscriptionPriceInWei) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_subscriptionPriceInWei)));
        return deployRemoteCall(Beaglegaze_sol_Beaglegaze.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Beaglegaze_sol_Beaglegaze> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            BigInteger _subscriptionPriceInWei) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_subscriptionPriceInWei)));
        return deployRemoteCall(Beaglegaze_sol_Beaglegaze.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Beaglegaze_sol_Beaglegaze> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger _subscriptionPriceInWei) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_subscriptionPriceInWei)));
        return deployRemoteCall(Beaglegaze_sol_Beaglegaze.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Beaglegaze_sol_Beaglegaze> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger _subscriptionPriceInWei) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_subscriptionPriceInWei)));
        return deployRemoteCall(Beaglegaze_sol_Beaglegaze.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ClientRegisteredEventResponse extends BaseEventResponse {
        public String client;
    }

    public static class ConsumedEventResponse extends BaseEventResponse {
        public String client;

        public BigInteger amount;
    }

    public static class DeveloperRegisteredEventResponse extends BaseEventResponse {
        public String developer;
    }

    public static class FundedEventResponse extends BaseEventResponse {
        public String client;

        public BigInteger amount;
    }

    public static class SubscriptionPurchasedEventResponse extends BaseEventResponse {
        public String client;
    }
}
