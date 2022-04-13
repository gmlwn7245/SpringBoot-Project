package com.energysolution.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.BillDTO;
import com.energysolution.domain.DetailBillDTO;
import com.energysolution.domain.PaymentDTO;
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
		billMapper.insertDetailBill(null);
	}
	
	@Override
	public void insertPayment(PaymentDTO paymentDTO) {
		billMapper.insertPayment(paymentDTO);
	}

	//고지서 내용 출력
	@Override
	public ArrayList<BillDTO> getBill(String UserId, int term) {
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		
		int rest = nowMonth - term;
		
		//return값 수정 필요
		ArrayList<BillDTO> arrDTO = new ArrayList<BillDTO>();
		
		//UserId값과 date값 넘겨줌
		HashMap<String, String> updateBillMap= new HashMap<String,String>();
		updateBillMap.put("UserId", UserId);
		
		if(rest <=0) {
			for(int i=12+rest; i<=12; i++) {
				int year = nowYear-1;
				int month = i;
				String date = year+"-"+month;
				updateBillMap.put("date", date);
				BillDTO billDTO = billMapper.getBill(updateBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(updateBillMap);
				print_bill(billDTO, detailbillDTO);
				updateBillMap.remove("date");
			}
			for(int i=1; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				updateBillMap.put("date", date);
				BillDTO billDTO = billMapper.getBill(updateBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(updateBillMap);
				print_bill(billDTO, detailbillDTO);
				updateBillMap.remove("date");
			}
		}else {
			for(int i=rest; i<nowMonth; i++) {
				int year = nowYear;
				int month = i;
				String date = year+"-"+month;
				updateBillMap.put("date", date);
				BillDTO billDTO = billMapper.getBill(updateBillMap);
				DetailBillDTO detailbillDTO = billMapper.getDetailBill(updateBillMap);
				print_bill(billDTO, detailbillDTO);
				updateBillMap.remove("date");
			}
		}
		
		return null;
	}

	//고지서 삭제
	@Override
	public void deleteBill(int BillId) {
		billMapper.deletePayment(BillId);
		billMapper.deleteDetailBill(BillId);
		billMapper.deleteBill(BillId);
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
