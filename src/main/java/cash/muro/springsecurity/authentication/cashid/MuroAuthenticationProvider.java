package cash.muro.springsecurity.authentication.cashid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import cash.muro.services.AccessedResourceService;
import cash.muro.springsecurity.authorization.AuthoritiesService;

public class MuroAuthenticationProvider extends CashIdAuthenticationProvider {

	@Autowired
	private AccessedResourceService resourceService;
	
	public MuroAuthenticationProvider(AuthoritiesService authoritiesService) {
		super(authoritiesService);
	}

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CashIdAuthenticationToken cashIdAuth = (CashIdAuthenticationToken) super.authenticate(authentication);
		if (cashIdAuth == null) {
			return null;
		}
		MuroAuthenticationToken auth = new MuroAuthenticationToken(cashIdAuth.getPrincipal(), cashIdAuth.getAuthorities());
		auth.setBalance(resourceService.freeBalance(auth.getName()), resourceService.boughtBalance(auth.getName()));
		return auth;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return CashIdAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
