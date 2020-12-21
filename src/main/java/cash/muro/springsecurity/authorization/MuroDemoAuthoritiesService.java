package cash.muro.springsecurity.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MuroDemoAuthoritiesService implements AuthoritiesService {
	
	private final Set<String> admins;
	
	public MuroDemoAuthoritiesService(Set<String> admins) {
		this.admins = admins;
	}
	
	@Override
	public Collection<GrantedAuthority> authorities(String userId) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (admins.contains(userId)) {
			authorities.add(new SimpleGrantedAuthority(MuroRole.ADMIN));
		}
		authorities.add(new SimpleGrantedAuthority(MuroRole.DEMO));
		return authorities;
	}

}
