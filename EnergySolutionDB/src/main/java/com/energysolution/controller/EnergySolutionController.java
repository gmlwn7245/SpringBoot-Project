package com.energysolution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.domain.BillVO;
import com.energysolution.domain.UserVO;
import com.energysolution.repository.UserDAO;

@RestController
public class EnergySolutionController {
	
	@Autowired
	private UserDAO userdao;
	
	@RequestMapping("/insertUser")
	public String getUser() throws Exception{
		UserVO uservo = new UserVO();
		uservo.setUserId("user044");
		uservo.setName("잉유저");
		uservo.setPassword("4324sdf");
		uservo.setEmail("user03@user.com");
		
		userdao.insertUser(uservo);
		return "success!";
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
