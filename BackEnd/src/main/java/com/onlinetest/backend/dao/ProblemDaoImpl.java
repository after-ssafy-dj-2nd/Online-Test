package com.onlinetest.backend.dao;

import com.onlinetest.backend.dto.Problem;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProblemDaoImpl {

	String ns = "problem.";
	
	@Autowired
	private SqlSession sqlSeesion;

	public void setName(String name) {
		sqlSeesion.insert(ns+"setName", name);
	}

	public List<Problem> getProblems(){
		return sqlSeesion.selectList(ns + "getProblems");
	}

	public Problem getProblem(int id){
		return sqlSeesion.selectOne(ns + "getProblem", id);
	}

	public void addProblem(Problem problem){ sqlSeesion.insert(ns + "addProblem", problem); }

	public void updateProblem(Problem problem){
		sqlSeesion.update(ns + "updateProblem", problem);
	}

	public void deleteProblem(int id){
		sqlSeesion.delete(ns + "deleteProblem", id);
	}
}
