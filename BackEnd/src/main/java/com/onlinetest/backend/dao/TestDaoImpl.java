package com.onlinetest.backend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;

@Repository
public class TestDaoImpl {

	String ns = "test.";
	
	@Autowired
	private SqlSession sqlSeesion;

	public void setStartTest(ExamStudent examStudent) {
		sqlSeesion.insert(ns+"setStartTest", examStudent);
	}

	public Exam getExam(int exam_id) {
		return sqlSeesion.selectOne(ns+"getExam", exam_id);
	}

	public List<Question> getQuestion(int exam_id) {
		return sqlSeesion.selectList(ns+"getQuestion", exam_id);
	}
}
