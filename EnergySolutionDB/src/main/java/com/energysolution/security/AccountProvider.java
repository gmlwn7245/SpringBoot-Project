package com.energysolution.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import security2.PreAuthorizationToken;

@RequiredArgsConstructor
@Component
public class AccountProvider implements AuthenticationProvider{
	@Autowired
	private AccountService accountService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
    //인증 구현
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PreAuthorizationToken token = (PreAuthorizationToken)authentication;
		String UserId = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		Account account = (Account) accountService.loadUserByUsername(UserId);
		
		if(!passwordEncoder.matches(password, account.getPassword())) {
			throw new BadCredentialsException("BadCredentialsException");
		}
		//PostAuthorizationToken.getTokenFromAccountContext(AccountContext);
		
		return new UsernamePasswordAuthenticationToken(account,account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	


}
