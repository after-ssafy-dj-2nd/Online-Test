package com.onlinetest.backend.service;

import java.util.List;

import com.onlinetest.backend.dto.swagger.Problem;
import com.onlinetest.backend.dto.swagger.Set;
import com.onlinetest.backend.dto.swagger.StartTest;

public interface ITestService {
	
	void setStartTest(StartTest student);

	Set getProblemSet(int setNum);

	List<Problem> getProblems(int setNum);


	
}	
