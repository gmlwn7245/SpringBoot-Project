package com.energysolution.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountProvider implements AuthenticationProvider{
	@Autowired
	private AccountService accountService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String UserId = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		Account account = (Account) accountService.loadUserByUsername(UserId);
		
		if(!passwordEncoder.matches(password, account.getPassword())) {
			throw new BadCredentialsException("BadCredentialsException");
		}
		
		return new UsernamePasswordAuthenticationToken(account,account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	


}
