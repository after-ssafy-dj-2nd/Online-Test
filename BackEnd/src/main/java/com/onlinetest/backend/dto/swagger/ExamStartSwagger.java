package com.onlinetest.backend.dto.swagger;

import java.util.List;
import com.onlinetest.backend.dto.Question;

public class ExamStartSwagger {
	private String status;
	private List<Question> questions;

    public ExamStartSwagger() {
        super();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
