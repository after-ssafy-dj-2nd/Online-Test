package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Question;

import java.util.List;

public interface IQuestionService {
    List<Question> getQuestions();
    Question getQuestion(int id);
    void createQuestion(Question question);
    void updateQuestion(Question question);
    void deleteQuestion(int id);
}
