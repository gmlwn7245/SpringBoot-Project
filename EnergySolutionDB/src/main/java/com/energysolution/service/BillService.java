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
import com.energysolution.mapper.BillMapper;

@Service
public class BillService implements BillServiceInterface{

	@Autowired
	private BillMapper billMapper;
	
	//고지서 등록
	@Override
	public void insertBill(BillDTO billDTO) {
		billMapper.insertBill(billDTO);
	}
	
	@Override
	public void insertDetailBill(DetailBillDTO detailbillDTO) {
		billMapper.insertDetailBill(detailbillDTO);
	}
	
	@Override
	public void insertPayment(PaymentDTO paymentDTO) {
		billMapper.insertPayment(paymentDTO);
	}

	//고지서 내용 출력
	@Override
	public List<BillDTO> getBill(String UserId, int term) {
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		
		int rest = nowMonth - term;
		
		//return값.. 수정 필요
		List<BillDTO> arrDTO = new ArrayList<BillDTO>();
		
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
				
				print_bill(billDTO, detailbillDTO);
				getBillMap.remove("date");
			}
		}
		
		return null;
	}

	@Override
	// 고지서 수정
	public void updateBill(String UserId, String Date, String Field, int fee) {
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
	}
	
	// 고지서 삭제
	@Override
	public void deleteBill(String UserId, String Date) {
		HashMap<String, String> deleteBillMap = new HashMap<String, String>();
		deleteBillMap.put("UserId", UserId);
		deleteBillMap.put("date", Date);
		System.out.println("success");
		billMapper.deletePayment(deleteBillMap);
		billMapper.deleteDetailBill(deleteBillMap);
		billMapper.deleteBill(deleteBillMap);
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
}
