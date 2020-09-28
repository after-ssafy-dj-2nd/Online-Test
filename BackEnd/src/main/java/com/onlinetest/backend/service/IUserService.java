package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.User;

public interface IUserService {
	void signUp(User user);
	
	int idCheck(String user_id);	
	
	User login(User user);

	String getEmail(String user_id);

	void updatePwd(User user);

	boolean sendEamil(String subject, String pwdMsg, String email);

}	
