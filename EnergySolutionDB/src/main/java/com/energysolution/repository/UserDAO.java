package com.energysolution.repository;

import org.mybatis.spring.SqlSessionTemplate;

import com.energysolution.domain.UserEntity;

public class UserDAO implements UserDAOInterface {
	UserEntity user;
	private final SqlSessionTemplate sqlSession = null;
	
	public UserEntity test() {
		return sqlSession.selectOne(null);
	}
	
	@Override
	public UserEntity insert(UserEntity user)  {
		this.user = user;
		
		return this.user;
	}

	@Override
	public UserEntity update(String originPW, String newPW) {
		return user;
	}

	@Override
	public UserEntity select() {
		return user;
	}

	@Override
	public UserEntity delete() {
		return user;
	}

}
