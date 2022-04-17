package com.energysolution.controller;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;
import com.energysolution.dto.TotalBillDTO;
import com.energysolution.service.BillService;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	BillService billService;
	
	private Reader reader;
	private JSONParser parser;
	private JSONObject jsonObject;
	
	@RequestMapping("")
	public @ResponseBody String mainView()  throws IOException, ParseException {
		reader = new FileReader("C:\\Users\\82109\\git\\SpringBoot-Project\\EnergySolutionDB\\src\\main\\java\\com\\energysolution\\controller\\RequestBillData.json");
		parser = new JSONParser();
		jsonObject = (JSONObject) parser.parse(reader) ;
				
		return "This is Billhome";
	}
	
	@RequestMapping("sub")
	public @ResponseBody String sub() throws Exception{
		return "This is Billsub for test";
	}
	
	//고지서 가져오기
	@RequestMapping("getBill")
	public @ResponseBody String getBill(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("GetBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		List<TotalBillDTO> listDTO = billService.getBill((String)obj.get("UserId"), Integer.parseInt(String.valueOf(obj.get("Term"))));
		
		if(listDTO.size()==0)
			return "fail : get Bill";
		return "success : get Bill!";
	}
	
	@RequestMapping("InsertBill")
	public @ResponseBody String setBill() throws Exception{
		JSONArray jsonArr = (JSONArray)jsonObject.get("InsertBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		BillDTO billDTO = new BillDTO(0,(String)obj.get("Date"),Integer.parseInt(String.valueOf(obj.get("Totalfee"))));
		DetailBillDTO detailbillDTO = new DetailBillDTO(0,Integer.parseInt(String.valueOf(obj.get("Waterfee"))),Integer.parseInt(String.valueOf(obj.get("Heatingfee"))),Integer.parseInt(String.valueOf(obj.get("Electricityfee"))));
		PaymentDTO paymentDTO = new PaymentDTO((String)obj.get("UserId"),0);
		
		String result = billService.insertBill(billDTO,detailbillDTO,paymentDTO);
		
		return result+":insert";
	}
	
	//고지서 수정
	@RequestMapping("updateBill")
	public @ResponseBody String updateBill() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String result = billService.updateBill((String)obj.get("UserId"), (String)obj.get("Date"), (String)obj.get("Field"), Integer.parseInt(String.valueOf(obj.get("Fee"))));
		
		return result + ":update!";
	}
	
	//임시로 URL로 설정. 나중에 바꿀예정!
	@RequestMapping("deleteBill")
	public @ResponseBody String deleteBill() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("DeleteBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String result = billService.deleteBill((String)obj.get("UserId"),(String)obj.get("Date"));
		return result +":delete";
	}
}