package com.energysolution.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.security.Account;

import security2.Accounts;

@Mapper
public interface AccountMapper {
	public Account findUser(HashMap<String, String> Usermap);
	public Account findUserById(String UserId);
	public Accounts findByUserId(String UserId);
	public String findRole(String UserId);
	public List<String> loadUserAuthorities(String userId);
}
