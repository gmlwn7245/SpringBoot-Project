package com.energysolution.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.UserDTO;
import com.energysolution.mapper.UserMapper;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void insertUser(UserDTO uservo) {
		userMapper.insertUser(uservo);
	}

	@Override
	public void updateUser(HashMap<String, String> updateMap) {
		userMapper.updateUser(updateMap);	
	}

	@Override
	public UserDTO selectUser(String UserId) {
		return userMapper.selectUser(UserId);
	}

	@Override
	public void deleteUser(String UserId) {
		userMapper.deleteUser(UserId);
	}

}
