package com.onlinetest.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.onlinetest.backend.dto.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlinetest.backend.service.IProblemService;

@RestController
@RequestMapping("/api")
public class ProblemController {
	
	@Autowired
	private IProblemService problemservice;
	
	@RequestMapping(value="/test")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> agency(@RequestParam String name) {
        
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println(name);
		problemservice.agency(name);		
		
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        
    }

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Problem>> getProblems() {
		List<Problem> problems = problemservice.getProblems();
		return new ResponseEntity<List<Problem>>(problems, HttpStatus.OK);
	}

	@RequestMapping(value = "/problem", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getProblem(@RequestParam int id) {
		Map<String, Object> resultMap = new HashMap<>();
		problemservice.getProblem(id);

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

    @RequestMapping(value = "/problem", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addProblem(@RequestBody Problem problem){
		Map<String, Object> resultMap = new HashMap<>();
		problemservice.addProblem(problem);

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/problem", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateProblem(@RequestBody Problem problem) {
		Map<String, Object> resultMap = new HashMap<>();
		problemservice.updateProblem(problem);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/problem", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteProblem(@RequestParam int id) {
		Map<String, Object> resultMap = new HashMap<>();
		problemservice.deleteProblem(id);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

}
