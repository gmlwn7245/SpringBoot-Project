package com.energysolution.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.domain.UserVO;

@Mapper
public interface UserMapper {

	public void insertUser(UserVO uservo);
	public void updateUser(String originPW, String newPW);
	public UserVO selectUser(String UserId);
	public void deleteUser();
}
