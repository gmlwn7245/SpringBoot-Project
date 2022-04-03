package com.energysolution.repository;

import com.energysolution.domain.UserVO;

public interface UserDAOInterface {
	//회원 저장
	public UserVO insert(UserVO user);
	
	//회원 수정 (비밀번호)
	public UserVO update(String originPW, String newPW);
	
	//회원 검색
	public UserVO select();
	
	//회원 삭제
	public UserVO delete();
		
}
