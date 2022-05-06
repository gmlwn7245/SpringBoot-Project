package security2;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.energysolution.mapper.AccountMapper;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//PreAuthorizationToken이 들어옴
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PreAuthorizationToken token = (PreAuthorizationToken) authentication;
		String UserId = token.getUserId();
		String password = token.getPassword();
		
		Accounts accounts = accountMapper.findByUserId(UserId);
		if(checkPassword(password, accounts))
			return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(accounts));
		throw new NoSuchElementException("인증정보 정확하지 않음");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return PreAuthorizationToken.class.isAssignableFrom(authentication);
	}
	
	private boolean checkPassword(String password, Accounts accounts) {
		return passwordEncoder.matches(password, accounts.getPassword());
	}

}
