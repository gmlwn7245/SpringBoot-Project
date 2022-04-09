package com.energysolution.repository;

import com.energysolution.domain.UserVO;

public interface UserDAOInterface {
	//회원 저장
	public void insertUser(UserVO user);
	
	//회원 수정 (비밀번호)
	public void updateUser(String originPW, String newPW);
	
	//회원 검색
	public UserVO selectUser();
	
	//회원 삭제
	public void deleteUser();
		
}
