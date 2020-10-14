package com.onlinetest.backend.dao;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.swagger.QuestionList;
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

    public QuestionList getQuestions(int user_id){
        List<Question> questions = sqlSeesion.selectList(ns + "getQuestions", user_id);
        return new QuestionList(questions);
    }

    public Question getQuestion(Map<String, Integer> paramMap){
        return sqlSeesion.selectOne(ns + "getQuestion", paramMap);
    }

    public Question getQuestionById(int id){
        return sqlSeesion.selectOne(ns + "getQuestionById", id);
    }

    public void createQuestion(Question question){
        sqlSeesion.insert(ns + "createQuestion", question);
    }

    public void updateQuestion(Question question){
        sqlSeesion.update(ns + "updateQuestion", question);
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