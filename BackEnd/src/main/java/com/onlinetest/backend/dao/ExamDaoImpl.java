package com.onlinetest.backend.dao;

import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import com.onlinetest.backend.dto.swagger.ExamSwagger;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExamDaoImpl {

    String ns = "exam.";

    @Autowired
    private SqlSession sqlSeesion;

    public List<ExamSwagger> getExams(int user_id){
        return sqlSeesion.selectList(ns + "getExams", user_id);
    }

    public ExamSwagger getExam(Map<String, Integer> paramMap){
        return sqlSeesion.selectOne(ns + "getExam", paramMap);
    }

    public ExamSwagger getExamById(int exam_id){
        return sqlSeesion.selectOne(ns + "getExamById", exam_id);
    }

    public void createExam(ExamQuestionsSwagger examQuestion){
        sqlSeesion.insert(ns + "createExam", examQuestion);
    }

    public void updateExam(ExamQuestionsSwagger examQuestion){
        sqlSeesion.insert(ns + "updateExam", examQuestion);
    }

    public void deleteExam(int exam_id){
        sqlSeesion.insert(ns + "deleteExam", exam_id);
    }

    public void createQuestionExam(QuestionExam questionExam){
        sqlSeesion.insert(ns+"createQuestionExam", questionExam);
    }

    public void deleteQuestionExam(int exam_id){
        sqlSeesion.delete(ns+"deleteQuestionExam", exam_id);
    }
}
