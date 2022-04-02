package com.energysolution.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.energysolution.domain.UserEntity;

@Mapper
public class TestMapper implements TestMapperInterface {

	@Override
	public UserEntity test() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
