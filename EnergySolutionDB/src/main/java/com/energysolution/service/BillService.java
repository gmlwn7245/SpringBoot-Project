package com.energysolution.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.domain.BillVO;
import com.energysolution.mapper.BillMapper;

@Service
public class BillService implements BillServiceInterface{

	@Autowired
	private BillMapper billMapper;
	
	@Override
	public void insertBill(BillVO billvo) {
		billMapper.insertBill(billvo);
	}

	@Override
	public BillVO selectBill(HashMap<String, String> updateMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillVO selectDetailBill(HashMap<String, String> updateMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBill(int BillId) {
		billMapper.deleteBill(BillId);
	}

}
