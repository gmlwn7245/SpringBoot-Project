package com.energysolution.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AndroidConnectionController {

	@RequestMapping("")
	public @ResponseBody String mainController() {
		
		
		return "This is main";
	}
	
	@SuppressWarnings({ "unchecked", "null" }) 
	@RequestMapping("/android")
	public void androidTestWithRequestAndResponse(HttpServletRequest request) {
		
	}
}
