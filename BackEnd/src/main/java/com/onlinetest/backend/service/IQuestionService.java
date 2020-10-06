package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.swagger.QuestionList;

import java.util.List;
import java.util.Map;

public interface IQuestionService {
    QuestionList getQuestions(int user_id);
    Question getQuestion(Map<String, Integer> paramMap);
    Question getQuestionById(int id);

    void createQuestion(Question question);
    void updateQuestion(Question question);
    void deleteQuestion(int id);

    List<Example> getExamples(int question_id);
    void createExample(Example example);
    void deleteExamples(int question_id);
}
