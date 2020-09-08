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

import com.onlinetest.backend.dto.swagger.Problem;
import com.onlinetest.backend.dto.swagger.Set;
import com.onlinetest.backend.dto.swagger.ProblemSet;
import com.onlinetest.backend.dto.swagger.StartTest;
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
            @ApiResponse(code = 200, message = "Successful", response = ProblemSet.class)})
	@ApiImplicitParam(name="setNum", value="시험번호")
	@ApiOperation(value = "시험 응시 시작")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<ProblemSet> test(@RequestParam int setNum) throws Exception {
		logger.info("1-------------test-----------------------------" + new Date());
		
		//유저 id 가져오기(헤더 토큰 만들면 수정)
		int student_id = 1;
		
		testservice.setStartTest(new StartTest(student_id, setNum));
		
		Set set = testservice.getProblemSet(setNum);
		List<Problem> problems = testservice.getProblems(setNum);
		
		ProblemSet start = new ProblemSet(set, problems);
		
		
		return new ResponseEntity<ProblemSet>(start, HttpStatus.OK);
	}

}
