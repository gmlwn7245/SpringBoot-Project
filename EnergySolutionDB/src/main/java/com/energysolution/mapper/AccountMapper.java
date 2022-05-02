package com.energysolution.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.security.Account;

@Mapper
public interface AccountMapper {
	public Account findUserById(String UserId);

	public List<String> loadUserAuthorities(String userId);
}
