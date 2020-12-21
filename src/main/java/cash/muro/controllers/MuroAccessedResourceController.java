package cash.muro.controllers;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import cash.muro.MuroPaging;
import cash.muro.entities.MuroSettings;
import cash.muro.repos.AccessedResourceRepository;
import cash.muro.springsecurity.authorization.MuroRole;

@Controller
public class MuroAccessedResourceController {

	private static final String PATH = "muro-history";

	@Autowired
	private MuroSettings muroSettings;

	@Autowired
	private AccessedResourceRepository accessedResourceRepository;

	@GetMapping(PATH)
	@RolesAllowed(MuroRole.DEMO)
	public String listAccessedResources(Model model, final Principal principal, HttpServletRequest request) throws UnknownHostException, URISyntaxException {
		model.addAttribute(MuroSettings.MURO_LIST,
				accessedResourceRepository.findAllByUserIdOrderByAccessedTimeDesc(principal.getName(), muroSettings.pageRequest(0)));
		model.addAttribute(MuroSettings.MURO_PAGING, muroPaging(principal, 1));
		return PATH;
	}

	@GetMapping(PATH + "/{page}")
	@RolesAllowed(MuroRole.DEMO)
	public String listAccessedResources(Model model, final Principal principal, @PathVariable int page) {

		model.addAttribute(MuroSettings.MURO_LIST,
				accessedResourceRepository.findAllByUserIdOrderByAccessedTimeDesc(principal.getName(), muroSettings.pageRequest(page - 1)));
		model.addAttribute(MuroSettings.MURO_PAGING, muroPaging(principal, page));
		return PATH;
	}

	@GetMapping("bought-balance")
	@ResponseBody
	public int boughtBalance(Model model, final Principal principal) {
		return accessedResourceRepository.getBoughtBalance(principal.getName());
	}

	private MuroPaging muroPaging(Principal principal, int page) {
		return new MuroPaging(page, muroSettings.getRowsPerPage(), accessedResourceRepository.countByUserId(principal.getName()), "/" + PATH);
	}
}
