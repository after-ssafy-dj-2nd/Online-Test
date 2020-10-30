package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.User;

public interface IUserService {
	void signUp(User user);
	
	int idCheck(String email);
	
	User login(User user);

	void updatePwd(User user);

	boolean sendEamil(String subject, String pwdMsg, String email);

}	
