package com.onlinetest.backend.controller;

import java.util.*;

import com.onlinetest.backend.dto.swagger.*;
import com.onlinetest.backend.service.IExamService;
import com.onlinetest.backend.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlinetest.backend.dto.Answer;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.Submit;
import com.onlinetest.backend.service.IJwtService;
import com.onlinetest.backend.service.ITestService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TestController {
	
	public static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ITestService testservice;
	
	@Autowired
    private IJwtService jwtservice;

	@Autowired
	private IUserService userService;

	@Autowired
	private IExamService examService;


	@ApiOperation(value = "시험 볼 학생 추가", response = ExamUserSwagger.class)
	@RequestMapping(value = "/test/student", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ExamUserResultSwagger> setExamUser(@RequestBody ExamUserSwagger examUserSwagger) throws Exception {
		logger.info("1-------------examUser-----------------------------" + new Date());
		int teacher_id = jwtservice.getId();
		int exam_id = examUserSwagger.getExam_id();
		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("id", exam_id);
		paramMap.put("user_id", teacher_id);
		ExamSwagger exam = examService.getExam(paramMap);
		ExamUserResultSwagger result = new ExamUserResultSwagger();
		List<String> registeredStudent = new ArrayList<>();
		List<String> notFoundedStudent = new ArrayList<>();
		if (exam == null){
			result.setStatus("해당하는 시험을 찾을 수 없습니다.");
			return new ResponseEntity<ExamUserResultSwagger>(result, HttpStatus.NOT_FOUND);
		}
		for (String studentEmail:examUserSwagger.getUserList()) {
			if (userService.signUpCheck(studentEmail)){
				int student_id = userService.getUserPk(studentEmail);
				testservice.setExamStudent(new ExamStudent(student_id, exam_id));
				registeredStudent.add(studentEmail);
			}
			else {
				notFoundedStudent.add(studentEmail);
			}
		}
		result.setStatus("학생 등록이 완료되었습니다.");
		result.setRegister(registeredStudent);
		result.setNotRegister(notFoundedStudent);
		return new ResponseEntity<ExamUserResultSwagger>(result, HttpStatus.NO_CONTENT);
	}
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful", response = Exam.class)})
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 준비 - 시험 정보")
	@RequestMapping(value = "/readytest", method = RequestMethod.GET)
	public ResponseEntity<Exam> readytest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------readytest-----------------------------" + new Date());
		
		//시험 응시 가능한 학생인지 체크
		
		Exam exam = testservice.getExam(exam_id);
		
		if(exam == null) {
			return new ResponseEntity<Exam>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Exam>(exam, HttpStatus.OK);
	}
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful / type - 객관식:true, 주관식:false / commentary, writer_id 사용안함", response = QuestionList.class)})
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 응시 시작")
	@RequestMapping(value = "/starttest", method = RequestMethod.GET)
	public ResponseEntity<QuestionList> starttest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------starttest-----------------------------" + new Date());
		
		int student_id = jwtservice.getId();
		
		if(testservice.isExamStudent(new ExamStudent(student_id, exam_id)) > 0) {
			return new ResponseEntity<QuestionList>(HttpStatus.BAD_REQUEST);
		}
		
		if(testservice.isPossible(exam_id) == 0) {
			return new ResponseEntity<QuestionList>(HttpStatus.BAD_REQUEST);
		}
		
		testservice.setStartTest(new ExamStudent(student_id, exam_id));
		
		List<Question> questions = testservice.getQuestion(exam_id);
		QuestionList start = new QuestionList(questions);
		
		return new ResponseEntity<QuestionList>(start, HttpStatus.OK);
	}
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful", response = Boolean.class)})
	@ApiOperation(value = "시험 응시 종료 및 채점")
	@RequestMapping(value = "/endtest", method = RequestMethod.POST)
	public ResponseEntity<Boolean> endTest(@RequestBody SubmitSwagger submit) throws Exception {
		logger.info("1-------------endTest-----------------------------" + new Date());
		
		int student_id = jwtservice.getId();

		ExamStudent exam_student = testservice.getExamStudent(new ExamStudent(student_id, submit.getExam_id()));
		
		if(exam_student==null || exam_student.getEndtime()!=null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		List<Submit> question_answer = testservice.getAnswer(submit.getExam_id());

		if(question_answer.size() != submit.getSubmit().size()) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		
		submit.getSubmit().sort(new Comparator<Submit>() {
			@Override
			public int compare(Submit o1, Submit o2) {
				return o1.getQuestion_id() - o2.getQuestion_id();
			}
		});
		
		int score = 0;
		
		for (int i=0; i<question_answer.size(); i++) {
			Submit question_s = question_answer.get(i);
			Submit student_s = submit.getSubmit().get(i);
			
			if(question_s.getQuestion_id() != student_s.getQuestion_id()) {
				return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			}
			
			String answer = "";
			boolean flag = true;
			
			if(question_s.getType()==true) {
				for (String ans : student_s.getAnswer()) {
					if(!question_s.getAnswer().contains(ans)) {
						flag = false;						
					}
					answer += ans+",";
				}
				answer = answer.substring(0,answer.length()-1);
				
				if(question_s.getAnswer().size() != student_s.getAnswer().size()) {
					flag = false;
				}
			}else {
				answer = student_s.getAnswer().get(0);
				if(!question_s.getAnswer().contains(answer)) {
					flag = false;
				}
			}
			
			if(flag) {
				score += question_s.getScore();
			}
			
			testservice.setAnswer(new Answer(exam_student.getId(), question_s.getQuestion_id(), answer, flag));
		}
		
		exam_student.setStudent_score(score);
		testservice.setScore(exam_student);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
