package com.onlinetest.backend.controller;

import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.swagger.QuestionSwagger;
import com.onlinetest.backend.service.IQuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @ApiOperation(value = "모든 문제 보기", response = QuestionSwagger.class)
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = questionService.getQuestions();
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 보기", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Question> getProblem(@RequestParam int id) {
        Question question = questionService.getQuestion(id);
        System.out.println(question);
        if (question == null){
            return new ResponseEntity<Question>(question, HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<Question>(question, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "문제 생성", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addProblem(@RequestBody Question question){
        Map<String, Object> resultMap = new HashMap<>();
        if (question.getContent() == null){
            resultMap.put("status", "400");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        questionService.createQuestion(question);
        resultMap.put("status", "200");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 수정", response = QuestionSwagger.class)
    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProblem(@RequestBody Question question) {
        Map<String, Object> resultMap = new HashMap<>();
        if (questionService.getQuestion(question.getId()) == null | question.getContent() == null){
            resultMap.put("status", "400");
            resultMap.put("problemInfo", null);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
        questionService.updateQuestion(question);
        Question questionInfo = questionService.getQuestion(question.getId());
        resultMap.put("problemInfo", questionInfo);
        resultMap.put("status", "200");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "문제 삭제", response = QuestionSwagger.class)
    @RequestMapping(value = "/problem", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteProblem(@RequestParam int id) {
        Map<String, Object> resultMap = new HashMap<>();
        questionService.deleteQuestion(id);
        resultMap.put("status", "200");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
