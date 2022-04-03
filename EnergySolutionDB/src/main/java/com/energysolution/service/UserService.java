package com.energysolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.energysolution.domain.UserVO;
import com.energysolution.mapper.EnergySolutionMapperInterface;

@Service
public class UserService implements UserServiceInterface{

	private final EnergySolutionMapperInterface ESMapper;
	
	@Autowired
	public UserService(EnergySolutionMapperInterface ESMapper) {
		this.ESMapper = ESMapper;
	}
	
	@Override
	public UserVO getUser() throws Exception{
		return ESMapper.getUser();
	}
	
}
