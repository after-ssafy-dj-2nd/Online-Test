package com.onlinetest.backend.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.swagger.QuestionList;
import com.onlinetest.backend.service.ITestService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class TestController {
	
	public static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ITestService testservice;
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful", response = Exam.class)})
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 준비 - 시험 정보")
	@RequestMapping(value = "/readytest", method = RequestMethod.GET)
	public ResponseEntity<Exam> readytest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------readytest-----------------------------" + new Date());
		
		//로그인 체크
		//시험 응시 가능한 학생인지 체크
		
		Exam exam = testservice.getExam(exam_id);
		
		return new ResponseEntity<Exam>(exam, HttpStatus.OK);
	}
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful", response = QuestionList.class)})
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 응시 시작")
	@RequestMapping(value = "/starttest", method = RequestMethod.GET)
	public ResponseEntity<QuestionList> starttest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------starttest-----------------------------" + new Date());
		
		//로그인 체크
		//시험 응시 가능한 학생인지 체크
		
		//응시 학생 id(토큰 만들면 수정)
		int student_id = 2;
		
		testservice.setStartTest(new ExamStudent(student_id, exam_id));
		
		List<Question> questions = testservice.getQuestion(exam_id);
		QuestionList start = new QuestionList(questions);
		
		return new ResponseEntity<QuestionList>(start, HttpStatus.OK);
	}

}
