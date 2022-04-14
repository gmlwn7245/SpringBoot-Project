package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import com.energysolution.domain.UserDTO;

public interface UserServiceInterface {
	//회원 가입
	public void insertUser(UserDTO userDTO);
	
	//회원 로그인
	public UserDTO LoginUser(String UserId);
	
	//회원 ID 찾기
	public List<UserDTO> FindUserId(String Email);
	
	//회원 PW 찾기
	public UserDTO FindUserPW(HashMap<String, String> findUserPWMap);
	
	//회원 수정 (비밀번호)
	public void updateUser(HashMap<String, String> updateMap);
		
	//회원 삭제
	public void deleteUser(String UserId);
}
