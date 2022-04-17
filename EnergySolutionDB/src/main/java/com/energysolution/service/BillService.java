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
	public String insertBill(BillDTO billDTO, DetailBillDTO detailbillDTO,PaymentDTO paymentDTO) {
		billId=220417;
		
		billDTO.setBillId(billId);
		detailbillDTO.setBillId(billId);
		paymentDTO.setBillId(billId);
		
		billMapper.insertBill(billDTO);
		billMapper.insertDetailBill(detailbillDTO);
		billMapper.insertPayment(paymentDTO);
		
		return checkBill(billId);
	}

	//고지서 내용 출력
	@Override
	public List<TotalBillDTO> getBill(String UserId, int term) {
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		
		int rest = nowMonth - term;
		
		//return값.. 수정 필요
		List<TotalBillDTO> arrDTO = new ArrayList<TotalBillDTO>();
		
		//UserId값과 date값 넘겨줌
		HashMap<String, String> getBillMap= new HashMap<String,String>();
		getBillMap.put("UserId", UserId);
		
		if(rest <=0) {
			for(int i=12+rest; i<=12; i++) {
				int year = nowYear-1;
				int month = i;
				String date = year+"-"+month;
				getBillMap.put("date", date);
				
				BillDTO billDTO = billMapper.getBill(getBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(getBillMap);
				arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
				getBillMap.remove("date");
			}
			for(int i=1; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				getBillMap.put("date", date);
				
				BillDTO billDTO = billMapper.getBill(getBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(getBillMap);
				arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
				getBillMap.remove("date");
			}
		}else {
			for(int i=rest; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				getBillMap.put("date", date);
				
				BillDTO billDTO = billMapper.getBill(getBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(getBillMap);
				arrDTO.add(makeDTO(billDTO, detailbillDTO));
				
				print_bill(billDTO, detailbillDTO);
				getBillMap.remove("date");
			}
		}
		
		return arrDTO;
	}

	@Override
	// 고지서 수정
	public String updateBill(String UserId, String Date, String Field, int fee) {
		HashMap<String, String> updateBillMap = new HashMap<String, String>();
		String table;
		
		if(Field.equals("Totalfee"))
			table = "Bill";
		else
			table = "detailBill";
		
		updateBillMap.put("UserId", UserId);
		updateBillMap.put("date", Date);
		updateBillMap.put("table",table);
		updateBillMap.put("Field", Field);
		updateBillMap.put("fee", Integer.toString(fee));
		
		billMapper.updateBill(updateBillMap);
		
		return checkUpdateBill(updateBillMap);
	}
	
	// 고지서 삭제
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
	
	public void print_bill(BillDTO billDTO, DetailBillDTO detailbillDTO) {
		System.out.println("BillId:"+billDTO.getBillId());
		System.out.println("날짜:"+billDTO.getDate());
		System.out.println("총요금:"+billDTO.getTotalfee());
		
		System.out.println("수도:"+detailbillDTO.getWaterFee());
		System.out.println("난방:"+detailbillDTO.getHeatingFee());
		System.out.println("전기:"+detailbillDTO.getElectricityFee());
		System.out.println("--------------fin--------------");
	}
	
	public TotalBillDTO makeDTO(BillDTO billDTO, DetailBillDTO detailbillDTO) {
		return new TotalBillDTO(billDTO.getBillId(),billDTO.getDate(),billDTO.getTotalfee(),detailbillDTO.getWaterFee(),detailbillDTO.getHeatingFee(),detailbillDTO.getElectricityFee());
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
	
	// 고지서 값 변경 확인
	@Override
	public String checkUpdateBill(HashMap<String, String> updateCheckBillMap) {	
		String fee = updateCheckBillMap.get("fee");
		updateCheckBillMap.remove("fee");
		
		String cnt = Integer.toString(billMapper.checkUpdateBill(updateCheckBillMap));
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
