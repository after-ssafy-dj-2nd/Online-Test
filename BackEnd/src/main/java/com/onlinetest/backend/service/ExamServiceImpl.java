package com.onlinetest.backend.service;


import com.onlinetest.backend.dao.ExamDaoImpl;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import com.onlinetest.backend.dto.swagger.ExamSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements IExamService{

    @Autowired
    private ExamDaoImpl examDao;

    @Override
    @Transactional(readOnly = true)
    public List<ExamSwagger> getExams(int user_id){
        return examDao.getExams(user_id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamSwagger getExam(Map<String, Integer> paramMap){
        return examDao.getExam(paramMap);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamSwagger getExamById(int exam_id){
        return examDao.getExamById(exam_id);
    }

    @Override
    @Transactional
    public void createExam(ExamQuestionsSwagger examQuestion){
        examDao.createExam(examQuestion);
    }

    @Override
    @Transactional
    public void updateExam(ExamQuestionsSwagger examQuestion){
        examDao.updateExam(examQuestion);
    }

    @Override
    @Transactional
    public void deleteExam(int exam_id){
        examDao.deleteExam(exam_id);
    }


    @Override
    @Transactional
    public void createQuestionExam(QuestionExam questionExam){
        examDao.createQuestionExam(questionExam);
    }

    @Override
    @Transactional
    public void deleteQuestionExam(int exam_id){
        examDao.deleteQuestionExam(exam_id);
    }
}
