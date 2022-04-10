package com.energysolution.service;

import java.util.HashMap;

import com.energysolution.domain.UserVO;

public interface UserServiceInterface {
	//회원 저장
	public void insertUser(UserVO user);
	
	//회원 수정 (비밀번호)
	public void updateUser(HashMap<String, String> updateMap);
	
	//회원 검색
	public UserVO selectUser(String UserId);
	
	//회원 삭제
	public void deleteUser();
}
