package com.onlinetest.backend.dto.swagger;

import java.util.List;

public class ExamListSwagger {
    private List<ExamSwagger> exams;

    public ExamListSwagger() {
        super();
    }

    public ExamListSwagger(List<ExamSwagger> exams) {
        super();
        this.exams = exams;
    }

    public List<ExamSwagger> getExams() {
        return exams;
    }

    public void setExams(List<ExamSwagger> exams) {
        this.exams = exams;
    }
}
