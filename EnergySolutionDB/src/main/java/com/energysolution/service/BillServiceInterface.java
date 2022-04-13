package com.energysolution.service;

import java.util.ArrayList;

import com.energysolution.domain.BillDTO;
import com.energysolution.domain.DetailBillDTO;
import com.energysolution.domain.PaymentDTO;

public interface BillServiceInterface {
	//고지서 저장
	public void insertBill(BillDTO billDTO);
	public void insertPayment(PaymentDTO paymentDTO);
	public void insertDetailBill(DetailBillDTO detailbillDTO);
	
	// 종합고지서 조회 (UserId와 Date)
	public ArrayList<BillDTO> getBill(String UserId, int term);
	
	// 고지서 삭제
	public void deleteBill(int BillId);
	
}
