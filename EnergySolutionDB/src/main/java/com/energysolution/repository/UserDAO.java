package com.energysolution.repository;

import org.mybatis.spring.SqlSessionTemplate;

import com.energysolution.domain.UserVO;

public class UserDAO implements UserDAOInterface {
	UserVO user;
	private final SqlSessionTemplate sqlSession = null;
	
	public UserVO test() {
		return sqlSession.selectOne(null);
	}
	
	@Override
	public UserVO insert(UserVO user)  {
		this.user = user;
		
		return this.user;
	}

	@Override
	public UserVO update(String originPW, String newPW) {
		return user;
	}

	@Override
	public UserVO select() {
		return user;
	}

	@Override
	public UserVO delete() {
		return user;
	}

}
