package security2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//인증요청 객체
public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {
	//principal 속성에 토큰에 대한 정보 저장
	public JwtPreProcessingToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public JwtPreProcessingToken(String token) {
		this(token, token.length());
	}
}
