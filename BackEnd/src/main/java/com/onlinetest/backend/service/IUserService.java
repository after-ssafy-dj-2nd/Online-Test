package com.onlinetest.backend.service;

import com.onlinetest.backend.dto.User;

public interface IUserService {
	void signUp(User user);
	
	int idCheck(String email);
	
	User login(User user);

	void updatePwd(User user);

	boolean signUpCheck(String email);

	int getUserPk(String email);

	String getUserName(int id);

	void setAuthenticate(int id);
}	
