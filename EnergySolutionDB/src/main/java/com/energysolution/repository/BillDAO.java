package com.energysolution.repository;

import org.mybatis.spring.SqlSessionTemplate;

import com.energysolution.domain.BillVO;

public class BillDAO implements BillDAOInterface {
	BillVO bill;
	private final SqlSessionTemplate sqlSession = null;
	
	public BillVO test() {
		return sqlSession.selectOne(null);
	}
	
	@Override
	public BillVO insert(BillVO bill)  {
		this.bill = bill;
		
		return this.bill;
	}
	@Override
	public BillVO select() {
		return bill;
	}

}
