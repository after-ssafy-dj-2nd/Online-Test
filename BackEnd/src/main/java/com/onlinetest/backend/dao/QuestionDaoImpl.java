package com.onlinetest.backend.dao;


import com.onlinetest.backend.dto.Question;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDaoImpl {

    String ns = "question.";

    @Autowired
    private SqlSession sqlSeesion;

    public List<Question> getQuestions(){
        return sqlSeesion.selectList(ns + "getQuestions");
    }

    public Question getQuetion(int id){
        return sqlSeesion.selectOne(ns + "getQuestion", id);
    }

    public void createQuestion(Question question){
        sqlSeesion.insert(ns + "createQuestion", question);
    }

    public void updateQuestion(Question question){
        sqlSeesion.update(ns + "updateQuestion", question);
    }

    public void deleteQuestion(int id){
        sqlSeesion.delete(ns + "deleteQuestion", id);
    }
}
