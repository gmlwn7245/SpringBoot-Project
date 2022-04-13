package com.energysolution.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.domain.UserDTO;
import com.energysolution.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	private Reader reader;
	private JSONParser parser;
	private JSONObject jsonObject;
	
	@RequestMapping("")
	public @ResponseBody String mainView() throws IOException, ParseException {
		reader = new FileReader("C:\\Users\\82109\\git\\SpringBoot-Project\\EnergySolutionDB\\src\\main\\java\\com\\energysolution\\controller\\Data.json");
		parser = new JSONParser();
		jsonObject = (JSONObject) parser.parse(reader) ;
		
		return "This is home";
	}
	
	@RequestMapping("sub")
	public @ResponseBody String sub() throws Exception{
		return "This is sub for test";
	}
	
	@RequestMapping("getUser")
	public @ResponseBody String getUser(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("SelectUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String UserId = (String)obj.get("UserId");
		
		UserDTO userDTO = userService.selectUser(UserId);	//id로 user정보 가져옴
		String Name = userDTO.getName();
		String Email = userDTO.getEmail();
		String Password = userDTO.getPassword();
		
		System.out.println("Name: "+Name);
		System.out.println("Email: "+Email);
		System.out.println("Password: "+Password);
		
		
		// testView.html에 데이터 넣고 출력
		/*mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);*/
		
		return "success : select user!";
	}
	
	@RequestMapping("insertUser")
	public @ResponseBody String insertUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("InsertUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		UserDTO userDTO = new UserDTO((String)obj.get("UserId"),(String)obj.get("Name"),(String)obj.get("Password"),(String)obj.get("Email"));
		
		userService.insertUser(userDTO);
		return "success : insert user!";
	}
	
	@RequestMapping("updateUser")
	public @ResponseBody String updateUser() throws Exception{
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", (String)obj.get("UserId"));
		updateMap.put("originPW", (String)obj.get("originPW"));
		updateMap.put("newPW", (String)obj.get("newPW"));
				
		userService.updateUser(updateMap);
		
		return "update : success!";
	}
	
	//임시로 URL로 설정. 나중에 바꿀예정!
	@RequestMapping("deleteUser")
	public @ResponseBody String deleteUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("DeleteUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		userService.deleteUser((String)obj.get("UserId"));
		
		return "success : delete user!";
	}
}