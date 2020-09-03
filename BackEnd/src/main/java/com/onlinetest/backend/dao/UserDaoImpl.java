package com.onlinetest.backend.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinetest.backend.dto.User;

@Repository
public class UserDaoImpl {

	String ns = "user.";
	
	@Autowired
	private SqlSession sqlSeesion;

	public void signUp(User user) {
		sqlSeesion.insert(ns+"signUp", user);
	}

	public int idCheck(String user_id) {
		return sqlSeesion.selectOne(ns+"idCheck", user_id);
	}

	public User login(User user) {
		return sqlSeesion.selectOne(ns+"login", user);
	}
}
