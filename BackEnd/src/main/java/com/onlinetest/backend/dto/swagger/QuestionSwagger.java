package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExample;

public class QuestionSwagger {
    private QuestionExample questionExample;

    public QuestionSwagger() {
        super();
    }

    public QuestionSwagger(QuestionExample questionExample) {
        super();
        this.questionExample = questionExample;
    }

    public QuestionExample getQuestionExample() {
        return questionExample;
    }

    public void setQuestionExample(QuestionExample questionExample) {
        this.questionExample = questionExample;
    }
}
