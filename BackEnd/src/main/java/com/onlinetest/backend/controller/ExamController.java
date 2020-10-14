package com.onlinetest.backend.controller;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.swagger.*;
import com.onlinetest.backend.service.IExamService;
import com.onlinetest.backend.service.IJwtService;
import com.onlinetest.backend.service.IQuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ExamController {
    @Autowired
    private IExamService examService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IJwtService jwtservice;

    @ApiOperation(value = "모든 시험 보기", response = ExamListSwagger.class)
    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExamListSwagger> getQuestions() {
        int user_id = jwtservice.getId();
        ExamListSwagger examList = new ExamListSwagger(examService.getExams(user_id));
        return new ResponseEntity<ExamListSwagger>(examList, HttpStatus.OK);
    }

    @ApiOperation(value = "시험 상세 보기", response = ExamSwagger.class)
    @RequestMapping(value = "/exam", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExamSwagger> getExam(@RequestParam int id) {
        int user_id = jwtservice.getId();

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("user_id", user_id);

        ExamSwagger exam = examService.getExam(paramMap);

        if (exam == null) {
            return new ResponseEntity<ExamSwagger>(exam, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ExamSwagger>(exam, HttpStatus.OK);
        }
    }

    @Transactional
    @ApiOperation(value = "시험 생성", response = ExamSwagger.class)
    @RequestMapping(value = "/exam", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ExamSwagger> createExam(@RequestBody ExamQuestionsSwagger examQuestion){
        int user_id = jwtservice.getId();
        // 검증
        if (examQuestion.getTeacher_id() != user_id){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.UNAUTHORIZED);
        }
        else if (examQuestion.getName() == null || examQuestion.getQuestions() == null
                || examQuestion.getStarttime() == null || examQuestion.getEndtime() == null){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
        }
        else if (examQuestion.getStarttime().isAfter(examQuestion.getEndtime())){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
        }
        List<QuestionExam> questionExamTable = examQuestion.getQuestions();
        for (QuestionExam questionExam: questionExamTable) {
            if (questionExam.getScore() == 0){
                return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
            }
            else{
                Question question = questionService.getQuestionById(questionExam.getQuestion_id());
                if (question == null){
                    return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
                }
                else if (question.getWriter_id() != user_id){
                    return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.UNAUTHORIZED);
                }
            }
        }
        // 실행
        examService.createExam(examQuestion);
        int exam_id = examQuestion.getId();
        for (QuestionExam questionExam: questionExamTable) {
            questionExam.setExam_id(exam_id);
            examService.createQuestionExam(questionExam);
        }
        ExamSwagger exam = examService.getExamById(exam_id);
        return new ResponseEntity<ExamSwagger>(exam, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "시험 수정", response = QuestionSwagger.class)
    @RequestMapping(value = "/exam", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ExamSwagger> updateExam(@RequestBody ExamQuestionsSwagger examQuestion) {
        int user_id = jwtservice.getId();
        // 검증
        if (examQuestion.getTeacher_id() != user_id){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.UNAUTHORIZED);
        }
        else if (examQuestion.getName() == null || examQuestion.getQuestions() == null
                || examQuestion.getStarttime() == null || examQuestion.getEndtime() == null){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
        }
        else if (examQuestion.getStarttime().isAfter(examQuestion.getEndtime())){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
        }
        int exam_id = examQuestion.getId();
        if (examService.getExamById(exam_id) == null){
            return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
        }

        List<QuestionExam> questionExamTable = examQuestion.getQuestions();
        for (QuestionExam questionExam: questionExamTable) {
            if (questionExam.getScore() == 0){
                return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
            }
            else{
                Question question = questionService.getQuestionById(questionExam.getQuestion_id());
                if (question == null){
                    return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.BAD_REQUEST);
                }
                else if (question.getWriter_id() != user_id){
                    return new ResponseEntity<ExamSwagger>(new ExamSwagger(), HttpStatus.UNAUTHORIZED);
                }
            }
        }
        // 실행
        examService.deleteQuestionExam(exam_id);
        examService.updateExam(examQuestion);
        for (QuestionExam questionExam: questionExamTable) {
            questionExam.setExam_id(exam_id);
            examService.createQuestionExam(questionExam);
        }
        ExamSwagger exam = examService.getExamById(exam_id);
        return new ResponseEntity<ExamSwagger>(exam, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "시험 삭제", response = Boolean.class)
    @RequestMapping(value = "/exam", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteExam(@RequestParam int id) {
        int user_id = jwtservice.getId();
        ExamSwagger exam = examService.getExamById(id);

        if (exam == null){
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        }
        else if (exam.getTeacher_id() != user_id){
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }

        examService.deleteQuestionExam(id);
        examService.deleteExam(id);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}