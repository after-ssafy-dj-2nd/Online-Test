package com.onlinetest.backend.dto.swagger;

public class StartTest {
	private int student_id;
	private int set_id;
	
	
	public StartTest() {
		super();
	}


	public StartTest(int student_id, int set_id) {
		super();
		this.student_id = student_id;
		this.set_id = set_id;
	}


	public int getStudent_id() {
		return student_id;
	}


	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}


	public int getSet_id() {
		return set_id;
	}


	public void setSet_id(int set_id) {
		this.set_id = set_id;
	}

	
}
