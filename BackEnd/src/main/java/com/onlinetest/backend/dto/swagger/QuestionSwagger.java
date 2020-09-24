package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Question;

public class QuestionSwagger {
    private Question question;

    public QuestionSwagger() {
        super();
    }

    public QuestionSwagger(Question question) {
        super();
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
