package com.energysolution.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.domain.UserVO;
import com.energysolution.repository.UserDAO;
import com.energysolution.service.UserService;

@Controller
public class EnergySolutionController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("insertUser")
	public @ResponseBody String setUser() throws Exception{
		UserVO uservo = new UserVO();
		uservo.setUserId("user4885");
		uservo.setName("사팔팔오");
		uservo.setPassword("pw4885");
		uservo.setEmail("user4885@user.com");
		
		userService.insertUser(uservo);
		return "insert : success!";
	}
	
	@RequestMapping("updateUser")
	public @ResponseBody String updateUser() throws Exception{
		String UserId = "user4885";
		UserVO uservo = userService.selectUser(UserId);	//id로 user정보 가져옴
		String newPW = "4885pw";
		
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("UserId", UserId);
		updateMap.put("originPW", uservo.getPassword());
		updateMap.put("newPW", newPW);
		
		
		userService.updateUser(updateMap);
		
		return "update : success!";
	}
		
	@RequestMapping("")
	public @ResponseBody String mainView() {
		return "This is home";
	}
	
	@RequestMapping("getUser")
	public @ResponseBody String getUser(Model mv) {
		String UserId = "user4885";
		UserVO uservo = userService.selectUser(UserId);	//id로 user정보 가져옴
		String Name = uservo.getName();
		String Email = uservo.getEmail();
		String Password = uservo.getPassword();
		
		// testView.html에 데이터 넣고 출력
		mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);
		System.out.println("select user!");
		return null;
	}
	
	@RequestMapping("sub")
	public @ResponseBody String sub() throws Exception{
		return "This is sub";
	}
	

}