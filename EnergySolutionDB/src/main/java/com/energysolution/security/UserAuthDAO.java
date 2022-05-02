package com.energysolution.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.energysolution.mapper.AccountMapper;

@Repository
public class UserAuthDAO {
	@Autowired
	AccountMapper accountMapper;
	
	public Account findById(String UserId) {
		return accountMapper.findUserById(UserId);
	}
}
