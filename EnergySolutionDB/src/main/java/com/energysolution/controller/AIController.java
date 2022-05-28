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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.energysolution.dto.PhotoDataDTO;
import com.energysolution.dto.RealTimeDataDTO;
import com.energysolution.service.PhotoAIServiceImpl;
import com.energysolution.service.WeatherAIService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class AIController {

	private final String url = "http://localhost:5000";

	@Autowired
	WeatherAIService weatherAIService;

	@Autowired
	PhotoAIServiceImpl photoAIService;


	@PostMapping(value = "/scanPhoto", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getPhoto(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("======이미지 스캔 요청======");		
		System.out.println(file.getName()); // 파일 파라미터 이름
		System.out.println(file.getSize()); // 파일 사이즈
		System.out.println(file.getOriginalFilename()); // 파일 실제 이름
		byte[] data = file.getBytes(); // 파일 실제 내용

				
		Map<String, Object> param = new HashMap<String, Object>();
		// Convert to Blob
		try {
			Blob blob = new javax.sql.rowset.serial.SerialBlob(data);
			param.put("file", blob);
			param.put("file_name", file.getOriginalFilename());
			param.put("file_size", file.getSize());

			photoAIService.insertPhoto(param);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Err 발생");
		}

		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();

		// 데이터 요청
		RestTemplate template = new RestTemplate();
		String response = template.getForObject(url + "/getPhoto", String.class);

		if (response.isEmpty() || response.equals("fail")) { // 파이썬 연결부분
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "이미지 스캔에 실패하였습니다.");
		} else if (response.equals("success")) { // 파이썬 연결부분
			PhotoDataDTO photoDataDTO = photoAIService.getPhotoData();
			if (photoDataDTO == null) {
				resultJSON.addProperty("result", "fail");
				resultJSON.addProperty("message", "데이터를 불러올 수 없습니다.");
			} else {
				resultJSON.addProperty("result", "success");
				resultJSON.addProperty("message", "이미지 스캔 완료!");
				resultJSON.add("data", photoDataDTO.revertToJson());
			}
		}

		// 사진, 고지서 결과 데이터 전부 삭제
		photoAIService.deletePhotoData();

		return gson.toJson(resultJSON);
	}

	// 실시간 요금 추정
	@GetMapping(value = "/realTimeData", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getWeather(@RequestParam("region1") String region1,
			@RequestParam("region2") String region2, @RequestParam("region3") String region3) {
		System.out.println("=====getWeather 요청=====");

		// Address 데이터베이스에 넣기
		HashMap<String, String> addressMap = new HashMap<>();
		addressMap.put("region1", region1);
		addressMap.put("region2", region2);
		addressMap.put("region3", region3);
		weatherAIService.insertAddress(addressMap);

		// 리턴 메세지
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();

		// nx, ny 좌표 데이터 요청 후 API 값 넣기
		RestTemplate template = new RestTemplate();
		String response = template.getForObject(url + "/getAddress", String.class);

		if (response.isEmpty() || response.equals("fail")) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "위치 데이터 로딩에 실패하였습니다.");
			return gson.toJson(resultJSON);

		} else if (response.equals("success")) {
			weatherAIService.insertWeather();
		}

		// 실시간 요금 데이터 요청
		response = template.getForObject(url + "/getRealTimeData", String.class);
		if (response.isEmpty() || response.equals("fail")) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "예측량 데이터 로딩에 실패하였습니다.");
			return gson.toJson(resultJSON);

		} else if (response.equals("success")) {
			RealTimeDataDTO RTDdto = weatherAIService.getRealTimeData();
			String datas = gson.toJson(RTDdto);
			resultJSON.addProperty("result", "success");
			resultJSON.addProperty("message", "예측량 데이터입니다.");
			resultJSON.addProperty("electFee", RTDdto.getPredictedFee());
		}

		// 데이터 삭제
		weatherAIService.deleteWeatherData();

		return gson.toJson(resultJSON);
	}
}
