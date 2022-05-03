package com.energysolution.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class AccountController {	
	@Autowired
	AccountService accountService;
	
    
	@Autowired
	JwtUtils jwtUtils;
		
	@PostMapping("/Main/SignIn")
	public ResponseEntity<Object> LoginUser(@RequestBody Account account, HttpServletResponse response) {
		if(!accountService.isUser(account))
			return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
		try {
			Account accounts = accountService.loadUserByUsername(account.getUsername());
			String token = jwtUtils.createJwt(accounts);
			response.setHeader("jwt-auth-token", token);
			return new ResponseEntity<Object>("login Success", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/Bill/Test")
	public ResponseEntity<Object> testUser(HttpServletRequest request) {
		try {
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> tokenMap = jwtUtils.checkJwt(token);
			
			Account account = new ObjectMapper().convertValue(tokenMap.get("UserId"), Account.class);
			
			return new ResponseEntity<Object>(account, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
		
	@RequestMapping("/accessDenied_page")
	public String accessDenied() {
		return "accessDenied_page";
	}
}
