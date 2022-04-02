package com.energysolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.energysolution.domain.UserEntity;
import com.energysolution.mapper.TestMapperInterface;

@Service
public class UserService implements UserServiceInterface{

	private final TestMapperInterface testMapper;
	
	@Autowired
	public UserService(TestMapperInterface testMapper) {
		this.testMapper = testMapper;
	}
	
	@Override
	public UserEntity test() throws Exception{
		return testMapper.test();
	}
	
}
