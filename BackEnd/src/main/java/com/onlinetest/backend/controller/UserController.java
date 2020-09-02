package com.onlinetest.backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.service.IUserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userservice;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> signUp(@RequestBody User user) throws Exception {
		logger.info("1-------------signUp-----------------------------" + new Date());
		Map<String, Object> resultMap = new HashMap<>();
		
		userservice.signUp(user);

		resultMap.put("signUp", "ok");

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> idCheck(@RequestParam String user_id) throws Exception {
		logger.info("1-------------idCheck-----------------------------" + new Date());
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int cnt = userservice.idCheck(user_id);

		if(cnt==0) {
			resultMap.put("idCheck", true);
		}else {
			resultMap.put("idCheck", false);
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

}
