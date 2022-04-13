package com.energysolution.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import com.energysolution.domain.BillDTO;
import com.energysolution.domain.DetailBillDTO;
import com.energysolution.domain.PaymentDTO;

@Mapper
public interface BillMapper {	
	public void insertBill(BillDTO billDTO);
	public void insertDetailBill(DetailBillDTO detailbillDTO);
	public void insertPayment(PaymentDTO paymentDTO);
	
	public BillDTO getBill(HashMap<String, String> updateBillMap);
	
	public void deleteBill(int BillId);
	public void deleteDetailBill(int BillId);
	public void deletePayment(int BillId);
}
