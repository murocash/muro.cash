package cash.muro.springsecurity.authentication.cashid;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MuroAuthenticationToken extends CashIdAuthenticationToken {
	
	private int freeBalance = 0;
	private int boughtBalance = 0;
	private boolean hadBalance = false;

	public MuroAuthenticationToken(Object principal, CashIdCredentials credentials, CashIdAuthenticationDetails cashIdDetails) {
		super(principal, credentials, cashIdDetails);
	}

	public MuroAuthenticationToken(Object principal, Collection<GrantedAuthority> authorities) {
		super(principal, authorities);
	}
	
	public void setBalance(int freeBalance, int boughtBalance) {
		this.freeBalance = freeBalance;
		this.boughtBalance = boughtBalance;
	}
	
	public boolean hasBalance() {
		return (freeBalance + boughtBalance) > 0;
	}

}
