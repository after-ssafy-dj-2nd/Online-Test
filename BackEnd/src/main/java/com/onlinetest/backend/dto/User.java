package com.onlinetest.backend.dto;

public class User {
	private int id;
	private String user_id;
	private String password;
	private int auth;
	private String name;
	private String email;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String user_id, String password) {
		super();
		this.user_id = user_id;
		this.password = password;
	}

	public User(String user_id, String password, String name, String email) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public User(String user_id, String password, int auth, String name, String email) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.auth = auth;
		this.name = name;
		this.email = email;
	}

	public User(int id, String user_id, String password, int auth, String name, String email) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.password = password;
		this.auth = auth;
		this.name = name;
		this.email = email;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
