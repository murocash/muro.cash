package cash.muro.services;

import java.math.BigDecimal;

import cash.muro.entities.MuroCheckout;

public interface MuroCheckoutService {
	
	MuroCheckout createCheckout(String productCode);
	
	boolean hasPayment(String checkoutId);

	BigDecimal checkPending(String checkoutId);
}
