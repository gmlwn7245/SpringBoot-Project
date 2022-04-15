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
	
	public void updateBill(HashMap<String, String> deleteBillMap);
	
	public void deleteBill(HashMap<String, String> deleteBillMap);
	public void deleteDetailBill(HashMap<String, String> deleteDetailBillMap);
	public void deletePayment(HashMap<String, String> deletePaymentMap);
}
