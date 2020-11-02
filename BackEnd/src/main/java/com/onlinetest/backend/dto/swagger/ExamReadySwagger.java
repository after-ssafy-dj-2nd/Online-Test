package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Exam;

public class ExamReadySwagger {
	private String status;
    private Exam exam;

    public ExamReadySwagger() {
        super();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

    
}
