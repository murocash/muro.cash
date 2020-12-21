package cash.muro.springsecurity.authorization;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cash.muro.springsecurity.authentication.cashid.MuroAuthenticationToken;

public class MuroVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if (authentication.getPrincipal() instanceof String) {
			return ACCESS_ABSTAIN;
		} else {
			MuroAuthenticationToken token = (MuroAuthenticationToken) authentication;
			if (token.getAuthorities().contains(new SimpleGrantedAuthority(MuroRole.USER)) && token.hasBalance()) {
				return ACCESS_GRANTED;
			}
			return ACCESS_DENIED;
		}
	}

}
