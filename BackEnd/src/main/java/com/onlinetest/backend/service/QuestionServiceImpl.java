package com.onlinetest.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinetest.backend.dao.QuestionDaoImpl;
import com.onlinetest.backend.dto.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionDaoImpl questionDao;

    @Override
    public List<Question> getQuestions(){
        return questionDao.getQuestions();
    }

    @Override
    public Question getQuestion(int id){
        return questionDao.getQuetion(id);
    }

    @Override
    public void createQuestion(Question question) {
        questionDao.createQuestion(question);
    }

    @Override
    public void updateQuestion(Question question){
        questionDao.updateQuestion(question);
    }

    @Override
    public void deleteQuestion(int id){
        questionDao.deleteQuestion(id);
    }
}
