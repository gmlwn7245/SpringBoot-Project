package com.energysolution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.domain.UserEntity;
import com.energysolution.service.UserService;

@RestController
public class EnergySolutionController {
	private final UserService userService;
	
	@Autowired
	public EnergySolutionController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/test")
	public UserEntity test() throws Exception{
		return userService.test();
	}
	
	
	@RequestMapping("/")
	public String root_home() throws Exception{
		return "Hi Root Spring!";
	}
	
	@RequestMapping("/sub")
	public String sub_home() throws Exception{
		return "Hi SubRoot Spring!";
	}
	

}
