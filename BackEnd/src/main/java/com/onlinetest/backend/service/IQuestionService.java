package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExample;

import java.util.List;
import java.util.Map;

public interface IQuestionService {
    List<Question> getQuestions(int user_id);
    Question getQuestion(Map<String, Integer> paramMap);
    Question getQuestionById(int id);
    void createQuestion(QuestionExample questionExample);
    void updateQuestion(QuestionExample questionExample);
    void deleteQuestion(int id);

    List<Example> getExamples(int question_id);
    void createExample(Example example);
    void deleteExamples(int question_id);
}
