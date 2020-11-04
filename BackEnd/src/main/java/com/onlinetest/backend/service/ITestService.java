package com.onlinetest.backend.service;

import java.util.List;

import com.onlinetest.backend.dto.Answer;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.Submit;

public interface ITestService {
	void setStartTest(ExamStudent examStudent);

	Exam getExam(int exam_id);

	List<Question> getQuestion(int exam_id);

	int isExamStudent(ExamStudent examStudent);

	ExamStudent getExamStudent(ExamStudent examStudent);

	List<Submit> getAnswer(int exam_id);

	void setAnswer(Answer answer);

	void setScore(ExamStudent exam_student);

	int isPossible(int exam_id);

	void setExamStudent(ExamStudent examStudent);

	String getStudentStartTime(ExamStudent examStudent);
}	
