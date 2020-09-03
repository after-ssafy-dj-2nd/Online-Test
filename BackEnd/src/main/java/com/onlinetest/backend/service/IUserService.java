package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.User;

public interface IUserService {
	void signUp(User user);
	
	int idCheck(String user_id);	
	
	User login(User user);
}	
