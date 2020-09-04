package com.onlinetest.backend.dto.swagger;

import com.onlinetest.backend.dto.User;

public class UserSwagger {
	private Boolean login;
	private User user;
	public UserSwagger() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserSwagger(Boolean login, User user) {
		super();
		this.login = login;
		this.user = user;
	}
	public Boolean getLogin() {
		return login;
	}
	public void setLogin(Boolean login) {
		this.login = login;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
