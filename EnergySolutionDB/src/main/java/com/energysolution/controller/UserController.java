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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.dto.UserDTO;
import com.energysolution.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Controller
@RequestMapping("/User")
public class UserController {
	
	@JsonInclude(Include.NON_NULL)
	private String result;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<String> userIdList;
	
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
		JSONArray jArray = new JSONArray();
		JSONObject result = new JSONObject();
		
		result.put("id", "userId");
		result.put("pw", "userpw");
		result.put("email", "userEmail");
		result.put("name", "userName");
		return result.toJSONString();
	}
	
	@RequestMapping(value="testjson", produces="application/json; charset=utf-8")
	public void jsonTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("name", "me");
		jsonObj.put("age", 144);
		
		String jsons = jsonObj.toString();
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
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();		
		JSONObject data = new JSONObject();
		data.put("UserId", UserId);
		data.put("message", "로그인 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
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

		result = userService.insertUser(userDTO);
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", UserId);
		data.put("message", "회원가입 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
	//pw 바꾸기
	@RequestMapping("updateUser")
	public @ResponseBody String updateUser() throws Exception{
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", (String)obj.get("UserId"));
		updateMap.put("newPW", (String)obj.get("newPW"));
		
		System.out.println((String)obj.get("UserId"));
		
		result = userService.updateUser(updateMap);
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("message", "비밀번호 변경 완료");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
	//임시로 URL로 설정. 나중에 바꿀예정!
	@RequestMapping("deleteUser")
	public @ResponseBody String deleteUser() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("DeleteUser");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		result = userService.deleteUser((String)obj.get("UserId"),(String)obj.get("Password"));
		
		//Payment - Bill 테이블과 얽혀있어서 더 구현해야함!
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("message", "회원 탈퇴 완료");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
	//id 찾기
	@RequestMapping("FindUserId")
	public @ResponseBody String FindUserId() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findID");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		userIdList = userService.FindUserId((String)obj.get("Email"));
		
		if(userIdList.size()==0)
			return "fail";
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		
		JSONArray arr = new JSONArray();
		for(String userId : userIdList) {
			System.out.println("UserId:"+userId);
			JSONObject data = new JSONObject();	
			data.put("UserId", userId);
			arr.add(data);
		}
		
		resultJSON.put("data",arr);
		
		return resultJSON.toJSONString();
	}
	
	//PW 찾기
	@RequestMapping("FindUserPW")
	public @ResponseBody String FindUserPW(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("findPW");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		HashMap<String, String> findUserPWMap = new HashMap<String, String>();
		findUserPWMap.put("UserId", (String)obj.get("UserId"));
		findUserPWMap.put("Email", (String)obj.get("Email"));
		result = userService.FindUserPW(findUserPWMap);
	
		/*이메일로 전송하기*/
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("message", "임시 비밀번호 발송됨");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
	
}