package com.energysolution.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import com.energysolution.domain.BillVO;
import com.energysolution.domain.UserVO;

@Component
@MapperScan
public interface EnergySolutionMapperInterface {
	UserVO getUser() throws Exception;
	BillVO getBill() throws Exception;
}
