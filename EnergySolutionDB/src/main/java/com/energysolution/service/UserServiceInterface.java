package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import com.energysolution.dto.UserDTO;

public interface UserServiceInterface {
	//회원 가입
	public String insertUser(UserDTO userDTO);
	
	//회원 로그인
	public String LoginUser(String UserId, String Password);
	
	//회원 ID 찾기
	public List<String> FindUserId(String Email);
	
	//회원 PW 찾기
	public String FindUserPW(HashMap<String, String> findUserPWMap);
	
	//회원 수정 (비밀번호)
	public String updateUser(HashMap<String, String> updateMap);
		
	//회원 삭제
	public String deleteUser(String UserId, String Password);
	
	//회원 유무 체크
	public String checkUser(String UserId);

	//비밀번호 가져오기
	public String getUserPassword(String UserId);
}
