package com.onlinetest.backend.dto.swagger;

import java.util.List;


public class ProblemSet {
	private Set set;
	private List<Problem> problem;
	public ProblemSet(Set set, List<Problem> problem) {
		super();
		this.set = set;
		this.problem = problem;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public List<Problem> getProblem() {
		return problem;
	}
	public void setProblem(List<Problem> problem) {
		this.problem = problem;
	}
	
	
	
	
	
}
