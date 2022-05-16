package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface WeatherAIService {
		
	// 날씨 데이터 저장
	public HashMap<String, Object> insertWeather(int nx, int ny);
}
