package com.onlinetest.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinetest.backend.dao.ProblemDaoImpl;
import com.onlinetest.backend.dto.Problem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProblemServiceImpl implements IProblemService{
	
	@Autowired
	private ProblemDaoImpl problemdao;

	@Override
	public void agency(String name) {
		problemdao.setName(name);
	}

	@Override
	public List<Problem> getProblems(){ return problemdao.getProblems();}

	@Override
	public Problem getProblem(int id){ return problemdao.getProblem(id);}

	@Override
	public void addProblem(Problem problem) {
		problemdao.addProblem(problem);
	}

	@Override
	public void updateProblem(Problem problem) { problemdao.updateProblem(problem); }

	@Override
	public void deleteProblem(int id){ problemdao.deleteProblem(id); }

}
