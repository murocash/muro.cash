package cash.muro.controllers;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cash.muro.MuroPaging;
import cash.muro.entities.MuroSettings;
import cash.muro.repos.MuroPaymentRepository;
import cash.muro.springsecurity.authorization.MuroRole;

@Controller
public class MuroPaymentController {
	
	private static final String PATH = "muro-payments";
	
	@Autowired
	private MuroSettings muroSettings;
	
	@Autowired
	private MuroPaymentRepository paymentRepository;
	
	@GetMapping(PATH)
	@RolesAllowed(MuroRole.DEMO)
	public String listPayments(Model model, final Principal principal) throws UnknownHostException, URISyntaxException {
		model.addAttribute(MuroSettings.MURO_LIST, paymentRepository.findAllByCheckout_senderOrderByDateTimeDesc(principal.getName(), muroSettings.pageRequest(0)));
		model.addAttribute(MuroSettings.MURO_PAGING, muroPaging(principal, 1));
		return PATH;
	}

	@GetMapping(PATH + "/{page}")
	@RolesAllowed(MuroRole.DEMO)
	public String listPayments(Model model, final Principal principal, @PathVariable int page) {

		model.addAttribute(MuroSettings.MURO_LIST, paymentRepository.findAllByCheckout_senderOrderByDateTimeDesc(principal.getName(), muroSettings.pageRequest(page - 1)));
		model.addAttribute(MuroSettings.MURO_PAGING, muroPaging(principal, page));
		return PATH;
	}
	
	private MuroPaging muroPaging(Principal principal, int page) {
		return new MuroPaging(page, muroSettings.getRowsPerPage(), paymentRepository.countByCheckout_sender(principal.getName()), "/" + PATH);
	}
}
