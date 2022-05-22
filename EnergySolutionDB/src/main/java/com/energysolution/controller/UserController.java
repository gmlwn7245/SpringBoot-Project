package com.energysolution.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.dto.UserDTO;
import com.energysolution.service.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class UserController {

	private String result;
	private List<String> userIdList;

	@Autowired
	UserServiceImpl userService;
	
	
	@RequestMapping("/ttt")
	public @ResponseBody String maisssnView() {
		/*
		 * String springVersion = org.springframework.core.SpringVersion.getVersion();
		 * System.out.println("스프링 프레임워크 버전 : " + springVersion);
		 */
		System.out.println("tt");
		return "t";
	}
	
	@RequestMapping("/Main")
	public @ResponseBody String mainView() throws IOException, ParseException {
		System.out.println("Main");
		System.out.println(InetAddress.getLocalHost());
		return "This is Main";
	}

	@RequestMapping("/Main/Tests")
	public @ResponseBody String mainTest(@RequestParam("userId") String UserId) throws IOException, ParseException {
		System.out.println("Main");
		return "Hello, " + UserId;
	}

	// 로그인
	@PostMapping(value="/main/login",produces="text/plain;charset=UTF-8")
	public String loginUser(UserDTO userDTO) {
		System.out.println("==========================================");
		System.out.println("로그인:"+userDTO.getUserId());
		UserDTO getUserDTO = userService.LoginUser(userDTO.getUserId(), userDTO.getPassword());
		
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		if (getUserDTO == null) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "잘못된 아이디 또는 비밀번호 입니다.");
		} else {
			resultJSON.addProperty("result", "success");
			resultJSON.addProperty("message", getUserDTO.getName() + "님 로그인 성공!");
			resultJSON.addProperty("name", getUserDTO.getName());
			resultJSON.addProperty("email", getUserDTO.getEmail());
		}

		System.out.println(getUserDTO);
		return gson.toJson(resultJSON);
	}

	// 회원가입
	@PostMapping(value="/main/register",produces="text/plain;charset=UTF-8")
	public String registerUser(UserDTO userDTO) {	
		System.out.println("==========================================");
		System.out.println("회원가입 시도");
		//UserDTO userDTO = new UserDTO(js.get("userId").toString(),js.get("name").toString(),js.get("password").toString(),js.get("email").toString());
		System.out.println("UserID:"+userDTO.getUserId());
		System.out.println("Password:"+userDTO.getPassword());
		System.out.println("Name:"+userDTO.getName());
		System.out.println("Email:"+userDTO.getEmail());
		
		result = userService.insertUser(userDTO);

		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();

		if (result != "success") {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "등록 실패");
		}else {
			resultJSON.addProperty("result", "success");
			resultJSON.addProperty("message", userDTO.getName() + "님 회원가입 성공");
		}
			
		System.out.println("회원가입 성공");
		return gson.toJson(resultJSON);
	}

	// 아이디 중복 확인
	@GetMapping(value="/main/checkId",produces="text/plain;charset=UTF-8")
	public String checkId(@RequestParam("userId") String UserId) {
		int count = userService.checkUser(UserId);
		System.out.println("==========================================");
		System.out.println("아이디 중복 체크:"+UserId);
		
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		if (count != 0) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "이미 존재하는 아이디입니다.");
		} else {
			resultJSON.addProperty("result", "success");
			resultJSON.addProperty("message", "사용 가능한 아이디입니다.");
		}
		return gson.toJson(resultJSON);
	}
	
	// 아이디 중복 확인
	@PostMapping("/main/checkIdPost")
	public String checkIdPost(@RequestBody HashMap<String, String> map) {
		String UserId = map.get("UserId").toString();
		int count = userService.checkUser(UserId);
		System.out.println("==========================================");
		System.out.println("아이디 중복 체크:"+UserId);
		
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		if (count != 0) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "This ID already exists.");
		} else {
			resultJSON.addProperty("result", "success");
			resultJSON.addProperty("message", "This ID is available.");
		}
		return gson.toJson(resultJSON);
	}

	// 비밀번호 바꾸기
	@PostMapping("/Main/ChangePW")
	public JSONObject updateUser(@RequestBody UserDTO userDTO) {
		System.out.println("비밀번호 변경 시도 : " + userDTO.getUserId());
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", userDTO.getUserId());
		updateMap.put("newPW", userDTO.getPassword());

		result = userService.updateUser(updateMap);

		JSONObject resultJSON = new JSONObject();

		if (result == "fail") {
			resultJSON.put("message", "비밀번호 변경 실패");
		} else {
			resultJSON.put("message", "비밀번호 변경 완료");
		}

		return resultJSON;
	}

	// ID 찾기
	@GetMapping("/Main/FindUserId")
	public JSONObject FindUserId(@RequestParam("email") String Email) {

		userIdList = userService.FindUserId(Email);

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("Message", "아이디 찾기 성공");

		JSONObject data = new JSONObject();
		data.put("count", userIdList.size());

		JSONArray Idarr = new JSONArray();
		for (String userId : userIdList) {
			JSONObject Id = new JSONObject();
			Id.put("UserId", userId);
			Idarr.add(Id);
		}

		data.put("UserIdList", Idarr);

		resultJSON.put("data", data);

		return resultJSON;
	}

	// 비밀번호 찾기
	@GetMapping("/Main/FindUserPW")
	public JSONObject FindUserPW(@RequestParam("userId") String UserId, @RequestParam("email") String Email)
			throws UnsupportedEncodingException, MessagingException {

		HashMap<String, String> findUserPWMap = new HashMap<String, String>();
		findUserPWMap.put("UserId", UserId);
		findUserPWMap.put("Email", Email);
		result = userService.FindUserPW(findUserPWMap);

		if (result == "fail")
			return null;

		/* 이메일로 전송하기 */

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("Message", "이메일로 해당 아이디의 임시 비밀번호가 전송되었습니다.");

		JSONObject data = new JSONObject();
		data.put("UserId", UserId);

		resultJSON.put("data", data);

		return resultJSON;
	}

	// 로그인
	@PostMapping("/Main/RetrofitSignIn")
	public JSONObject RetrofitLoginUser(@RequestBody HashMap<String, String> map) {
		System.out.println("Retrofit 로그인");
		UserDTO getUserDTO = userService.LoginUser(map.get("UserId"), map.get("Password"));
		if (getUserDTO == null)
			return null;

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("UserId", getUserDTO.getUserId());
		resultJSON.put("Name", getUserDTO.getName());
		resultJSON.put("Email", getUserDTO.getEmail());

		System.out.println(getUserDTO);
		return resultJSON;
	}

	// Retrofit 회원가입
	@PostMapping("/Main/RetrofitSignUp")
	public JSONObject RetrofitInsertUser(@RequestBody HashMap<String, String> map) {
		System.out.println("회원가입 시도");
		UserDTO userDTO = new UserDTO(map.get("UserId"), map.get("Name"), map.get("Password"), map.get("Email"));
		result = userService.insertUser(userDTO);

		if (result != "success")
			return null;
		System.out.println("회원가입 성공");

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("Name", userDTO.getName());

		return resultJSON;
	}

	// 탈퇴 기능 나중에 논의
	@RequestMapping("deleteUser")
	public @ResponseBody String deleteUser() {

		// result =
		// userService.deleteUser((String)obj.get("UserId"),(String)obj.get("Password"));

		// Payment - Bill 테이블과 얽혀있어서 더 구현해야함!

		if (result == "fail") {
			return "fail";
		}

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);

		JSONObject data = new JSONObject();
		// data.put("UserId", (String)obj.get("UserId"));
		data.put("Message", "회원 탈퇴 완료");

		resultJSON.put("data", data);

		return resultJSON.toJSONString();
	}

}