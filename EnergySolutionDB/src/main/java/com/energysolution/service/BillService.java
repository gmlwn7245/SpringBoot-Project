package com.energysolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.energysolution.domain.BillVO;
import com.energysolution.mapper.EnergySolutionMapperInterface;

@Service
public class BillService implements BillServiceInterface{

	private final EnergySolutionMapperInterface ESMapper;
	
	@Autowired
	public BillService(EnergySolutionMapperInterface ESMapper) {
		this.ESMapper = ESMapper;
	}
	
	@Override
	public BillVO getBill() throws Exception {
		return ESMapper.getBill();
	}

}
