package com.energysolution.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.energysolution.service.BillServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class BillController {
	
	@JsonInclude(Include.NON_NULL)
	private String result;
	
	@Autowired
	BillServiceImpl billService;
	
	private JSONObject jsonObject;
	
	@GetMapping("")
	public @ResponseBody String Root() {		
		return "This is Root";
	}
	
	@GetMapping("/Bill")
	public @ResponseBody String mainView() {
		return "This is Billhome";
	}
	
	//특정 날짜 고지서 가져오기
	@GetMapping(value = "/bill/getData", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getBill(@RequestParam("userId") String UserId, @RequestParam("date") String date) {
		System.out.println("=====특정 날짜 데이터 조회=====");
		System.out.println("userId:"+UserId);
		System.out.println("date:"+date);
		TotalBillDTO totalBillDTO = billService.getBill(UserId, date);
			
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		
		if(totalBillDTO.getBillId()==-1) {
			System.out.println("fail");
			resultJSON.addProperty("message", UserId+"님 고지서 조회 실패");
			resultJSON.addProperty("result", "fail");
			return gson.toJson(resultJSON);
		}
		
		System.out.println("success");
		resultJSON.addProperty("message", UserId+"님 고지서 조회 성공");
		resultJSON.addProperty("result", "success");
		resultJSON.addProperty("UserId", UserId);
		resultJSON.addProperty("date", totalBillDTO.getDate());
		resultJSON.addProperty("electUse", totalBillDTO.getElectUse());
		resultJSON.addProperty("waterUse", totalBillDTO.getWaterUse());
		resultJSON.addProperty("electFee", totalBillDTO.getElectFee());
		resultJSON.addProperty("waterFee", totalBillDTO.getWaterFee());
		resultJSON.addProperty("publicFee", totalBillDTO.getPublicFee());	
		resultJSON.addProperty("totalFee", totalBillDTO.getTotalFee());	
		
		return gson.toJson(resultJSON);
	}
	
	// 원 그래프 데이터 가져오기
	@GetMapping(value = "/bill/pieGraph", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getPie(@RequestParam("userId") String UserId) {
		System.out.println("=====원그래프데이터 조회=====");
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();

		TotalBillDTO totalBillDTO = billService.getGraphData(UserId);
		if(totalBillDTO.getBillId()==-1) {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "데이터가 없습니다.");
			return gson.toJson(resultJSON);
		}
	
		resultJSON.addProperty("result", "success");
		resultJSON.addProperty("message", "데이터 조회 성공");
		resultJSON.addProperty("electFee", totalBillDTO.getElectFee());
		resultJSON.addProperty("waterFee", totalBillDTO.getWaterFee());
		resultJSON.addProperty("publicFee", totalBillDTO.getPublicFee());
		
		int tFee = Integer.parseInt(totalBillDTO.getTotalFee());
		int eFee = Integer.parseInt(totalBillDTO.getElectFee());
		int wFee = Integer.parseInt(totalBillDTO.getWaterFee());
		int pFee = Integer.parseInt(totalBillDTO.getPublicFee());
		int iFee = tFee-(eFee+wFee+pFee);
		
		resultJSON.addProperty("individualFee", Integer.toString(iFee));
		
		return gson.toJson(resultJSON);
	}
	
	// 특정 기간 고지서 가져오기
	@GetMapping(value = "/bill/getDataList", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getBillTerm(@RequestParam("userId") String UserId) {
		//,@RequestParam("month") int term
		System.out.println("=====6개월 데이터 조회=====");		
		List<TotalBillDTO> listDTO = billService.getBillTerm(UserId, 6);
		
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		JsonObject data = new JsonObject();
		
		if(listDTO.size()==0) {
			data.addProperty("message", UserId+"님 6개월 고지서 조회 실패");
			data.addProperty("result", "fail");
			resultJSON.add("data", data);
			return gson.toJson(resultJSON);
		}
		
		data.addProperty("message", UserId+"님 6개월 고지서 조회 성공");
		data.addProperty("result", "success");
		JsonArray BillList = new JsonArray();
		for(TotalBillDTO dto : listDTO) {
			JsonObject detailData = new JsonObject();
			detailData.addProperty("date", dto.getDate());
			detailData.addProperty("electUse", dto.getElectUse());
			detailData.addProperty("waterUse", dto.getWaterUse());
			detailData.addProperty("electFee", dto.getElectFee());
			detailData.addProperty("waterFee", dto.getWaterFee());
			detailData.addProperty("publicFee", dto.getPublicFee());
			detailData.addProperty("totalFee", dto.getTotalFee());
			BillList.add(detailData);
		}
		
		data.add("dataList", BillList);
		resultJSON.add("data", data);
		
		return gson.toJson(resultJSON);
	}
	
	//고지서 등록하기
	@PostMapping(value = "/bill/register", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String setBill(@RequestBody TotalBillDTO totalDTO) throws Exception{
		System.out.println("======고지서 등록======");
		BillDTO billDTO = new BillDTO(0,
				totalDTO.getDate(),
				totalDTO.getTotalFee());
		DetailBillDTO detailbillDTO = new DetailBillDTO(0,
				totalDTO.getPublicFee(),
				totalDTO.getElectUse(),
				totalDTO.getWaterUse(),
				totalDTO.getElectFee(),
				totalDTO.getWaterFee()
				);
		
		PaymentDTO paymentDTO = new PaymentDTO(totalDTO.getUserId());
		String result = billService.insertBill(billDTO,detailbillDTO,paymentDTO);
		
		
		Gson gson = new Gson();
		JsonObject resultJSON = new JsonObject();
		if(result=="fail") {
			resultJSON.addProperty("result", "fail");
			resultJSON.addProperty("message", "등록 실패하였습니다.");
			return gson.toJson(resultJSON);
		}
	
		resultJSON.addProperty("result", "success");
		resultJSON.addProperty("message", "등록이 완료되었습니다.");
					
		return gson.toJson(resultJSON);
	}
	
	//고지서 부분 수정
	@RequestMapping("/Bill/updateBillField")
	public @ResponseBody JSONObject updateBillField() {
		JSONArray jsonArr = (JSONArray)jsonObject.get("UpdateBillField");
		JSONObject obj = (JSONObject) jsonArr.get(0);
		
		String result = billService.updateBillField(
				(String)obj.get("UserId"),
				(String)obj.get("Date"),
				(String)obj.get("Field"),
				Integer.parseInt(String.valueOf(obj.get("Fee"))));
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(result=="fail") {
			data.put("Message", "수정에 실패하였습니다.");
			return resultJSON;
		}
		

		data.put("Message", "수정이 완료되었습니다.");
		data.put("count", 2);
		
		resultJSON.put("Data", data);
		
		return resultJSON;
	}
	
	//고지서 전체 수정
	@PostMapping("/bill/update")
	public JSONObject updateBill(@RequestBody TotalBillDTO totalDTO) {
		
		String result = billService.updateBill(totalDTO);
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("result", result);
		if(result=="fail") {
			resultJSON.put("message", "고지서 수정 실패하였습니다.");
			return resultJSON;
		}
		
		String UserId = totalDTO.getUserId();
		String date = totalDTO.getDate();
		
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