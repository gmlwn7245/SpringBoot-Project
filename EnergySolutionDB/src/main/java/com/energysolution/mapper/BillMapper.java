package com.energysolution.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import com.energysolution.domain.BillVO;

@Mapper
public interface BillMapper {	
	public void insertBill(BillVO billvo);
	public BillVO selectBill(HashMap<String, String> updateBillMap);
	public void deleteBill(int UserId);
}
