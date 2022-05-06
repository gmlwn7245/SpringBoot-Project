package security2;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

//인증된 객체
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

	public PostAuthorizationToken(Object principal, Object password,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, password, authorities);
	}
	
	
	public static PostAuthorizationToken getTokenFromAccountContext(AccountContext accountContext) {
		return new PostAuthorizationToken(accountContext, accountContext.getPassword(), accountContext.getAuthorities());
	}

	public String getPassword() {
		return (String) super.getCredentials();
	}
	
	public AccountContext getAccountContext() {
		return (AccountContext) super.getPrincipal();
	}
}
