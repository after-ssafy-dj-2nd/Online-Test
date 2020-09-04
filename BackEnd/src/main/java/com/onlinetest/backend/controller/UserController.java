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
import com.onlinetest.backend.dto.swagger.UserSwagger;
import com.onlinetest.backend.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userservice;
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful / true:성공, false:실패", response = Boolean.class)})
	@ApiOperation(value = "회원가입", response = Boolean.class)
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Boolean> signUp(@RequestBody @ApiParam(value="id 제외하고 입력") User user) throws Exception {
		logger.info("1-------------signUp-----------------------------" + new Date());
		Boolean signUp = false;
		
		try {
			userservice.signUp(user);
			signUp = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Boolean>(signUp, HttpStatus.OK);
	}

	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful / true:사용가능, false:중복", response = Boolean.class)})
	@ApiOperation(value = "회원가입 시 id 중복 체크", response = Boolean.class)
	@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
	public ResponseEntity<Boolean> idCheck(@RequestParam String user_id) throws Exception {
		logger.info("1-------------idCheck-----------------------------" + new Date());
		
		boolean idCheck = false;
		
		int cnt = userservice.idCheck(user_id);

		if(cnt==0) {
			idCheck = true;
		}

		return new ResponseEntity<Boolean>(idCheck, HttpStatus.OK);
	}
	
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successful / 성공 - login:true, user:user정보, 실패 - login:false", response = UserSwagger.class)})
	@ApiOperation(value = "로그인", response = UserSwagger.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value="user_id, password만 입력") User user) throws Exception {
		logger.info("1-------------login-----------------------------" + new Date());
		Map<String, Object> resultMap = new HashMap<>();
		
		User userInfo = userservice.login(user);
		
		if(userInfo==null) {
			resultMap.put("login", false);
		}else {
			resultMap.put("login", true);
			resultMap.put("userInfo", userInfo);			
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

}
