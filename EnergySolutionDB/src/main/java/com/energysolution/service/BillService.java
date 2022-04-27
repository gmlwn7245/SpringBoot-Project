package com.energysolution.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;
import com.energysolution.dto.TotalBillDTO;
import com.energysolution.mapper.BillMapper;

@Service
public class BillService implements BillServiceInterface{

	@Autowired
	private BillMapper billMapper;
	private int billId;
	
	//고지서 등록
	@Override
	public String insertBill(BillDTO billDTO, DetailBillDTO detailbillDTO, PaymentDTO paymentDTO) {
		billId = getLastBillId()+1;
		 
		billDTO.setBillId(billId);
		detailbillDTO.setBillId(billId);
		paymentDTO.setBillId(billId);
		
		billMapper.insertBill(billDTO);
		billMapper.insertDetailBill(detailbillDTO);
		billMapper.insertPayment(paymentDTO);
		
		return checkBill(billId);
	}
	
	//마지막 BillId 가져옴
	public int getLastBillId() {
		return billMapper.getLastBillId(); 
	}
	
	//특정 날짜 고지서 출력
	@Override
	public TotalBillDTO getBill(String UserId, String date) {
		//BillId 가져옴
		int BillId = getBillId(UserId, date);
		
		//각각 Bill, DetailBill에서 데이터 조회
		BillDTO billDTO = billMapper.getBill(BillId);
		DetailBillDTO detailbillDTO = billMapper.getDetailBill(BillId);
		TotalBillDTO totalBillDTO = makeDTO(billDTO, detailbillDTO);
		return totalBillDTO;
	}

	//특정 기간 고지서 출력
	@Override
	public List<TotalBillDTO> getBillTerm(String UserId, int term) {
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		
		int rest = nowMonth - term;
		
		//해당 기간의 모든 요금 상세량 넘겨줌
		List<TotalBillDTO> arrDTO = new ArrayList<TotalBillDTO>();
		
	
		if(rest <=0) {
			for(int i=12+rest; i<=12; i++) {
				int year = nowYear-1;
				int month = i;
				String date = year+"-"+month;
				int BillId = getBillId(UserId, date);
				
				BillDTO billDTO = billMapper.getBill(BillId);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(BillId);
				arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
			}
			for(int i=1; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				int BillId = getBillId(UserId, date);
				
				BillDTO billDTO = billMapper.getBill(BillId);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(BillId);
				arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
			}
		}else {
			for(int i=rest; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				int BillId = getBillId(UserId, date);
				
				BillDTO billDTO = billMapper.getBill(BillId);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(BillId);arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
			}
		}
		
		return arrDTO;
	}
	
	// 전체 고지서 수정
	@Override
	public String updateBill(TotalBillDTO totalDTO) {
		HashMap<String, Integer> updateBillMap = new HashMap<String, Integer>();
		String UserId = totalDTO.getUserId();
		String date = totalDTO.getDate();
		int BillId = getBillId(UserId,date);
		updateBillMap.put("BillId", BillId);
		updateBillMap.put("TotalFee",totalDTO.getTotalFee());
		updateBillMap.put("WaterFee", totalDTO.getWaterFee());
		updateBillMap.put("WaterUsage", totalDTO.getWaterUsage());
		updateBillMap.put("ElectricityFee", totalDTO.getElectricityFee());
		updateBillMap.put("ElectricityUsage", totalDTO.getElectricityUsage());
		
		billMapper.updateBill(updateBillMap);
		billMapper.updateDetailBill(updateBillMap);
		
		return checkUpdateBill(BillId, updateBillMap);
	}

	// 전체 고지서 수정 확인
	@Override
	public String checkUpdateBill(int BillId, HashMap<String, Integer> updateBillMap) {
		TotalBillDTO totDTO = makeDTO(billMapper.getBill(BillId), billMapper.getDetailBill(BillId));
		if(updateBillMap.get("TotalFee") == totDTO.getTotalFee()
				&& updateBillMap.get("WaterFee")==totDTO.getWaterFee()
				&& updateBillMap.get("WaterUsage")==totDTO.getWaterUsage()
				&& updateBillMap.get("ElectricityFee")==totDTO.getElectricityFee()
				&& updateBillMap.get("ElectricityUsage")==totDTO.getElectricityUsage())
			return "true";
		return "false";
	}
	
	// 특정 필드 고지서 수정
	@Override
	public String updateBillField(String UserId, String Date, String Field, int fee) {
		HashMap<String, String> updateBillMap = new HashMap<String, String>();
		String table;
		
		if(Field.equals("TotalFee"))
			table = "Bill";
		else
			table = "detailBill";
		
		int BillId = getBillId(UserId, Date);
		updateBillMap.put("BillId", Integer.toString(BillId));
		updateBillMap.put("table",table);
		updateBillMap.put("Field", Field);
		updateBillMap.put("fee", Integer.toString(fee));
		
		billMapper.updateBillField(updateBillMap);
		
		return checkUpdateBillField(updateBillMap);
	}
	
	// 고지서 삭제 나중에 필요하면 사용
	@Override
	public String deleteBill(String UserId, String Date) {
		HashMap<String, String> deleteBillMap = new HashMap<String, String>();
		deleteBillMap.put("UserId", UserId);
		deleteBillMap.put("date", Date);
		
		int billId = billMapper.getBillId(deleteBillMap);
		billMapper.deletePayment(billId);
		billMapper.deleteDetailBill(billId);
		billMapper.deleteBill(billId);

		return checkDeleteBill(billId);
	}
	
	// 콘솔 출력
	public void print_bill(BillDTO billDTO, DetailBillDTO detailbillDTO) {
		System.out.println("BillId:"+billDTO.getBillId());
		System.out.println("날짜:"+billDTO.getDate());
		System.out.println("총요금:"+billDTO.getTotalFee());
		
		System.out.println("수도요금:"+detailbillDTO.getWaterFee());
		System.out.println("수도사용량:"+detailbillDTO.getWaterUsage());
		System.out.println("전기요금:"+detailbillDTO.getElectricityFee());
		System.out.println("전기사용량:"+detailbillDTO.getElectricityUsage());
		System.out.println("--------------fin--------------");
	}
	
	//DTO 통합
	public TotalBillDTO makeDTO(BillDTO billDTO, DetailBillDTO detailbillDTO) {
		return new TotalBillDTO(
				billDTO.getBillId(),
				billDTO.getDate(),
				billDTO.getTotalFee(),
				detailbillDTO.getWaterFee(),
				detailbillDTO.getWaterUsage(),
				detailbillDTO.getElectricityFee(),
				detailbillDTO.getElectricityUsage());
	}
	
	// 고지서 ID 가져오기
	@Override
	public int getBillId(String UserId, String date) {
		HashMap<String, String> getBillIdMap = new HashMap<String, String>();
		getBillIdMap.put("UserId", UserId);
		getBillIdMap.put("date", date);
		
		int BillId = billMapper.getBillId(getBillIdMap);
		return BillId;
	}
	
	// 고지서 유무 확인
	@Override
	public String checkBill(int billId) {
		int cnt = billMapper.checkBill(billId);
		
		if(cnt == 1)
			return "success";
		return "fail";
	}
	
	
	// 고지서 특정 필드 값 변경 확인
	@Override
	public String checkUpdateBillField(HashMap<String, String> updateCheckBillMap) {	
		String fee = updateCheckBillMap.get("fee");
		updateCheckBillMap.remove("fee");
		
		String cnt = Integer.toString(billMapper.checkUpdateBillField(updateCheckBillMap));
		if(fee.equals(cnt))
			return "success";
		return "fail";
	}
	
	// 고지서 삭제 확인
	@Override
	public String checkDeleteBill(int billId) {
		int cnt = 0;
		cnt += billMapper.checkDeleteBill(billId);
		cnt += billMapper.checkDeleteDetailBill(billId);
		cnt += billMapper.checkDeletePayment(billId);
		
		if(cnt==0)
			return "success";
		return "fail";
	}
}
