package com.onlinetest.backend.dto;

public class Answer {
	private int exam_student_id;
	private int question_id;
	private String answer;
	private boolean correct;
	
	
	public Answer() {
		super();
	}


	public Answer(int exam_student_id, int question_id, String answer, boolean correct) {
		super();
		this.exam_student_id = exam_student_id;
		this.question_id = question_id;
		this.answer = answer;
		this.correct = correct;
	}


	public int getExam_student_id() {
		return exam_student_id;
	}


	public void setExam_student_id(int exam_student_id) {
		this.exam_student_id = exam_student_id;
	}


	public int getQuestion_id() {
		return question_id;
	}


	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public boolean isCorrect() {
		return correct;
	}


	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


}
