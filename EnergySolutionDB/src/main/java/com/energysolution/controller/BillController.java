package com.energysolution.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.domain.BillVO;
import com.energysolution.service.BillService;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	BillService billService;
	
	@RequestMapping("")
	public @ResponseBody String mainView() {
		return "This is Billhome";
	}
	
	@RequestMapping("sub")
	public @ResponseBody String sub() throws Exception{
		return "This is Billsub for test";
	}
	
	@RequestMapping("getBill")
	public @ResponseBody String getUser(Model mv) {
		/*int BillId = 0001;
		BillVO billvo = billService.selectBill(BillId); //id로 user정보 가져옴
		String Name = uservo.getName();
		String Email = uservo.getEmail();
		String Password = uservo.getPassword();
		
		// testView.html에 데이터 넣고 출력
		mv.addAttribute("UserId",UserId);
		mv.addAttribute("Name",Name);
		mv.addAttribute("Email",Email);
		mv.addAttribute("Password",Password);*/
		return "success : select user!";
	}
	
	@RequestMapping("insertBill")
	public @ResponseBody String setBill() throws Exception{
		BillVO billvo = new BillVO();
		billvo.setBillId(1000);
		billvo.setBilldate("2022-01");
		billvo.setTotalFee(14000);
		
		billService.insertBill(billvo);
		return "success : insert bill!";
	}
	
	//임시로 URL로 설정. 나중에 바꿀예정!
	@RequestMapping("deleteBill")
	public @ResponseBody String deleteUser() {
		int BillId = 1000;
		
		billService.deleteBill(BillId);
		return "success : delete bill!";
	}
}