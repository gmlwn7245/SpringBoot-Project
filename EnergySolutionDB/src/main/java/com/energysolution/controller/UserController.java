package com.energysolution.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

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


	@RequestMapping("/Main")
	public @ResponseBody String mainView() throws IOException, ParseException {
		/*
		 * String springVersion = org.springframework.core.SpringVersion.getVersion();
		 * 
		 * System.out.println("스프링 프레임워크 버전 : " + springVersion);
		 */
		System.out.println("Main");
		System.out.println(InetAddress.getLocalHost());
		return "This is User";
	}

	@RequestMapping("/Main/Tests")
	public @ResponseBody String mainTest(@RequestParam("userId") String UserId) throws IOException, ParseException {
		System.out.println("Main");
		return "Hello, " + UserId;
	}

	// 로그인
	@PostMapping("/Main/SignIn")
	public JSONObject LoginUser(@RequestBody UserDTO userDTO) {
		System.out.println("로그인");
		UserDTO getUserDTO = userService.LoginUser(userDTO.getUserId(), userDTO.getPassword());
		// UserDTO getUserDTO =
		// userService.LoginUser(jsonObject.get("UserId").toString(),jsonObject.get("Password").toString());
		JSONObject resultJSON = new JSONObject();

		if (getUserDTO == null) {
			resultJSON.put("Message", "잘못된 아이디 또는 비밀번호 입니다.");
			resultJSON.put("result", "fail");
		} else {
			resultJSON.put("Message", userDTO.getUserName() + "님 로그인 성공");
			resultJSON.put("result", "success");
		}

		System.out.println(getUserDTO);
		return resultJSON;
	}

	// 회원가입
	@PostMapping("/Main/SignUp")
	public JSONObject InsertUser(@RequestBody UserDTO userDTO) {
		System.out.println("회원가입 시도");
		// UserDTO userDTO = new
		// UserDTO(jsonObject.get("UserId").toString(),jsonObject.get("Name").toString(),jsonObject.get("Password").toString(),jsonObject.get("Email").toString());
		result = userService.insertUser(userDTO);

		JSONObject resultJSON = new JSONObject();

		if (result != "success")
			resultJSON.put("Message", "회원가입 실패");
		else
			resultJSON.put("Message", userDTO.getName() + "님 회원가입 성공");
		System.out.println("회원가입 성공");

		return resultJSON;
	}

	// 아이디 중복 확인
	@GetMapping("/Main/CheckId")
	public JSONObject checkId(@RequestParam("userId") String UserId) {
		int count = userService.checkUser(UserId);
		System.out.println("아이디 중복 체크");
		JSONObject resultJSON = new JSONObject();
		if (count != 0) {
			resultJSON.put("Message", "이미 존재하는 아이디입니다.");
		} else {
			resultJSON.put("Message", "사용 가능한 아이디입니다.");
		}

		return resultJSON;
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
			resultJSON.put("Message", "비밀번호 변경 실패");
		} else {
			resultJSON.put("Message", "비밀번호 변경 완료");
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
		resultJSON.put("Name", getUserDTO.getUserName());
		resultJSON.put("Email", getUserDTO.getUserEmail());

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