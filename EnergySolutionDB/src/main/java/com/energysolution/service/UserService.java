package com.energysolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.UserVO;
import com.energysolution.mapper.UserMapper;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void insertUser(UserVO user) {
		
	}

	@Override
	public void updateUser(String originPW, String newPW) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserVO selectUser(String UserId) {
		return userMapper.selectUser(UserId);
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
		
	}

}
