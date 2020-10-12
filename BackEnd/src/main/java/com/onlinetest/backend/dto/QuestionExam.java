package com.onlinetest.backend.dto;

public class QuestionExam {
    private int exam_id;
    private int question_id;
    private int score;

    public QuestionExam() {
        super();
    }

    public QuestionExam(int exam_id, int question_id, int score) {
        this.exam_id = exam_id;
        this.question_id = question_id;
        this.score = score;
    }

    public QuestionExam(int question_id, int score) {
        this.question_id = question_id;
        this.score = score;
    }

    public QuestionExam(int question_id) {
        this.question_id = question_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
