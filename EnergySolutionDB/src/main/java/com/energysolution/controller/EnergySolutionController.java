package com.energysolution.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergySolutionController {
	@RequestMapping("/")
	public String root_home() throws Exception{
		return "Hi Root Spring!";
	}
	
	@RequestMapping("/sub")
	public String sub_home() throws Exception{
		return "Hi SubRoot Spring!";
	}
}
