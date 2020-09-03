package com.onlinetest.backend.dto;

public class User {
	private int id;
	private String user_id;
	private String password;
	private int auth;
	private int agency_id;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String user_id, String password) {
		super();
		this.user_id = user_id;
		this.password = password;
	}

	public User(String user_id, String password, int auth, int agency_id) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.auth = auth;
		this.agency_id = agency_id;
	}

	public User(int id, String user_id, String password, int auth, int agency_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.password = password;
		this.auth = auth;
		this.agency_id = agency_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public int getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(int agency_id) {
		this.agency_id = agency_id;
	}
	
	
	
	
}
