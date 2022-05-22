package com.energysolution.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.energysolution.dto.RealTimeDataDTO;
import com.energysolution.dto.nxnyDTO;

@Mapper
public interface WeatherAIMapper {
	public void insertAddress(HashMap<String, String> addressMap);
	public void insertWeatherData(HashMap<String, Object> weatherMap);
	
	// nx,ny 가져오기
	public nxnyDTO getNXNY();
	
	//실시간 요금 가져오기
	public RealTimeDataDTO getRealTimeData();
	
	//데이터 삭제
	public void deleteWeatherData();
	public void deleteAddress();
	public void deleteNXNY();
}
