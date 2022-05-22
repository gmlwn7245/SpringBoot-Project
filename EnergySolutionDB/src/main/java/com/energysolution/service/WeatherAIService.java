package com.energysolution.service;

import java.util.HashMap;

import com.energysolution.dto.RealTimeDataDTO;
import com.energysolution.dto.nxnyDTO;


public interface WeatherAIService {
	// 주소 저장
	public void insertAddress(HashMap<String, String> addressMap);
	// 날씨 데이터 저장
	public void insertWeather();
	
	// nx, ny 데이터 가져오기
	public nxnyDTO getNXNY();
	
	// 실시간 요금 가져오기
	public RealTimeDataDTO getRealTimeData();
	
	// 데이터 삭제
	public void deleteWeatherData();
}
