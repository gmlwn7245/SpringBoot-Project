package com.energysolution.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.dto.UserDTO;
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
		reader = new FileReader("C:\\Users\\82109\\git\\SpringBoot-Project\\EnergySolutionDB\\src\\main\\java\\com\\energysolution\\controller\\RequestUserData.json");
		parser = new JSONParser();
		jsonObject = (JSONObject) parser.parse(reader) ;
		
		return "This is home";
	}
	
	@RequestMapping("sub")
	public @ResponseBody String sub() throws Exception{
		return "This is sub for test";
	}
	
	// 로그인
	@RequestMapping("LoginUser")
	public @ResponseBody String LoginUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("LoginUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String UserId = (String)obj.get("UserId");
		String Password = (String)obj.get("Password");
		String result = userService.LoginUser(UserId, Password);
		
		/*// User.html에 데이터 넣고 출력
		mv.addAttribute("Successfield", "로그인성공");
		mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);*/
		
		return result;
	}
	
	
	// 회원가입
	@RequestMapping("InsertUser")
	public @ResponseBody String InsertUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("InsertUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
			
		String UserId = (String)obj.get("UserId");
		String Name = (String)obj.get("Name");
		String Email = (String)obj.get("Email");
		String Password = (String)obj.get("Password");
		
		UserDTO userDTO = new UserDTO(UserId,Name,Password,Email);

		String result = userService.insertUser(userDTO);
		
		
		return result;
	}
	
	//pw 바꾸기
	@RequestMapping("updateUser")
	public @ResponseBody String updateUser() throws Exception{
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", (String)obj.get("UserId"));
		updateMap.put("originPW", (String)obj.get("originPW"));
		updateMap.put("newPW", (String)obj.get("newPW"));
		
		System.out.println((String)obj.get("UserId"));
		
		String result = userService.updateUser(updateMap);
		
		return result;
	}
	
	//임시로 URL로 설정. 나중에 바꿀예정!
	@RequestMapping("deleteUser")
	public @ResponseBody String deleteUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("DeleteUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String result = userService.deleteUser((String)obj.get("UserId"),(String)obj.get("Password"));
		
		//Payment - Bill 테이블과 얽혀있어서 더 구현해야함!
		
		return result;
	}
	
	//id 찾기
	@RequestMapping("FindUserId")
	public @ResponseBody String FindUserId() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findID");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		List<String> list = userService.FindUserId((String)obj.get("Email"));
		
		if(list.size()==0)
			return "fail";
		
		for(String str : list) {
			System.out.println("UserId:"+str);
		}
		
		return "success";
	}
	
	//PW 찾기
	@RequestMapping("FindUserPW")
	public @ResponseBody String FindUserPW(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findPW");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> findUserPWMap = new HashMap<String, String>();
		findUserPWMap.put("UserId", (String)obj.get("UserId"));
		findUserPWMap.put("Email", (String)obj.get("Email"));
		String result = userService.FindUserPW(findUserPWMap);
	
		//이메일로 전송하기
		
		return result;
	}
	
	
}