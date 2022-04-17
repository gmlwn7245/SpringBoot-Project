package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;
import com.energysolution.dto.TotalBillDTO;

public interface BillServiceInterface {
	//고지서 저장 
	public String insertBill(BillDTO billDTO, DetailBillDTO detailbillDTO,PaymentDTO paymentDTO);
	
	// 종합고지서 조회 (UserId와 Date)
	public List<TotalBillDTO> getBill(String UserId, int term);
	
	// 고지서 수정
	public String updateBill(String UserId, String Date, String Field, int fee);
	
	// 고지서 삭제
	public String deleteBill(String UserId, String Date);
	
	// 고지서 유무 확인
	public String checkBill(int billId);
	
	// 고지서 변경 확인
	public String checkUpdateBill(HashMap<String, String> updateCheckBillMap);
	
	// 고지서 삭제 확인
	public String checkDeleteBill(int billId);
	
	// 고지서 ID 가져오기
	public int getBillId(String UserId, String date);
}
