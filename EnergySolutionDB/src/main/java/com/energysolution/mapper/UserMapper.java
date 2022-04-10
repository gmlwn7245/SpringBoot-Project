package com.energysolution.mapper;


import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.domain.UserVO;

@Mapper
public interface UserMapper {
	public void insertUser(UserVO uservo);
	public void updateUser(HashMap<String, String> updateMap);
	public UserVO selectUser(String UserId);
	public void deleteUser(String UserId);
}
