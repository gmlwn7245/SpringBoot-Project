package com.energysolution.repository;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.energysolution.domain.UserVO;

@Repository
public class UserDAO implements UserDAOInterface {

	UserVO user;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String namespace = "com.energysolution.mappers.UserMapper";
	

	@Override
	public void insertUser(UserVO user) {
		sqlSession.insert(namespace+".insertUser", user);
	}

	@Override
	public void updateUser(String originPW, String newPW) {
		
	}

	@Override
	public UserVO selectUser() {
		return null;
	}

	@Override
	public void deleteUser() {
		
	}
	


}
