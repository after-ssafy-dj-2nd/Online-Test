package com.onlinetest.backend.dto;

import java.util.List;

public class Submit {
	private int question_id;
	private List<String> answer;
	private boolean type;
	private int score;
	
	
	public Submit() {
		super();
	}


	public Submit(int question_id, List<String> answer) {
		super();
		this.question_id = question_id;
		this.answer = answer;
	}
	

	public Submit(int question_id, List<String> answer, boolean type, int score) {
		super();
		this.question_id = question_id;
		this.answer = answer;
		this.type = type;
		this.score = score;
	}


	public int getQuestion_id() {
		return question_id;
	}


	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}


	public List<String> getAnswer() {
		return answer;
	}


	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}
	

	public boolean getType() {
		return type;
	}


	public void setType(boolean type) {
		this.type = type;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


}
