package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import com.onlinetest.backend.dto.swagger.ExamSwagger;

import java.util.List;
import java.util.Map;

public interface IExamService {

    List<ExamSwagger> getExams(int user_id);
    ExamSwagger getExam(Map<String, Integer> paramMap);
    ExamSwagger getExamById(int exam_id);
    void createExam(ExamQuestionsSwagger examQuestion);
    void updateExam(ExamQuestionsSwagger examQuestion);
    void deleteExam(int exam_id);


    void createQuestionExam(QuestionExam questionExam);
    void deleteQuestionExam(int exam_id);
}
