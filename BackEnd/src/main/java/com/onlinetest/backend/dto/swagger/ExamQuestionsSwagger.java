package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExam;

import java.util.List;

public class ExamQuestionsSwagger extends Exam {
    private List<QuestionExam> questions;

    public ExamQuestionsSwagger() {
        super();
    }

    public ExamQuestionsSwagger(Exam exam) {
        super(exam.getId(), exam.getName(), exam.getStarttime(), exam.getEndtime(), exam.getTeacher_id());
    }

    public ExamQuestionsSwagger(List<QuestionExam> questions) {
        this.questions = questions;
    }

    public List<QuestionExam> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionExam> questions) {
        this.questions = questions;
    }
}
