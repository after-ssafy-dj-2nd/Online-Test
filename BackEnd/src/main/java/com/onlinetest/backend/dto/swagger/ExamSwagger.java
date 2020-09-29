package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.Question;

import java.util.List;

public class ExamSwagger extends Exam {
    private List<Question> questions;

    public ExamSwagger() {
        super();
    }

    public ExamSwagger(Exam exam) {
        super(exam.getId(), exam.getName(), exam.getStarttime(), exam.getEndtime(), exam.getTeacher_id());
    }

    public ExamSwagger(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
