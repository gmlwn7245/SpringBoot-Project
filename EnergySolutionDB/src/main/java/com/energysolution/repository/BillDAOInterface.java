package com.energysolution.repository;

import com.energysolution.domain.BillVO;

public interface BillDAOInterface {
	//고지서 저장
	public BillVO insert(BillVO bill);
		
	//고지서 검색
	public BillVO select();
}
