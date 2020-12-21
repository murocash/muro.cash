package cash.muro.controllers;

import java.math.BigDecimal;
import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cash.muro.bch.model.BchException;
import cash.muro.services.AccessedResourceService;
import cash.muro.services.MuroCheckoutService;
import cash.muro.springsecurity.authentication.cashid.MuroAuthenticationToken;
import cash.muro.springsecurity.authorization.MuroRole;

@Controller
public class BchMuroCheckoutController {

	private final static BigDecimal bdZero = new BigDecimal(0);
	
	@Autowired
	private MuroCheckoutService checkoutService;
	
	@Autowired
	private AccessedResourceService resourceService;

	@GetMapping("/muro-checkout/{productCode}/")
	@RolesAllowed({MuroRole.DEMO})
	public String checkout(@PathVariable String productCode, Model model) throws BchException {
		model.addAttribute("checkout", checkoutService.createCheckout(productCode));
		return "muro-checkout";
	}
	
	@PostMapping("/muro-checkout/checkpayment/")
	@RolesAllowed({MuroRole.DEMO})
	@ResponseBody
	public String checkPending(HttpServletRequest request, Principal principal) {
		BigDecimal pending = checkoutService.checkPending(request.getParameter("receiver"));
		if (pending.compareTo(bdZero) < 1) {
			((MuroAuthenticationToken)principal).setBoughtBalance(resourceService.boughtBalance(principal.getName()));
		}
		return pending.toPlainString();
	}

}
