package com.onlinetest.backend.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemDaoImpl {

	String ns = "problem.";
	
	@Autowired
	private SqlSession sqlSeesion;

	public void setName(String name) {
		sqlSeesion.insert(ns+"setName", name);
	}
}
