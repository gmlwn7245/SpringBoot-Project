package security2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	//@Autowired
	//private JwtFactory jwtFactory;
	
	@Autowired
	private ObjectMapper objectmapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		PostAuthorizationToken postAuthorizationToken = (PostAuthorizationToken) authentication;
		AccountContext accountContext = (AccountContext) postAuthorizationToken.getPrincipal();
		
		//String token = jwtFacotry.generateToken(accountContext);
		
		//processResponse(response, writeDto(token));
	}
	/*
	private TokenDto writeDto(String token) {
		return new TokenDto(token);
	}
	
	private void ProcessResponse(HttpServletResponse res, TokenDto dto) throws IOException {
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectmapper.writeValueAsString(dto));
	}*/

}
