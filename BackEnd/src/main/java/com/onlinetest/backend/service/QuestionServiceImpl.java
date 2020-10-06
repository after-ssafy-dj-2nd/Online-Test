package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dao.QuestionDaoImpl;

import com.onlinetest.backend.dto.swagger.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionDaoImpl questionDao;

    @Override
    @Transactional(readOnly = true)
    public QuestionList getQuestions(int user_id){
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
    public void createQuestion(Question question){
        questionDao.createQuestion(question);
    }

    @Override
    @Transactional
    public void updateQuestion(Question question){
        questionDao.updateQuestion(question);
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