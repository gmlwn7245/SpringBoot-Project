package com.energysolution.controller;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.energysolution.service.PhotoAIServiceImp;
import com.energysolution.service.WeatherAIService;

@RestController
public class AIController {
	
	@Autowired
	WeatherAIService weatherAIService;
	
	@Autowired
	PhotoAIServiceImp photoAIService;
	
	@PostMapping("/scanPhoto")
	public @ResponseBody Map<String, Object> getPhoto(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("이미지 스캔 요청");
		
		System.out.println(file.getName());		//파일 파라미터 이름
		System.out.println(file.getSize());		//파일 사이즈
		System.out.println(file.getOriginalFilename());	//파일 실제 이름
		byte[] data = file.getBytes();			//파일 실제 내용
		
		Map<String, Object> param = new HashMap<String, Object>();
		//Convert to Blob
		try {
			Blob blob = new javax.sql.rowset.serial.SerialBlob(data);
			param.put("file", blob);
			param.put("file_name", file.getOriginalFilename());
			param.put("file_size",file.getSize());
			photoAIService.insertPhoto(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 실시간 요금 추정
	@GetMapping("/realTimeFee")
	public @ResponseBody Map<String, String> getWeather(@RequestParam("nx") int nx, @RequestParam("ny") int ny) {
		System.out.println("getWeather 요청");
		Map<String, Integer> map = new HashMap<>();
		Map<String, String> ResultMap = new HashMap<>();
		try {
			map.put("nx", nx);
			map.put("ny", ny);
			
			weatherAIService.insertWeather(nx, ny);
			ResultMap.put("result","success");
		} catch (Exception e) {
			e.printStackTrace();
			ResultMap.put("result","fail");
		}
				
		System.out.println("getWeather 반환");
		return ResultMap;
	}
	
}
