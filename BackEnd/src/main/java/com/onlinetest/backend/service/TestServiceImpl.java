package com.onlinetest.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinetest.backend.dao.TestDaoImpl;
import com.onlinetest.backend.dto.Answer;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.Submit;

@Service
public class TestServiceImpl implements ITestService{
	
	@Autowired
	private TestDaoImpl testdao;

	@Override
	@Transactional
	public void setStartTest(ExamStudent examStudent) {
		testdao.setStartTest(examStudent);
	}

	@Override
	@Transactional(readOnly=true)
	public Exam getExam(int exam_id) {
		return testdao.getExam(exam_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Question> getQuestion(int exam_id) {
		return testdao.getQuestion(exam_id);
	}

	@Override
	@Transactional(readOnly=true)
	public int isExamStudent(ExamStudent examStudent) {
		return testdao.isExamStudent(examStudent);
	}

	@Override
	@Transactional(readOnly=true)
	public ExamStudent getExamStudent(ExamStudent examStudent) {
		return testdao.getExamStudent(examStudent);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Submit> getAnswer(int exam_id) {
		return testdao.getAnswer(exam_id);
	}

	@Override
	@Transactional
	public void setAnswer(Answer answer) {
		testdao.setAnswer(answer);
	}

	@Override
	@Transactional
	public void setScore(ExamStudent exam_student) {
		testdao.setScore(exam_student);
	}

	@Override
	@Transactional(readOnly=true)
	public int isPossible(int exam_id) {
		return testdao.isPossible(exam_id);
	}

	@Override
	@Transactional
	public void setExamStudent(ExamStudent examStudent) {
		testdao.setExamStudent(examStudent);
	}

	@Override
	@Transactional(readOnly=true)
	public String getStudentStartTime(ExamStudent examStudent) {
		return testdao.getStudentStartTime(examStudent);
	}

}
