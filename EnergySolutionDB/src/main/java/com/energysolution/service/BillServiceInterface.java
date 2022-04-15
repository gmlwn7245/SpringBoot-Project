package com.energysolution.service;

import java.util.List;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;

public interface BillServiceInterface {
	//고지서 저장
	public void insertBill(BillDTO billDTO);
	public void insertPayment(PaymentDTO paymentDTO);
	public void insertDetailBill(DetailBillDTO detailbillDTO);
	
	// 종합고지서 조회 (UserId와 Date)
	public List<BillDTO> getBill(String UserId, int term);
	
	// 고지서 수정
	public void updateBill(String UserId, String Date, String Field, int fee);
	
	// 고지서 삭제
	public void deleteBill(String UserId, String Date);
	
}
