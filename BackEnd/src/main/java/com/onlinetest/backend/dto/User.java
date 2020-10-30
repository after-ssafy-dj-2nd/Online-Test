package com.onlinetest.backend.dto;

public class User {
	private int id;
	private String email;
	private String password;
	private int auth;
	private String name;


	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String password) {
		super();
		this.password = password;
	}

	public User(String password, String name, String email) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;

	}

	public User(String email, String password, int auth, String name) {
		super();
		this.password = password;
		this.auth = auth;
		this.name = name;
		this.email = email;
	}

	public User(int id, String email, String password, int auth, String name) {
		super();
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
}
