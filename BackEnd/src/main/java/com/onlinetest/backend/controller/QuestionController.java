package com.onlinetest.backend.controller;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.swagger.QuestionList;
import com.onlinetest.backend.dto.swagger.QuestionSwagger;
import com.onlinetest.backend.service.IJwtService;
import com.onlinetest.backend.service.IQuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;
    
    @Autowired
    private IJwtService jwtservice;

    @ApiOperation(value = "모든 문제 보기", response = QuestionList.class)
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Question>> getQuestions() {
        int user_id = jwtservice.getId();

        List<Question> questions = questionService.getQuestions(user_id);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 상세 보기", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Question> getQuestion(@RequestParam int id) {
        int user_id = jwtservice.getId();

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("user_id", user_id);
        Question question = questionService.getQuestion(paramMap);

        if (question == null) {
            return new ResponseEntity<Question>(question, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Question>(question, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "문제 생성", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        int user_id = jwtservice.getId();

        if (question.getWriter_id() != user_id){
            return new ResponseEntity<Question>(new Question(), HttpStatus.UNAUTHORIZED);
        }
        else if (question.getContent() == null){
            return new ResponseEntity<Question>(new Question(), HttpStatus.BAD_REQUEST);
        }
        questionService.createQuestion(question);
        int q_id = question.getId();
        List<Example> examples = question.getExamples();
        for (Example example:examples) {
            example.setQuestion_id(q_id);
            questionService.createExample(example);
        }
        Question question_result = questionService.getQuestionById(q_id);
        return new ResponseEntity<Question>(question_result, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 수정", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        int user_id = jwtservice.getId();
        Map<String, Object> resultMap = new HashMap<>();

        if (question.getWriter_id() != user_id){
            return new ResponseEntity<Question>(new Question(), HttpStatus.UNAUTHORIZED);
        }
        else if (question.getContent() == null){
            return new ResponseEntity<Question>(new Question(), HttpStatus.BAD_REQUEST);
        }
        int q_id = question.getId();
        List<Example>examples = question.getExamples();
        questionService.deleteExamples(q_id);
        questionService.updateQuestion(question);

        for (Example example:examples) {
            example.setQuestion_id(q_id);
            questionService.createExample(example);
        }
        Question question_result = questionService.getQuestionById(q_id);
        return new ResponseEntity<Question>(question_result, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 삭제", response = Boolean.class)
    @RequestMapping(value = "/question", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteQuestion(@RequestParam int id) {
        int user_id = jwtservice.getId();
        Question question = questionService.getQuestionById(id);

        if (question == null){
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        }
        else if (question.getWriter_id() != user_id){
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }

        questionService.deleteExamples(id);
        questionService.deleteQuestion(id);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
