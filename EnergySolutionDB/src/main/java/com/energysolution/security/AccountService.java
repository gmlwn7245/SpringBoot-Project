package com.energysolution.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.energysolution.mapper.AccountMapper;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	AccountMapper accountMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Account account = accountMapper.findUserById(userId);
        if(account == null) {
            throw new UsernameNotFoundException(userId);
        }
		account.setAuthorities(loadUserAuthorities(userId));
		
		return account;
	}

    public ArrayList<GrantedAuthority> loadUserAuthorities(String UserId) {
        List<String> authorities = accountMapper.loadUserAuthorities(UserId);
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        for (String auth: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(auth));
        }

        return grantedAuthorities;
    }
}
