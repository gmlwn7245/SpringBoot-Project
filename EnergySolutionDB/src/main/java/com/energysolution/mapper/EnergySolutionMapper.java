package com.energysolution.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.domain.BillVO;
import com.energysolution.domain.UserVO;

@Mapper
public class EnergySolutionMapper implements EnergySolutionMapperInterface {

	@Override
	public UserVO getUser() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillVO getBill() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
