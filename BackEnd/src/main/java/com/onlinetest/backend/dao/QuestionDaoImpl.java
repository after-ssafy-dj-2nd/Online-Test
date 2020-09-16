package com.onlinetest.backend.dao;


import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExample;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class QuestionDaoImpl {

    String ns = "question.";

    @Autowired
    private SqlSession sqlSeesion;

    public List<Question> getQuestions(int user_id){
        return sqlSeesion.selectList(ns + "getQuestions", user_id);
    }

    public Question getQuestion(Map<String, Integer> paramMap){
        return sqlSeesion.selectOne(ns + "getQuestion", paramMap);
    }

    public Question getQuestionById(int id){
        return sqlSeesion.selectOne(ns + "getQuestionById", id);
    }

    public void createQuestion(QuestionExample questionExample){
        sqlSeesion.insert(ns + "createQuestion", questionExample);
    }

    public void updateQuestion(QuestionExample questionExample){
        sqlSeesion.update(ns + "updateQuestion", questionExample);
    }

    public void deleteQuestion(int id) {
        sqlSeesion.delete(ns + "deleteQuestion", id);
    }

    public List<Example> getExamples(int question_id){
        return sqlSeesion.selectList(ns + "getExamples", question_id);
    }

    public void createExample(Example example){
        sqlSeesion.insert(ns + "createExample", example);
    }

    public void deleteExamples(int question_id){
        sqlSeesion.delete(ns + "deleteExamples", question_id);
    }

}