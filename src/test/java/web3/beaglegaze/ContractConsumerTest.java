package web3.beaglegaze;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.web3j.crypto.Credentials;

import web3.beaglegaze.BatchReadyEvent;
import web3.beaglegaze.ContractConsumer;
import web3.beaglegaze.SmartContract;

public class ContractConsumerTest {
	
	private static final long BATCH_AMOUNT = 100L;
	private static final BigInteger SUFFICIENT_FUNDS = BigInteger.valueOf(100);
	private static final BigInteger INSUFFICIENT_FUNDS = BigInteger.valueOf(50);
	
	@Mock
	private Credentials credentials;
	@Mock
	private SmartContract mockContract;

	private ContractConsumer contractConsumer;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.contractConsumer = new ContractConsumer(mockContract);
	}

	/**
	 * Tests that the contract consumer goes (and stays in a blocked state after a
	 * consumption failure from the contract.
	 */
	@Test
	void shouldKeepThrowingExceptionsWhileBlocked() throws Exception {
		setupContractToThrowInsufficientFundsError();
		blockConsumerWithInitialFailedBatch();
		verifySubsequentBatchEventsThrowIllegalStateException();
	}

	/**
	 * Tests that the contract consumer can be unblocked when it receives an
	 * unblocking attempt event with sufficient funds.
	 */
	@Test
	void shouldUnblockWhenReceivingUnblockingAttemptEventWithSufficientFunds() throws Exception {

		blockConsumerDueToInsufficientFunds();
		setupContractWithSufficientFunding();
		when(mockContract.consume(any(BigInteger.class))).thenReturn(true);
		attemptToHandleBatchEvent();

		assertFalse(contractConsumer.isInErrorState(),
				"ContractConsumer should be unblocked when sufficient funds are available");
		verify(mockContract, firstAndSecondInvocation()).consume(any());
	}

	private VerificationMode firstAndSecondInvocation() {
		return times(2);
	}

	/**
	 * Tests that the contract consumer remains blocked when funding is insufficient
	 * for the pending batch amount.
	 */
	@Test
	void shouldRemainBlockedWhenFundingIsInsufficientForPendingBatch() throws Exception {
		blockConsumerDueToInsufficientFunds();
		setupContractWithInsufficientFunding();

		BatchReadyEvent batchEvent = new BatchReadyEvent(BATCH_AMOUNT);
		assertThrows(IllegalStateException.class, () -> contractConsumer.handle(batchEvent));

		assertTrue(contractConsumer.isInErrorState(),
				"ContractConsumer should remain blocked when funding is insufficient for pending batch");
		verify(mockContract, onlyFirstInvocationWhenBlocked()).consume(any());
	}

	private VerificationMode onlyFirstInvocationWhenBlocked() {
		return times(1);
	}

	private void setupContractToThrowInsufficientFundsError() throws Exception {
		when(mockContract.consume(any(BigInteger.class)))
				.thenThrow(new RuntimeException("Insufficient funds"));
	}

	private void blockConsumerWithInitialFailedBatch() throws Exception {
		BatchReadyEvent initialBatchEvent = new BatchReadyEvent(BATCH_AMOUNT);
		assertThrows(RuntimeException.class, () -> contractConsumer.handle(initialBatchEvent));
		assertTrue(contractConsumer.isInErrorState());
	}

	private void verifySubsequentBatchEventsThrowIllegalStateException() throws Exception {
		BatchReadyEvent secondBatchEvent = new BatchReadyEvent(50);
		assertThrows(IllegalStateException.class, () -> contractConsumer.handle(secondBatchEvent));
		assertTrue(contractConsumer.isInErrorState());

		BatchReadyEvent thirdBatchEvent = new BatchReadyEvent(25);
		assertThrows(IllegalStateException.class, () -> contractConsumer.handle(thirdBatchEvent));
		assertTrue(contractConsumer.isInErrorState());
	}

	private void blockConsumerDueToInsufficientFunds() throws Exception {
		BatchReadyEvent initialBatchEvent = new BatchReadyEvent(BATCH_AMOUNT);
		when(mockContract.consume(any(BigInteger.class)))
				.thenThrow(new RuntimeException("Insufficient funds"));

		assertThrows(RuntimeException.class, () -> contractConsumer.handle(initialBatchEvent));
		assertTrue(contractConsumer.isInErrorState());
	}

	private void setupContractWithSufficientFunding() throws Exception {
		when(mockContract.getClientFunding()).thenReturn(SUFFICIENT_FUNDS);
	}

	private void setupContractWithInsufficientFunding() throws Exception {
		when(mockContract.getClientFunding()).thenReturn(INSUFFICIENT_FUNDS);
	}

	private void attemptToHandleBatchEvent() throws Exception {
		BatchReadyEvent unblockingBatchEvent = new BatchReadyEvent(BATCH_AMOUNT);
		contractConsumer.handle(unblockingBatchEvent);
	}
}
