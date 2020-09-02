package com.onlinetest.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
