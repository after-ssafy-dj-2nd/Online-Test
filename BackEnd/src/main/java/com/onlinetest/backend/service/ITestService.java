package com.onlinetest.backend.service;

import java.util.List;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;

public interface ITestService {
	void setStartTest(ExamStudent examStudent);

	Exam getExam(int exam_id);

	List<Question> getQuestion(int exam_id);

	int getExamStudent(ExamStudent examStudent);
}	
