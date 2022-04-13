package com.energysolution.mapper;


import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.domain.UserDTO;

@Mapper
public interface UserMapper {
	public void insertUser(UserDTO uservo);
	public void updateUser(HashMap<String, String> updateMap);
	public UserDTO selectUser(String UserId);
	public void deleteUser(String UserId);
}
