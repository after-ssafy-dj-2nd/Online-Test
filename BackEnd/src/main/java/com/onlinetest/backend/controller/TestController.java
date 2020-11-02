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
import com.onlinetest.backend.service.IMailService;

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

	@Autowired
	private IMailService mailservice;


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

		String URL = "http://221.158.91.249:3000/tryout/"+exam_id+"/wait";
		String subject = "[online-test] 시험 응시 안내 입니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("<div align='center' style='border:1px solid black; font-family:verdana'>");
		sb.append("<h3 style='color:blue;'>아래 URL에 접속하여 시험 응시 바랍니다.</h3>");
		sb.append("<div style='font-size:130%'>");
		sb.append("<a href='");
		sb.append(URL);
		sb.append("'>");
		sb.append(URL);
		sb.append("</a></div><br/>");
		mailservice.sendEmail(subject, sb.toString(), registeredStudent);

		result.setStatus("학생 등록이 완료되었습니다.");
		result.setRegister(registeredStudent);
		result.setNotRegister(notFoundedStudent);
		return new ResponseEntity<ExamUserResultSwagger>(result, HttpStatus.NO_CONTENT);
	}
	
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 준비 - 시험 정보")
	@RequestMapping(value = "/readytest", method = RequestMethod.GET)
	public ResponseEntity<ExamReadySwagger> readytest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------readytest-----------------------------" + new Date());
		
		int student_id = jwtservice.getId();
		
		ExamReadySwagger result = new ExamReadySwagger();
		
		Exam exam = testservice.getExam(exam_id);
		
		if(exam == null) {
			result.setStatus("해당하는 시험을 찾을 수 없습니다.");
			return new ResponseEntity<ExamReadySwagger>(result, HttpStatus.NOT_FOUND);
		}
		if(testservice.isExamStudent(new ExamStudent(student_id, exam_id)) == 0) {
			result.setStatus("시험 응시가 가능한 학생이 아닙니다.");
			return new ResponseEntity<ExamReadySwagger>(result, HttpStatus.BAD_REQUEST);
		}
		
		result.setStatus("OK");
		result.setExam(exam);
		return new ResponseEntity<ExamReadySwagger>(result, HttpStatus.OK);
	}
	
	@ApiImplicitParam(name="exam_id", value="시험번호")
	@ApiOperation(value = "시험 응시 시작")
	@RequestMapping(value = "/starttest", method = RequestMethod.GET)
	public ResponseEntity<ExamStartSwagger> starttest(@RequestParam int exam_id) throws Exception {
		logger.info("1-------------starttest-----------------------------" + new Date());
		
		int student_id = jwtservice.getId();
		
		ExamStartSwagger result = new ExamStartSwagger();
		
		Exam exam = testservice.getExam(exam_id);		
		if(exam == null) {
			result.setStatus("해당하는 시험을 찾을 수 없습니다.");
			return new ResponseEntity<ExamStartSwagger>(result, HttpStatus.NOT_FOUND);
		}
		
		if(testservice.isExamStudent(new ExamStudent(student_id, exam_id)) == 0) {
			result.setStatus("시험 응시가 가능한 학생이 아닙니다.");
			return new ResponseEntity<ExamStartSwagger>(result, HttpStatus.BAD_REQUEST);
		}
		if(testservice.getStudentStartTime(new ExamStudent(student_id, exam_id))!=null) {
			result.setStatus("시험 응시 이력이 있습니다.");
			return new ResponseEntity<ExamStartSwagger>(result, HttpStatus.BAD_REQUEST);
		}
		if(testservice.isPossible(exam_id) == 0) {
			result.setStatus("시험 응시 가능한 시간이 아닙니다.");
			return new ResponseEntity<ExamStartSwagger>(result, HttpStatus.BAD_REQUEST);
		}
		
		testservice.setStartTest(new ExamStudent(student_id, exam_id));
		List<Question> questions = testservice.getQuestion(exam_id);
		
		result.setStatus("OK");
		result.setQuestions(questions);
		return new ResponseEntity<ExamStartSwagger>(result, HttpStatus.OK);
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
