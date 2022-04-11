package com.energysolution.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.UserVO;
import com.energysolution.mapper.UserMapper;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void insertUser(UserVO uservo) {
		userMapper.insertUser(uservo);
	}

	@Override
	public void updateUser(HashMap<String, String> updateMap) {
		userMapper.updateUser(updateMap);	
	}

	@Override
	public UserVO selectUser(String UserId) {
		return userMapper.selectUser(UserId);
	}

	@Override
	public void deleteUser(String UserId) {
		userMapper.deleteUser(UserId);
	}

}
