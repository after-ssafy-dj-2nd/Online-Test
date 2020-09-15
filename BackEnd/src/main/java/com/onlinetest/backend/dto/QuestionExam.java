package com.onlinetest.backend.dto;

import java.util.List;

public class QuestionExam {
	private List<Question> question;
	
	public QuestionExam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuestionExam(List<Question> question) {
		super();
		this.question = question;
	}

	public List<Question> getQuestion() {
		return question;
	}
	
	public void setQuestion(List<Question> question) {
		this.question = question;
	}

}
