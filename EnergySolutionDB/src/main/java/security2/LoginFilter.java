package security2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	private AuthenticationFailureHandler authenticationFailureHandler;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	protected LoginFilter(String defaultFilterProcessesUrl,
						AuthenticationSuccessHandler successHandler,
						AuthenticationFailureHandler failureHandler) {
		super(defaultFilterProcessesUrl);
		this.authenticationSuccessHandler = successHandler;
		this.authenticationFailureHandler = failureHandler;
	}
	
	//HTTP Request가 처음 들어오는 곳
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		LoginDto loginDto = new ObjectMapper().readValue(request.getReader(), LoginDto.class);
		PreAuthorizationToken token = new PreAuthorizationToken(loginDto);
		return super.getAuthenticationManager().authenticate(token);
	}
	
	//인증 성공 -> JWT 토큰 생성
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
	}
	
	//인증 실패
	@Override
	public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
}
