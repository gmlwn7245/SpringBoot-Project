package com.energysolution.repository;

import com.energysolution.domain.UserEntity;

public interface UserDAOInterface {
	//회원 저장
	public UserEntity insert(UserEntity user);
	
	//회원 수정 (비밀번호)
	public UserEntity update(String originPW, String newPW);
	
	//회원 검색
	public UserEntity select();
	
	//회원 삭제
	public UserEntity delete();
		
}
