package com.energysolution.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoAIMapper {
	public void insertPhoto(Map<String, Object> map);
}
