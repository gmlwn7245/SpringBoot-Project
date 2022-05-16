package com.energysolution.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.energysolution.service.PhotoAIService;
import com.energysolution.service.WeatherAIService;

@RestController
public class AIController {
	
	@Autowired
	WeatherAIService weatherAIService;
	
	@Autowired
	PhotoAIService photoAIService;
	
	@PostMapping("/getPhoto")
	public @ResponseBody Map<String, Object> Image(@RequestParam("file") List<MultipartFile> file) {
		System.out.println("getImage 요청");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (file != null) {
				photoAIService.insertPhoto(file);
				map.put("result", "success");
			} else {
				map.put("result", "FAIL");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/getWeather")
	public @ResponseBody Map<String, Integer> Image(@RequestParam("nx") int nx, @RequestParam("nx") int ny) {
		System.out.println("getWeather 요청");
		Map<String, Integer> map = new HashMap<>();
		try {
			map.put("nx", nx);
			map.put("ny", ny);
			
			weatherAIService.insertWeather(nx, ny);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
