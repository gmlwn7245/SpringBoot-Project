package com.energysolution.service;

import java.util.HashMap;

import com.energysolution.domain.BillVO;

public interface BillServiceInterface {
	//고지서 저장
	public void insertBill(BillVO billvo);
	
	// 종합고지서 조회 (UserId와 Date)
	public BillVO selectBill(HashMap<String, String> updateMap);
	// 수도요금 조회	
	public BillVO selectDetailBill(HashMap<String, String> updateMap);
	
	// 고지서 삭제
	public void deleteBill(int BillId);
	
}
