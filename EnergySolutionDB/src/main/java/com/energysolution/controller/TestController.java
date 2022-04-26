package com.energysolution.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.dto.UserDTO;

@RestController
public class TestController {

	//인터넷 브라우저 요청은 get 요청만 가능
	@GetMapping("main")
	public String mainController() {
		return "This is main";
	}
	
	@GetMapping("get")
	public JSONObject getData(@RequestParam int id) {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("id", id);
		data.put("message", "Get 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	@PostMapping("post")
	public JSONObject postData(@RequestBody UserDTO userDTO) {	//MessageConverter의 Jackson Lib가 자동으로 mapping
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		resultJSON.put("result", "success");
		resultJSON.put("message", "Post 성공");
		
		data.put("UserId", userDTO.getUserId());
		data.put("Password", userDTO.getPassword());
		data.put("Email", userDTO.getEmail());
		data.put("Name", userDTO.getName());
		
		resultJSON.put("data", data);
		
		System.out.println(userDTO.getUserId());
		
		return resultJSON;
	}
	
	@PutMapping("put")
	public JSONObject putData() {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("message", "Put 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	@DeleteMapping("delete")
	public JSONObject deleteData() {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("message", "Delete 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
}
