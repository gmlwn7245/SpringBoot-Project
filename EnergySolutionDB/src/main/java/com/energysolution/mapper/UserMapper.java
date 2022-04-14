package com.energysolution.mapper;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.energysolution.domain.UserDTO;

@Mapper
public interface UserMapper {
	public void insertUser(UserDTO userDTO);
	public void updateUser(HashMap<String, String> updateMap);
	public List<UserDTO> FindUserId(String Email);
	public UserDTO FindUserPW(HashMap<String, String> findUserPWMap);
	public UserDTO LoginUser(String UserId);
	public void deleteUser(String UserId);
}
