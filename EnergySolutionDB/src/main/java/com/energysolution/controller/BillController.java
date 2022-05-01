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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;
import com.energysolution.dto.TotalBillDTO;
import com.energysolution.dto.UserDTO;
import com.energysolution.service.BillServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestController
public class BillController {
	
	@JsonInclude(Include.NON_NULL)
	private String result;
	
	@Autowired
	BillServiceImpl billService;
	
	private JSONObject jsonObject;
	
	@RequestMapping("/Bill")
	public @ResponseBody String mainView() {
		
		return "This is Billhome";
	}
	
	//특정 날짜 고지서 가져오기
	@GetMapping("/Bill/GetBill")
	public JSONObject getBill(@RequestParam("userId") String UserId,@RequestParam("date") String date) {
			
		TotalBillDTO totalBillDTO = billService.getBill(UserId, date);
			
		if(totalBillDTO.getDate()==null)
			return null;
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", UserId+"님 고지서 조회 성공");
		JSONObject data = new JSONObject();
		data.put("UserId", UserId);
		data.put("date", totalBillDTO.getDate());
		data.put("TotalFee", totalBillDTO.getTotalFee());
		data.put("WaterFee", totalBillDTO.getWaterFee());
		data.put("WaterUsage", totalBillDTO.getWaterUsage());
		data.put("ElectricityFee", totalBillDTO.getElectricityFee());
		data.put("ElectricityUsage", totalBillDTO.getElectricityUsage());	
		resultJSON.put("data", data);
			
		return resultJSON;
	}
	
	//특정 기간 고지서 가져오기
	@GetMapping("/Bill/GetBillList")
	public JSONObject getBillTerm(@RequestParam("userId") String UserId,@RequestParam("month") int term) {
		
		List<TotalBillDTO> listDTO = billService.getBillTerm(UserId, term);
		
		if(listDTO.size()==0)
			return null;
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", UserId+"님 "+term+"개월 고지서 조회 성공");
		
		JSONObject data = new JSONObject();
		data.put("UserId", UserId);
		data.put("term", term);
		JSONArray BillList = new JSONArray();
		for(TotalBillDTO dto : listDTO) {
			JSONObject detailData = new JSONObject();
			detailData.put("Date", dto.getDate());
			detailData.put("Totalfee", dto.getTotalFee());
			detailData.put("Waterfee", dto.getWaterFee());
			detailData.put("Electricityfee", dto.getElectricityFee());
			BillList.add(detailData);
		}
		
		data.put("BillList", BillList);
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	//고지서 등록하기
		@PostMapping("/Bill/InsertBill")
		public JSONObject setBill(@RequestBody TotalBillDTO totalDTO) throws Exception{
			
			BillDTO billDTO = new BillDTO(
					totalDTO.getDate(),
					totalDTO.getTotalFee());
			DetailBillDTO detailbillDTO = new DetailBillDTO(
					totalDTO.getWaterFee(),
					totalDTO.getWaterUsage(),
					totalDTO.getElectricityFee(),
					totalDTO.getElectricityUsage()
					);
			PaymentDTO paymentDTO = new PaymentDTO(totalDTO.getUserId());
			
			String result = billService.insertBill(billDTO,detailbillDTO,paymentDTO);
			
			if(result=="fail") {
				return null;
			}
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", "등록이 완료되었습니다.");
						
			return resultJSON;
		}
	
	//고지서 부분 수정
	@RequestMapping("updateBillField")
	public @ResponseBody String updateBillField() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateBillField");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String result = billService.updateBillField(
				(String)obj.get("UserId"),
				(String)obj.get("Date"),
				(String)obj.get("Field"),
				Integer.parseInt(String.valueOf(obj.get("Fee"))));
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("Date", (String)obj.get("Date"));
		data.put("Field", (String)obj.get("Field"));
		data.put("message", "수정이 완료되었습니다.");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
	}
	
	//고지서 전체 수정
	@PostMapping("/Bill/UpdateBill")
	public JSONObject updateBill(@RequestBody TotalBillDTO totalDTO) {
		
		
		String result = billService.updateBill(totalDTO);
		if(result=="fail") {
			return null;
		}
		
		String UserId = totalDTO.getUserId();
		String date = totalDTO.getDate();
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", UserId+"님의 "+date+"의 고지서 수정이 완료되었습니다.");

		return resultJSON;
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