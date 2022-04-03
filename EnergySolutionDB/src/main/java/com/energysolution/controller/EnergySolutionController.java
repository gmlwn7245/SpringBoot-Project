package com.energysolution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.domain.BillVO;
import com.energysolution.domain.UserVO;
import com.energysolution.service.BillService;
import com.energysolution.service.UserService;

@RestController
public class EnergySolutionController {
	private final UserService userService;
	private final BillService billService;
	
	@Autowired
	public EnergySolutionController(UserService userService, BillService billService) {
		this.userService = userService;
		this.billService = billService;
	}
	
	@RequestMapping("/getUser")
	public UserVO getUser() throws Exception{
		return userService.getUser();
	}
	
	@RequestMapping("/getBill")
	public BillVO getBill() throws Exception{
		return billService.getBill();
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
