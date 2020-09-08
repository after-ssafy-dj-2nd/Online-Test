package com.onlinetest.backend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinetest.backend.dto.swagger.Problem;
import com.onlinetest.backend.dto.swagger.Set;
import com.onlinetest.backend.dto.swagger.StartTest;

@Repository
public class TestDaoImpl {

	String ns = "test.";
	
	@Autowired
	private SqlSession sqlSeesion;

	public void setStartTest(StartTest student) {
		sqlSeesion.insert(ns+"setStartTest", student);
	}

	public Set getProblemSet(int setNum) {
		return sqlSeesion.selectOne(ns+"getProblemSet", setNum);
	}

	public List<Problem> getProblems(int setNum) {
		return sqlSeesion.selectList(ns+"getProblems", setNum);
	}

	

}
