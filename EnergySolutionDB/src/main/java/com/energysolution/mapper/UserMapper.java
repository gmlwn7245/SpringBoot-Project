package com.energysolution.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import com.energysolution.domain.BillVO;
import com.energysolution.domain.UserVO;

@Component
@MapperScan
public interface UserMapper {

	public void insertUser(UserVO user);
	public void updateUser(String originPW, String newPW);
	public UserVO selectUser();
	public void deleteUser();
}
