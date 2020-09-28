package com.onlinetest.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinetest.backend.dao.TestDaoImpl;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;

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
	public int getExamStudent(ExamStudent examStudent) {
		return testdao.getExamStudent(examStudent);
	}

	

	

}
