package com.energysolution.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;

@Mapper
public interface BillMapper {	
	//등록
	public void insertBill(BillDTO billDTO);
	public void insertDetailBill(DetailBillDTO detailbillDTO);
	public void insertPayment(PaymentDTO paymentDTO);
	
	//조회
	public BillDTO getBill(int BillId);
	public DetailBillDTO getDetailBill(int BillId);
	
	//마지막 BillId 가져옴
	public int getLastBillId();
	
	//ID로 조회
	public int getBillId(HashMap<String, String> getBillIdMap);
	
	//변경
	public void updateBillField(HashMap<String, String> updateBillFieldMap);
	public void updateBill(HashMap<String, Integer> updateBillMap);
	public void updateDetailBill(HashMap<String, Integer> updateDetailBillMap);
	
	//삭제
	public void deleteBill(int BillId);
	public void deleteDetailBill(int BillId);
	public void deletePayment(int BillId);
	
	//변경확인
	public int checkUpdateBill(int billId);
	public int checkUpdateDetailBill(int billId);
	public int checkUpdateBillField(HashMap<String, String> updateCheckMap);
	
	//삭제확인
	public int checkDeleteBill(int billId);
	public int checkDeleteDetailBill(int billId);
	public int checkDeletePayment(int billId);
	
	//고지서 확인
	public int checkBill(int BillId);
}
