package com.energysolution.controller;

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
	private UserDAO userdao;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("insertUser")
	public String setUser() throws Exception{
		UserVO uservo = new UserVO();
		uservo.setUserId("user044");
		uservo.setName("잉유저");
		uservo.setPassword("4324sdf");
		uservo.setEmail("user03@user.com");
		
		userdao.insertUser(uservo);
		return "success!";
	}
	
	@RequestMapping("getUser")
	public UserVO getUser() throws Exception{
		return null;
	}
		
	@RequestMapping("")
	public @ResponseBody String mainView() {
		return "This is home";
	}
	
	@RequestMapping("test")
	public @ResponseBody String getView(Model mv) {
		String UserId = "user01";
		UserVO uservo = userService.selectUser(UserId);
		String Name = uservo.getName();
		String Email = uservo.getEmail();
		String Password = uservo.getPassword();
		
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