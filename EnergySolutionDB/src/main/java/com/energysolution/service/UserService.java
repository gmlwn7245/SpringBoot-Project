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
	
	//회원가입
	@Override
	public String insertUser(UserDTO userDTO) {
		userMapper.insertUser(userDTO);
		if(checkUser(userDTO.getUserId())==1)
			return "success";
		return "fail";
	}

	//비밀번호 변경
	@Override
	public String updateUser(HashMap<String, String> updateMap) {
		//새 비밀번호로 변경
		userMapper.updateUser(updateMap);
		
		//아이디로 비밀번호 조회
		String getPassword = userMapper.getUserPassword(updateMap.get("UserId"));
		
		//새 비밀번호로 바뀌었는지 확인
		if(updateMap.get("newPW").equals(getPassword))
			return "success";
		return "fail";
	}
	
	//아이디 찾기
	@Override
	public List<String> FindUserId(String Email) {
		return userMapper.FindUserId(Email);
	}
	
	//비밀번호 찾기
	@Override
	public String FindUserPW(HashMap<String, String> findUserPWMap) {
		//아이디 있는지 확인
		int count = userMapper.checkUserByIdEmail(findUserPWMap);
		System.out.println(count);
		
		if(count==0)
			return "fail";
		
		UserDTO userDTO = userMapper.FindUserPW(findUserPWMap);
		


		//임시 비밀번호 발급
		String temp = "tempPW002";
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", userDTO.getUserId());
		updateMap.put("newPW", temp);
		String res = updateUser(updateMap);
		
		return res;
	}
	
	
	//로그인 하기
	@Override
	public UserDTO LoginUser(String UserId, String Password) {		
		//해당 아이디의 비밀번호가 사용자가 입력한 비밀번호와 일치하는지 확인

		if(Password.equals(getUserPassword(UserId)))
			return userMapper.getUser(UserId);
		return null;
	}
	

	@Override
	public String deleteUser(String UserId,String Password) {
		if(!Password.equals(getUserPassword(UserId)))
			return "fail";
		
		userMapper.deleteUser(UserId);
		
		//UserId에 해당하는 User 못찾았다면 success
		if(checkUser(UserId)==0)
			return "success";
		return "fail";
	}
	
	@Override
	public int checkUser(String UserId) {
		return userMapper.checkUser(UserId);
	}
	
	@Override
	public int checkUserByIdEmail(HashMap<String, String> UserMap) {
		return userMapper.checkUserByIdEmail(UserMap);
	}
	
	@Override
	public String getUserPassword(String UserId) {
		return userMapper.getUserPassword(UserId);
	}
}
