package com.energysolution.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.domain.BillDTO;
import com.energysolution.domain.DetailBillDTO;
import com.energysolution.domain.PaymentDTO;
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
	public @ResponseBody String getBill(Model mv) {
		String UserId = "user01";		
		int term = 12;
		billService.getBill(UserId, term);
		
		return "success : select user!";
	}
	
	@RequestMapping("insertBill")
	public @ResponseBody String setBill() throws Exception{
		
		BillDTO billDTO = new BillDTO();
		billDTO.setBillId(10010);
		billDTO.setBilldate("2022-1");
		billDTO.setTotalfee(14000);
		
		DetailBillDTO detailbillDTO = new DetailBillDTO();
		
		detailbillDTO.setElectricityfee(10000);
		detailbillDTO.setWaterfee(2000);
		detailbillDTO.setHeatingfee(2000);
		
		PaymentDTO paymentDTO = new PaymentDTO("user01",10010);
		
		billService.insertBill(billDTO);
		billService.insertDetailBill(detailbillDTO);
		billService.insertPayment(paymentDTO);
		
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