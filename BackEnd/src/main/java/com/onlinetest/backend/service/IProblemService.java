package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Problem;

import java.util.List;

public interface IProblemService {
	void agency(String name);

	// Problem CRUD
	List<Problem> getProblems();
	Problem getProblem(int id);
	void addProblem(Problem problem);
	void updateProblem(Problem problem);
	void deleteProblem(int id);

}	
