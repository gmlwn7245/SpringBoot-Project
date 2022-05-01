package com.energysolution.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.dto.UserDTO;
import com.energysolution.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestController
public class UserController {
	
	@JsonInclude(Include.NON_NULL)
	private String result;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<String> userIdList;
	
	@Autowired
	UserServiceImpl userService;
	
	
	@RequestMapping("/User")
	public @ResponseBody String mainView() throws IOException, ParseException {
		String springVersion = org.springframework.core.SpringVersion.getVersion();

		System.out.println("스프링 프레임워크 버전 : " + springVersion);

		return "This is User";
	}

	
	// 로그인
	@PostMapping("/User/SignIn")
	public JSONObject LoginUser(@RequestBody UserDTO userDTO) {
		UserDTO getUserDTO = userService.LoginUser(userDTO.getUserId(), userDTO.getUserPassword());
		
		JSONObject resultJSON = new JSONObject();	
		resultJSON.put("message", "로그인 성공");
		
		JSONObject data = new JSONObject();
		data.put("UserId", userDTO.getUserId());
		data.put("Name", getUserDTO.getUserName());
		data.put("Email", getUserDTO.getUserEmail());
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	
	// 회원가입
	@PostMapping("/User/SignUp")
	public JSONObject InsertUser(@RequestBody UserDTO userDTO) {
		result = userService.insertUser(userDTO);
		
		if(result != "success")
			return null;

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", "회원가입 성공");
		
		return resultJSON;
	}
	
	//아이디 중복 확인
	@GetMapping("/User/checkId")
	public JSONObject checkId(@RequestParam("userId") String UserId) {
		int count = userService.checkUser(UserId);
		
		if(count != 0)
			return null;
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", "사용 가능한 아이디입니다.");
		
		return resultJSON;
	}
	
	//id 찾기
	@GetMapping("/User/FindUserId")
	public JSONObject FindUserId(@RequestParam("email") String Email) {
			
		userIdList = userService.FindUserId(Email);
			
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", "아이디 찾기 성공");
		
		JSONObject data = new JSONObject();
		data.put("count", userIdList.size());	
			
		JSONArray Idarr = new JSONArray();
		for(String userId : userIdList) {
			JSONObject Id = new JSONObject();	
			Id.put("UserId", userId);
			Idarr.add(Id);
		}
			
		data.put("UserIdList",Idarr);
		
		resultJSON.put("data", data);
			
		return resultJSON;
	}
	
	//비밀번호 찾기
	@GetMapping("/User/FindUserPW")
	public JSONObject FindUserPW(@RequestParam("userId") String UserId,@RequestParam("email") String Email) throws UnsupportedEncodingException, MessagingException {
		
		HashMap<String, String> findUserPWMap = new HashMap<String, String>();
		findUserPWMap.put("UserId", UserId);
		findUserPWMap.put("Email", Email);
		result = userService.FindUserPW(findUserPWMap);
	
		if(result == "fail")
			return null;
		
		/*이메일로 전송하기*/
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", "이메일로 해당 아이디의 임시 비밀번호가 전송되었습니다.");
		
		JSONObject data = new JSONObject();
		data.put("UserId", UserId);
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	//비밀번호 바꾸기
	@PostMapping("/User/ChangeUserPW")
	public JSONObject updateUser(@RequestBody UserDTO userDTO){
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", userDTO.getUserId());
		updateMap.put("newPW", userDTO.getPassword());
		
		result = userService.updateUser(updateMap);
		
		if(result=="fail") {
			return null;
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", "비밀번호 변경 완료");
		
		return resultJSON;
	}
	
	//탈퇴 기능 나중에 논의
	@RequestMapping("deleteUser")
	public @ResponseBody String deleteUser() {
		
		//result = userService.deleteUser((String)obj.get("UserId"),(String)obj.get("Password"));
		
		//Payment - Bill 테이블과 얽혀있어서 더 구현해야함!
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		//data.put("UserId", (String)obj.get("UserId"));
		data.put("message", "회원 탈퇴 완료");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
}