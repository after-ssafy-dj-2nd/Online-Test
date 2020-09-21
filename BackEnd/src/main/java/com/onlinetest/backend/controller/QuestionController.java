package com.onlinetest.backend.controller;

import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExample;
import com.onlinetest.backend.dto.swagger.QuestionSwagger;
import com.onlinetest.backend.service.IQuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @ApiOperation(value = "모든 문제 보기", response = QuestionSwagger.class)
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<QuestionExample>> getQuestions() {
        int user_id = 2;

        List<Question> questions = questionService.getQuestions(user_id);
        List<QuestionExample> questionExampleList = new ArrayList<QuestionExample>();
        for (Question question : questions) {
            List<Example> examples = questionService.getExamples(question.getId());
            QuestionExample questionExample = new QuestionExample(question);
            questionExample.setExamples(examples);
            questionExampleList.add(questionExample);
        }
        return new ResponseEntity<List<QuestionExample>>(questionExampleList, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 상세 보기", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<QuestionExample> getQuestion(@RequestParam int id) {
        int user_id = 2;

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("user_id", user_id);
        Question question = questionService.getQuestion(paramMap);

        if (question == null) {
            QuestionExample questionExample = new QuestionExample();
            return new ResponseEntity<QuestionExample>(questionExample, HttpStatus.BAD_REQUEST);
        } else {
            QuestionExample questionExample = new QuestionExample(question);
            questionExample.setExamples(questionService.getExamples(question.getId()));
            return new ResponseEntity<QuestionExample>(questionExample, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "문제 생성", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addQuestion(@RequestBody QuestionExample questionExample){
        int user_id = 2;
        Map<String, Object> resultMap = new HashMap<>();
      
        if (questionExample.getWriter_id() != user_id){
            resultMap.put("status", "401");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        else if (questionExample.getContent() == null){
            resultMap.put("status", "400");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        questionService.createQuestion(questionExample);
        List<Example> examples = questionExample.getExamples();
        for (Example example:examples) {
            example.setQuestion_id(questionExample.getId());
            questionService.createExample(example);
        }

        resultMap.put("status", "200");
        resultMap.put("question", questionExample);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 수정", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuestion(@RequestBody QuestionExample questionExample) {
        int user_id = 2;
        Map<String, Object> resultMap = new HashMap<>();

        if (questionExample.getWriter_id() != user_id){
            resultMap.put("status", "401");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        else if (questionExample.getContent() == null){
            resultMap.put("status", "400");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        List<Example>examples = questionExample.getExamples();
        questionService.deleteExamples(questionExample.getId());
        questionService.updateQuestion(questionExample);

        for (Example example:examples) {
            example.setQuestion_id(questionExample.getId());
            questionService.createExample(example);
        }

        resultMap.put("status", "200");
        resultMap.put("question", questionExample);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 삭제", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteQuestion(@RequestParam int id) {
        int user_id = 2;
        Question question = questionService.getQuestionById(id);
        Map<String, Object> resultMap = new HashMap<>();

        if (question.getWriter_id() != user_id){
            resultMap.put("status", "401");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        questionService.deleteExamples(id);
        questionService.deleteQuestion(id);
        resultMap.put("status", "200");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
