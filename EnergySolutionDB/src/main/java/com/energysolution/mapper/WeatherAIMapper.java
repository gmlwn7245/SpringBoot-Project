package com.energysolution.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherAIMapper {
	public void insertWeatherData(HashMap<String, Object> weatherMap);
}
