package com.energysolution.repository;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.energysolution.domain.UserVO;

@Repository("userDAO")
public class UserDAO implements UserDAOInterface {
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String namespace = "com.energysolution.mapper.UserMapper";
	

	@Override
	public void insertUser(UserVO uservo) {
		sqlSession.insert(namespace+".insertUser", uservo);
	}

	@Override
	public void updateUser(UserVO uservo) {
		sqlSession.update(namespace+".updateUser",uservo);
	}

	@Override
	public UserVO selectUser(String UserId) {
		return sqlSession.selectOne(namespace+".selectUser", UserId);
	}

	@Override
	public void deleteUser() {
		
	}
}
