package com.onlinetest.backend.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Exam {
	private int id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime starttime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime endtime;
	private int teacher_id;
	private String teacher_name;
	
	public Exam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exam(String name, LocalDateTime starttime, LocalDateTime endtime, int teacher_id) {
		super();
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.teacher_id = teacher_id;
	}

	public Exam(int id, String name, LocalDateTime starttime, LocalDateTime endtime, int teacher_id) {
		super();
		this.id = id;
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.teacher_id = teacher_id;
	}

	public Exam(int id, String name, LocalDateTime starttime, LocalDateTime endtime, String teacher_name) {
		super();
		this.id = id;
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.teacher_name = teacher_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
		
}
