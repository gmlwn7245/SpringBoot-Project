package com.energysolution.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping("/Bill/getImage")
	public @ResponseBody Map<String, Object> Image(@RequestParam("file") List<MultipartFile> file) {
		System.out.println("getImage 요청");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (file != null) {
				map.put("result", "success");
			} else {
				map.put("result", "FAIL");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
		}
		return map;
	}

//	@GetMapping("/getData")
//	@ResponseBody
//	public Map<String, Object> getData(){
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			
//			if(userService.getReqData().size()> 0) {
//				System.out.println("::>> "+userService.getReqData());
//				map.put("result","SUCC");
//				map.put("data", userService.getReqData());
//			}else {
//				map.put("result", "FAIL");
//			}
//
//			
//		}catch(Exception e) {
//			map.put("result", "FAIL");
//			e.printStackTrace();
//		}
//		return map;
//	}
	
	@GetMapping("/Bill")
	public @ResponseBody String mainView() {
		return "This is Billhome";
	}
	
	//특정 날짜 고지서 가져오기
	@GetMapping("/Bill/GetBill")
	public JSONObject getBill(@RequestParam("userId") String UserId, @RequestParam("date") String date) {
			
		TotalBillDTO totalBillDTO = billService.getBill(UserId, date);
			
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(totalBillDTO.getDate()==null) {
			data.put("Message", UserId+"님 고지서 조회 실패");
			resultJSON.put("Data",data);
			return resultJSON;
		}
		

		JSONObject TotalBill = new JSONObject();
		TotalBill.put("UserId", UserId);
		TotalBill.put("date", totalBillDTO.getDate());
		TotalBill.put("TotalFee", totalBillDTO.getTotalFee());
		TotalBill.put("WaterFee", totalBillDTO.getWaterFee());
		TotalBill.put("WaterUsage", totalBillDTO.getWaterUsage());
		TotalBill.put("ElectricityFee", totalBillDTO.getElectricityFee());
		TotalBill.put("ElectricityUsage", totalBillDTO.getElectricityUsage());	
		
		data.put("TotalBill", TotalBill);
		data.put("Message", UserId+"님 고지서 조회 성공");
		
		resultJSON.put("Data",data);
		return resultJSON;
	}
	
	//특정 기간 고지서 가져오기
	@GetMapping("/Bill/GetBillList")
	public JSONObject getBillTerm(@RequestParam("userId") String UserId) {
		//,@RequestParam("month") int term
		List<TotalBillDTO> listDTO = billService.getBillTerm(UserId, 6);
		
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(listDTO.size()==0) {
			data.put("Message", UserId+"님 6개월 고지서 조회 실패");
			resultJSON.put("Data", data);
			return resultJSON;
		}
		
		data.put("Message", UserId+"님 6개월 고지서 조회 성공");
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
		resultJSON.put("Data", data);
		
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
			
			JSONObject resultJSON = new JSONObject();
			JSONObject data = new JSONObject();
			if(result=="fail") {
				data.put("Message", "등록 실패하였습니다.");
				resultJSON.put("Data", data);
				return resultJSON;
			}
			

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
	@PostMapping("/Bill/UpdateBill")
	public JSONObject updateBill(@RequestBody TotalBillDTO totalDTO) {
		
		String result = billService.updateBill(totalDTO);
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(result=="fail") {
			data.put("Message", "고지서 수정 실패하였습니다.");
			return resultJSON;
		}
		
		String UserId = totalDTO.getUserId();
		String date = totalDTO.getDate();
		

		data.put("Message", UserId+"님의 "+date+"의 고지서 수정이 완료되었습니다.");
		
		resultJSON.put("Data", data);

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