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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Controller
@RequestMapping("/Bill")
public class BillController {
	
	@JsonInclude(Include.NON_NULL)
	private String result;
	
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
	
	//특정 날짜 고지서 가져오기
		@RequestMapping("getBill")
		public @ResponseBody String getBill(Model mv) {
			JSONArray jsonArr = (JSONArray)jsonObject.get("GetBill");
			JSONObject obj = (JSONObject) jsonArr.get(0);
			
			TotalBillDTO totalBillDTO = billService.getBill((String)obj.get("UserId"), (String)obj.get("Date"));
			
			if(totalBillDTO.getDate()==null)
				return "fail : get Bill";
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("count", 2);
			
			JSONObject data = new JSONObject();
			data.put("UserId", (String)obj.get("UserId"));
			data.put("date", totalBillDTO.getDate());
			data.put("TotalFee", totalBillDTO.getTotalFee());
			data.put("WaterFee", totalBillDTO.getWaterFee());
			data.put("WaterUsage", totalBillDTO.getWaterUsage());
			data.put("ElectricityFee", totalBillDTO.getElectricityFee());
			
			resultJSON.put("data", data);
			
			return resultJSON.toJSONString();
		}
	
	//특정 기간 고지서 가져오기
	@RequestMapping("getBillTerm")
	public @ResponseBody String getBillTerm(Model mv) {
		JSONArray jsonArr = (JSONArray)jsonObject.get("GetBillTerm");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		List<TotalBillDTO> listDTO = billService.getBillTerm((String)obj.get("UserId"), Integer.parseInt(String.valueOf(obj.get("Term"))));
		
		if(listDTO.size()==0)
			return "fail : get Bill";
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONArray arr = new JSONArray();
		for(TotalBillDTO dto : listDTO) {
			JSONObject data = new JSONObject();
			data.put("UserId", (String)obj.get("UserId"));
			data.put("Date", dto.getDate());
			data.put("Totalfee", dto.getTotalFee());
			data.put("Waterfee", dto.getWaterFee());
			data.put("Electricityfee", dto.getElectricityFee());
			arr.add(data);
		}
		
		resultJSON.put("data", arr);
		return resultJSON.toJSONString();
	}
	
	//고지서 등록하기
	@RequestMapping("InsertBill")
	public @ResponseBody String setBill() throws Exception{
		JSONArray jsonArr = (JSONArray)jsonObject.get("InsertBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		BillDTO billDTO = new BillDTO(
				(String)obj.get("Date"),
				Integer.parseInt(String.valueOf(obj.get("TotalFee"))));
		DetailBillDTO detailbillDTO = new DetailBillDTO(
				Integer.parseInt(String.valueOf(obj.get("WaterFee"))),
				Integer.parseInt(String.valueOf(obj.get("WaterUsage"))),
				Integer.parseInt(String.valueOf(obj.get("ElectricityFee"))),
				Integer.parseInt(String.valueOf(obj.get("ElectricityUsage"))));
		PaymentDTO paymentDTO = new PaymentDTO((String)obj.get("UserId"));
		
		String result = billService.insertBill(billDTO,detailbillDTO,paymentDTO);
		
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("date", (String)obj.get("Date"));
		data.put("message", "등록이 완료되었습니다.");
		
		resultJSON.put("data", data);
		
		return resultJSON.toJSONString();
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
	@RequestMapping("updateBill")
	public @ResponseBody String updateBill() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateBill");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String UserId = (String)obj.get("UserId");
		BillDTO billDTO = new BillDTO(
				(String)obj.get("Date"),
				Integer.parseInt(String.valueOf(obj.get("TotalFee"))));
		DetailBillDTO detailbillDTO = new DetailBillDTO(
				Integer.parseInt(String.valueOf(obj.get("WaterFee"))),
				Integer.parseInt(String.valueOf(obj.get("WaterUsage"))),
				Integer.parseInt(String.valueOf(obj.get("ElectricityFee"))),
				Integer.parseInt(String.valueOf(obj.get("ElectricityUsage"))));
		
		String result = billService.updateBill(UserId, billDTO, detailbillDTO);
		if(result=="fail") {
			return "fail";
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("count", 2);
		
		JSONObject data = new JSONObject();
		data.put("UserId", (String)obj.get("UserId"));
		data.put("Date", (String)obj.get("Date"));
		data.put("message", "수정이 완료되었습니다.");
		
		resultJSON.put("data", data);
		return resultJSON.toJSONString();
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