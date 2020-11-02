package com.onlinetest.backend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinetest.backend.dto.Answer;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.ExamStudent;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.Submit;

@Repository
public class TestDaoImpl {

	String ns = "test.";

	@Autowired
	private SqlSession sqlSeesion;

	// 수정필요-> 시작시간만 변경 하도록 변경해야 할듯
	public void setStartTest(ExamStudent examStudent) {
		sqlSeesion.insert(ns+"setStartTest", examStudent);
	}

	public Exam getExam(int exam_id) {
		return sqlSeesion.selectOne(ns+"getExam", exam_id);
	}

	public List<Question> getQuestion(int exam_id) {
		return sqlSeesion.selectList(ns+"getQuestion", exam_id);
	}

	public int isExamStudent(ExamStudent examStudent) {
		return sqlSeesion.selectOne(ns+"isExamStudent", examStudent);
	}

	public ExamStudent getExamStudent(ExamStudent examStudent) {
		return sqlSeesion.selectOne(ns+"getExamStudent", examStudent);
	}

	public List<Submit> getAnswer(int exam_id) {
		return sqlSeesion.selectList(ns+"getAnswer", exam_id);
	}

	public void setAnswer(Answer answer) {
		sqlSeesion.insert(ns+"setAnswer", answer);
	}

	public void setScore(ExamStudent exam_student) {
		sqlSeesion.update(ns+"setScore", exam_student);
	}

	public int isPossible(int exam_id) {
		return sqlSeesion.selectOne(ns+"isPossible", exam_id);
	}

	// 수정이 필요 -> db starttime 수정 후
	public void setExamStudent(ExamStudent examStudent) {
		sqlSeesion.insert(ns + "setExamStudent", examStudent);
	}

	public String getStudentStartTime(ExamStudent examStudent) {
		return sqlSeesion.selectOne(ns+"getStudentStartTime", examStudent);
	}
}
