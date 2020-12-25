package cash.muro.services;

import java.math.BigDecimal;

import cash.muro.bch.model.BchAddress;
import cash.muro.bch.model.BchException;
import cash.muro.bch.model.BchValidatedAddress;

public interface BchService {

	public static final int minConfirmations = 0;

	boolean verifyMessage(String pubKey, String signature, String message) throws BchException;

	BchValidatedAddress validateAddress(String address) throws BchException;

	BchAddress newAddress() throws BchException;

	/**
	 * Check if the payment to the payment address has been done. Returns the amount
	 * pending to complete the payment
	 * 
	 * @param paymentAddress The address where they payment should have been send
	 * @return the pending amount to complete the payment
	 * @throws BchException when the payment can not be checked
	 */
	BigDecimal checkPayment(String paymentAddress) throws BchException;
	
	String sendToAddress(BchAddress address, BigDecimal amount, boolean substractFeeFromAmount) throws BchException; 
}
