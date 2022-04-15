package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.dto.UserDTO;
import com.energysolution.mapper.UserMapper;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public String insertUser(UserDTO userDTO) {
		
		//이미 있는 사용자인 경우
		if(checkUser(userDTO.getUserId()).equals("success"))
			return "false";
		
		//insert 작업
		userMapper.insertUser(userDTO);
		
		return checkUser(userDTO.getUserId());
	}

	@Override
	public String updateUser(HashMap<String, String> updateMap) {
		userMapper.updateUser(updateMap);
		String getPassword = userMapper.getUserPassword(updateMap.get("UserId"));
		
		if(updateMap.get("newPW").equals(getPassword))
			return "success";
		return "fail";
	}
	
	@Override
	public List<String> FindUserId(String Email) {
		return userMapper.FindUserId(Email);
	}
	
	@Override
	public String FindUserPW(HashMap<String, String> findUserPWMap) {
		UserDTO userDTO = userMapper.FindUserPW(findUserPWMap);
		if(userDTO.getUserId().isEmpty())
			return "false";
		
		// 임시 비밀번호 발급
		String temp = "tempPW0415";
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", userDTO.getUserId());
		updateMap.put("originPW", userDTO.getPassword());
		updateMap.put("newPW", temp);
		String res = updateUser(updateMap);
		
		return res;
	}

	@Override
	public String LoginUser(String UserId, String Password) {
		String getPassword = userMapper.getUserPassword(UserId);
		if(getPassword.equals(Password))
			return "success";
		else
			return "fail";
	}

	@Override
	public String deleteUser(String UserId,String Password) {
		if(!Password.equals(getUserPassword(UserId)))
			return "fail";
		
		userMapper.deleteUser(UserId);
		
		//UserId에 해당하는 User 못찾았다면 success
		if(checkUser(UserId).equals("fail"))
			return "success";
		
		return "fail";
	}
	
	@Override
	public String checkUser(String UserId) {
		int res = userMapper.checkUser(UserId);
		if(res == 0)
			return "fail";
		else
			return "success";
	}
	
	@Override
	public String getUserPassword(String UserId) {
		return userMapper.getUserPassword(UserId);
	}
}
