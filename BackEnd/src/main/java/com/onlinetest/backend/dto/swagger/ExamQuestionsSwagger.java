package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.QuestionExam;

import java.time.LocalDateTime;
import java.util.List;

public class ExamQuestionsSwagger extends Exam {
    private List<QuestionExam> questions;

    public ExamQuestionsSwagger() {
        super();
    }

    public ExamQuestionsSwagger(String name, LocalDateTime starttime, LocalDateTime endtime, int teacher_id) {
        super(name, starttime, endtime, teacher_id);
    }

    public ExamQuestionsSwagger(int id, String name, LocalDateTime starttime, LocalDateTime endtime, int teacher_id) {
        super(id, name, starttime, endtime, teacher_id);
    }

    public ExamQuestionsSwagger(Exam exam) {
        super(exam.getId(), exam.getName(), exam.getStarttime(), exam.getEndtime(), exam.getTeacher_id());
    }


    public ExamQuestionsSwagger(Exam exam, List<QuestionExam> questions) {
        super(exam.getId(), exam.getName(), exam.getStarttime(), exam.getEndtime(), exam.getTeacher_id());
        this.questions = questions;
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
