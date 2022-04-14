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
	public String LoginUser(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("LoginUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String UserId = (String)obj.get("UserId");
		
		UserDTO userDTO = userService.LoginUser(UserId);
		String Name = userDTO.getName();
		String Email = userDTO.getEmail();
		String Password = userDTO.getPassword();
		
		// User.html에 데이터 넣고 출력
		mv.addAttribute("Successfield", "로그인성공");
		mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);
		
		return "User";
	}
	
	
	// 회원가입
	@RequestMapping("InsertUser")
	public String InsertUser(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("InsertUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
			
		String UserId = (String)obj.get("UserId");
		String Name = (String)obj.get("Name");
		String Email = (String)obj.get("Email");
		String Password = (String)obj.get("Password");
		
		UserDTO userDTO = new UserDTO(UserId,Name,Password,Email);

		userService.insertUser(userDTO);
		
		// User.html에 데이터 넣고 출력
		mv.addAttribute("Successfield", "회원가입성공");
		mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);
		
		return "User";
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
	
	//id 찾기
	@RequestMapping("FindUserId")
	public @ResponseBody String FindUserId() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findID");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		List<UserDTO> list = userService.FindUserId((String)obj.get("Email"));
		
		for(UserDTO dto : list) {
			System.out.println("UserId:"+dto.getUserId());
		}
		
		return "success : find user!";
	}
	
	//PW 찾기
	@RequestMapping("FindUserPW")
	public @ResponseBody String FindUserPW(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findPW");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> findUserPWMap = new HashMap<String, String>();
		findUserPWMap.put("UserId", (String)obj.get("UserId"));
		findUserPWMap.put("Email", (String)obj.get("Email"));
		UserDTO userDTO = userService.FindUserPW(findUserPWMap);
		
		// 임시 비밀번호 발급
		String temp = "tempPW0414";
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", userDTO.getUserId());
		updateMap.put("originPW", userDTO.getPassword());
		updateMap.put("newPW", temp);
		userService.updateUser(updateMap);
		
		//이메일로 전송하는 코드 추가!
		
		System.out.println("Success : PW is changed");
		
		// User.html에 데이터 넣고 출력
		mv.addAttribute("Successfield", "로그인성공");
		mv.addAttribute("UserId",userDTO.getUserId());
		mv.addAttribute("Name",userDTO.getUserName());
		mv.addAttribute("Email",userDTO.getUserEmail());
		mv.addAttribute("Password",userDTO.getUserPassword());
		
		return "Success : PW is changed";
	}
}