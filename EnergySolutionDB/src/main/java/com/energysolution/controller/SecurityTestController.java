package com.energysolution.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {
	private final PasswordEncoder passwordEncoder = null;
	
    @GetMapping("/password")
    public String getPassword(@RequestParam String password) {
    	String EncodedPW = passwordEncoder.encode(password);
    	
    	return EncodedPW;
    }
}
