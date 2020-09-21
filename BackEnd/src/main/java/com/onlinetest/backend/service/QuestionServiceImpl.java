package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinetest.backend.dao.QuestionDaoImpl;
import com.onlinetest.backend.dto.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionDaoImpl questionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestions(int user_id){
        return questionDao.getQuestions(user_id);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestion(Map<String, Integer> paramMap){
        return questionDao.getQuestion(paramMap);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(int id){
        return questionDao.getQuestionById(id);
    }

    @Override
    @Transactional
    public void createQuestion(QuestionExample questionExample){
        questionDao.createQuestion(questionExample);
    }

    @Override
    @Transactional
    public void updateQuestion(QuestionExample questionExample){
        questionDao.updateQuestion(questionExample);
    }

    @Override
    @Transactional
    public void deleteQuestion(int id){
        questionDao.deleteQuestion(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Example> getExamples(int question_id){
        return questionDao.getExamples(question_id);
    }

    @Override
    @Transactional
    public void createExample(Example example){
        questionDao.createExample(example);
    }

    @Override
    @Transactional
    public void deleteExamples(int question_id){
        questionDao.deleteExamples(question_id);
    }
}