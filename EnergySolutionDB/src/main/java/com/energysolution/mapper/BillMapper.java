package com.energysolution.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import com.energysolution.dto.BillDTO;
import com.energysolution.dto.DetailBillDTO;
import com.energysolution.dto.PaymentDTO;

@Mapper
public interface BillMapper {	
	public void insertBill(BillDTO billDTO);
	public void insertDetailBill(DetailBillDTO detailbillDTO);
	public void insertPayment(PaymentDTO paymentDTO);
	
	public BillDTO getBill(HashMap<String, String> getBillMap);
	public DetailBillDTO getDetailBill(HashMap<String, String> getDetailBillMap);
	
	public int getBillId(HashMap<String, String> getBillIdMap);
	
	public void updateBill(HashMap<String, String> deleteBillMap);
	
	public void deleteBill(int BillId);
	public void deleteDetailBill(int BillId);
	public void deletePayment(int BillId);
	
	public int checkBill(int billId);
	public int checkUpdateBill(HashMap<String, String> updateCheckMap);
	
	public int checkDeleteBill(int billId);
	public int checkDeleteDetailBill(int billId);
	public int checkDeletePayment(int billId);
}
