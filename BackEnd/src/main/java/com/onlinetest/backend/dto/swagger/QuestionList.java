package com.onlinetest.backend.dto.swagger;

import java.util.List;
import com.onlinetest.backend.dto.Question;

public class QuestionList {
	private List<Question> question;
	
	public QuestionList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuestionList(List<Question> question) {
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
