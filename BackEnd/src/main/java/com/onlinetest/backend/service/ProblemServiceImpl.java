package com.onlinetest.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinetest.backend.dao.ProblemDaoImpl;

@Service
public class ProblemServiceImpl implements IProblemService{
	
	@Autowired
	private ProblemDaoImpl problemdao;

	@Override
	public void agency(String name) {
		problemdao.setName(name);
		
	}

}
