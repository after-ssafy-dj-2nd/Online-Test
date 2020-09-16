package com.onlinetest.backend.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExamStudent {
	private int id;
	private int student_id;
	private int exam_id;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime starttime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime endtime;
	private int student_score;
	
	public ExamStudent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExamStudent(int id, int student_id, int exam_id, LocalDateTime starttime, LocalDateTime endtime,
			int student_score) {
		super();
		this.id = id;
		this.student_id = student_id;
		this.exam_id = exam_id;
		this.starttime = starttime;
		this.endtime = endtime;
		this.student_score = student_score;
	}

	public ExamStudent(int student_id, int exam_id) {
		super();
		this.student_id = student_id;
		this.exam_id = exam_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getExam_id() {
		return exam_id;
	}

	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
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

	public int getStudent_score() {
		return student_score;
	}

	public void setStudent_score(int student_score) {
		this.student_score = student_score;
	}
	
	
	
}
