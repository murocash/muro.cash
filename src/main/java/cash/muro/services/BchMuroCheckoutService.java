package cash.muro.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cash.muro.bch.model.BchAddress;
import cash.muro.bch.model.BchException;
import cash.muro.entities.MuroCheckout;
import cash.muro.entities.MuroPayment;
import cash.muro.repos.MuroCheckoutRepository;
import cash.muro.repos.MuroPaymentRepository;
import cash.muro.repos.MuroProductRepository;
import cash.muro.springsecurity.authentication.cashid.MuroAuthenticationToken;

@Service
public class BchMuroCheckoutService implements MuroCheckoutService {
	
	@Autowired
	private BchService bchService;
	
	@Autowired
	private MuroProductRepository productRepository;
	
	@Autowired
	private MuroCheckoutRepository checkoutRepository;
	
	@Autowired
	private MuroPaymentRepository paymentRepository;

	@Override
	public MuroCheckout createCheckout(String productCode) {
		MuroAuthenticationToken auth = (MuroAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		try {
			return checkoutRepository.save(new MuroCheckout(bchService.newAddress().toString(),
					auth.getName(),
					productRepository.findById(productCode).get()));
		}
		catch (BchException e) {
			throw new InternalError("Could not create Checkout", e);
		}
	}
	
	@Override
	public boolean hasPayment(String checkoutId) {
		return paymentRepository.existsById(checkoutId);
	}
	
	@Override
	public BigDecimal checkPending(String checkoutId) {
		if (hasPayment(checkoutId)) {
			return new BigDecimal("0");
		}
		MuroCheckout checkout = checkoutRepository.findById(checkoutId).get();
		try {
			BigDecimal bd = bchService.checkPayment(checkout.getReceiver());
			if (bd == null) {
				return checkout.getProduct().getPrice();
			} else {
				int comparation = bd.compareTo(checkout.getProduct().getPrice());
				if (comparation > -1) {
					paymentRepository.save(new MuroPayment(checkout, ""));
					if (comparation > 0)  { //Payback
						bchService.sendToAddress(new BchAddress(checkout.getSender()), 
												bd.subtract(checkout.getProduct().getPrice()), 
												true);
					}
				}
				return checkout.getProduct().getPrice().subtract(bd);				
			}
		} catch (BchException e) {
			return checkout.getProduct().getPrice();
		}
	}

}
