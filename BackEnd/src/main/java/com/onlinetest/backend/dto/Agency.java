package com.onlinetest.backend.dto;

public class Agency {
	private int id;
	private int name;
	public Agency() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Agency(int id, int name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	
	
}
