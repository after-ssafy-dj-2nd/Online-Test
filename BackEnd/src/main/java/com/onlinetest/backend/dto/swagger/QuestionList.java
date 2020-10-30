package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Question;

import java.util.List;

public class QuestionList {
	private List<Question> questions;
	
	public QuestionList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuestionList(List<Question> questions) {
		super();
		this.questions = questions;
	}

	public List<Question> getQuestion() {
		return questions;
	}
	
	public void setQuestion(List<Question> questions) {
		this.questions = questions;
	}

}
