package com.energysolution.security;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AccountController {	
	@Autowired
	AccountService accountService;
	
	
	
	@PostMapping("/Main/SignIn")
	public @ResponseBody JSONObject LoginUser(@RequestBody Account account) {
		UserDetails accounts = accountService.loadUserByUsername(account.getUsername());
		
		JSONObject resultJSON = new JSONObject();	
		resultJSON.put("message", "로그인 성공");
		/*
		JSONObject data = new JSONObject();
		data.put("UserId", accounts.getUsername());
		
		resultJSON.put("data", data);*/
		
		return resultJSON;
	}
		
	@RequestMapping("/accessDenied_page")
	public String accessDenied() {
		return "accessDenied_page";
	}
}
