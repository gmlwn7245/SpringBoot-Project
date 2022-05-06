package security2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountContext extends User{
	private Accounts accounts;
	
	public AccountContext(Accounts accounts, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.accounts= accounts;
	}
	
	public AccountContext(String username, String role) {
		super(username, "1234", parseAuthorities(role));
	}
	
	public static AccountContext fromAccountModel(Accounts accounts) {
		return new AccountContext(accounts, accounts.getUserId(), accounts.getPassword(), parseAuthorities(accounts.getUserRole()));
	}
	
	public static List<SimpleGrantedAuthority> parseAuthorities(UserRole role){
		return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority((r.getRoleName()))).collect(Collectors.toList());
	}
	
	
	public static List<SimpleGrantedAuthority> parseAuthorities(String role){
		return parseAuthorities(UserRole.getRoleByName(role));
	}
	
	public Accounts getAccount() {
		return this.accounts;
	}

}
