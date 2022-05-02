package com.energysolution.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.energysolution.dto.UserDTO;

@Mapper
public interface UserMapper {
	public void insertUser(UserDTO userDTO);
	public void updateUser(HashMap<String, String> updateMap);
	public List<String> FindUserId(String Email);
	public UserDTO FindUserPW(HashMap<String, String> findUserPWMap);
	public String getUserPassword(String UserId);
	public void deleteUser(String UserId);
	
	public int checkUser(String UserId);
	public int checkUserByIdEmail(HashMap<String, String> UserMap);
	public UserDTO getUser(String UserId);
}
