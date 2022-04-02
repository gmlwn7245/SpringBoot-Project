package com.energysolution.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.energysolution.domain.UserEntity;

@Component
@MapperScan
public interface TestMapperInterface {
	UserEntity test() throws Exception;
}
