package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.UserDTO;
import com.energysolution.mapper.UserMapper;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void insertUser(UserDTO userDTO) {
		System.out.println(userDTO.getPassword());
		userMapper.insertUser(userDTO);
	}

	@Override
	public void updateUser(HashMap<String, String> updateMap) {
		userMapper.updateUser(updateMap);	
	}
	
	@Override
	public List<UserDTO> FindUserId(String Email) {
		return userMapper.FindUserId(Email);
	}
	
	@Override
	public UserDTO FindUserPW(HashMap<String, String> findUserPWMap) {
		return userMapper.FindUserPW(findUserPWMap);
	}

	@Override
	public UserDTO LoginUser(String UserId) {
		return userMapper.LoginUser(UserId);
	}

	@Override
	public void deleteUser(String UserId) {
		userMapper.deleteUser(UserId);
	}

}
