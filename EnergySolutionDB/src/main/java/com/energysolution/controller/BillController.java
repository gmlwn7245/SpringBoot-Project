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
	
	@GetMapping("/Test/Get")
	public @ResponseBody String testPost() {
		return "This is Get";
	}
	
	@PostMapping("/Test/Post")
	public @ResponseBody String testGet() {	
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		return "This is Root";
	}
	
	@GetMapping("/Bill")
	public @ResponseBody String mainView() {
		return "This is Billhome";
	}
	
	//특정 날짜 고지서 가져오기
	@GetMapping("/bill/getData")
	public JSONObject getBill(@RequestParam("userId") String UserId, @RequestParam("date") String date) {
			
		TotalBillDTO totalBillDTO = billService.getBill(UserId, date);
			
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(totalBillDTO.getDate()==null) {
			data.put("message", UserId+"님 고지서 조회 실패");
			data.put("result", "fail");
			resultJSON.put("data",data);
			return resultJSON;
		}
		

		data.put("message", UserId+"님 고지서 조회 성공");
		data.put("result", "success");
		data.put("UserId", UserId);
		data.put("date", totalBillDTO.getDate());
		data.put("electUse", totalBillDTO.getElectUse());
		data.put("waterUse", totalBillDTO.getWaterUse());
		data.put("electFee", totalBillDTO.getElectFee());
		data.put("waterFee", totalBillDTO.getWaterFee());
		data.put("publicFee", totalBillDTO.getPublicFee());	
		data.put("totalFee", totalBillDTO.getTotalFee());	
		
		resultJSON.put("data",data);
		return resultJSON;
	}
	
	//특정 기간 고지서 가져오기
	@GetMapping("/bill/getDataList")
	public JSONObject getBillTerm(@RequestParam("userId") String UserId) {
		//,@RequestParam("month") int term
		List<TotalBillDTO> listDTO = billService.getBillTerm(UserId, 6);
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(listDTO.size()==0) {
			data.put("message", UserId+"님 6개월 고지서 조회 실패");
			data.put("result", "fail");
			resultJSON.put("data", data);
			return resultJSON;
		}
		
		data.put("message", UserId+"님 6개월 고지서 조회 성공");
		data.put("result", "success");
		JSONArray BillList = new JSONArray();
		for(TotalBillDTO dto : listDTO) {
			JSONObject detailData = new JSONObject();
			detailData.put("date", dto.getDate());
			detailData.put("electUse", dto.getElectUse());
			detailData.put("waterUse", dto.getWaterUse());
			detailData.put("electFee", dto.getElectFee());
			detailData.put("waterFee", dto.getWaterFee());
			detailData.put("publicFee", dto.getPublicFee());
			detailData.put("totalFee", dto.getTotalFee());
			BillList.add(detailData);
		}
		
		data.put("BillList", BillList);
		resultJSON.put("Data", data);
		
		return resultJSON;
	}
	
	//고지서 등록하기
		@PostMapping("/bill/register")
		public JSONObject setBill(@RequestBody TotalBillDTO totalDTO) throws Exception{
			
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
			
			JSONObject resultJSON = new JSONObject();
			JSONObject data = new JSONObject();
			if(result=="fail") {
				data.put("result", "fail");
				data.put("Message", "등록 실패하였습니다.");
				resultJSON.put("Data", data);
				return resultJSON;
			}
			

			data.put("result", "success");
			data.put("Message", "등록이 완료되었습니다.");
			resultJSON.put("Data", data);
						
			return resultJSON;
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