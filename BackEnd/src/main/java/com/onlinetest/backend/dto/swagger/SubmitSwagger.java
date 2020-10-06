package com.onlinetest.backend.dto.swagger;

import java.util.List;

import com.onlinetest.backend.dto.Submit;

public class SubmitSwagger {
	private int exam_id;
	private List<Submit> submit;
	
	
	public SubmitSwagger() {
		super();
	}


	public SubmitSwagger(int exam_id, List<Submit> submit) {
		super();
		this.exam_id = exam_id;
		this.submit = submit;
	}


	public int getExam_id() {
		return exam_id;
	}


	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}


	public List<Submit> getSubmit() {
		return submit;
	}


	public void setSubmit(List<Submit> submit) {
		this.submit = submit;
	}


}
