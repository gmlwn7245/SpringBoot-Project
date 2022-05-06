package security2;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

//인증 요청 객체
public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

	public PreAuthorizationToken(LoginDto loginDto) {
		super(loginDto.getUserId(), loginDto.getPassword());
	}
	
	public PreAuthorizationToken(String UserId, String Password) {
		super(UserId,Password);
	}
	
	public String getUserId() {
		return (String)super.getPrincipal();
	}
	
	public String getPassword() {
		return (String)super.getCredentials();
	}


}
