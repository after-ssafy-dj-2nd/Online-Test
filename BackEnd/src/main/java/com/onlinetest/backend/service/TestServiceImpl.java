package com.onlinetest.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinetest.backend.dao.TestDaoImpl;
import com.onlinetest.backend.dto.swagger.Problem;
import com.onlinetest.backend.dto.swagger.Set;
import com.onlinetest.backend.dto.swagger.StartTest;

@Service
public class TestServiceImpl implements ITestService{
	
	@Autowired
	private TestDaoImpl testdao;

	@Override
	@Transactional
	public void setStartTest(StartTest student) {
		testdao.setStartTest(student);
	}

	@Override
	@Transactional(readOnly=true)
	public Set getProblemSet(int setNum) {
		return testdao.getProblemSet(setNum);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Problem> getProblems(int setNum) {
		return testdao.getProblems(setNum);
	}

}
