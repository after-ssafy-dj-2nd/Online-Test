package com.onlinetest.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinetest.backend.dao.ProblemDaoImpl;
import com.onlinetest.backend.dao.UserDaoImpl;
import com.onlinetest.backend.dto.User;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserDaoImpl userdao;

	@Override
	@Transactional
	public void signUp(User user) {
		userdao.signUp(user);		
	}

	@Override
	@Transactional(readOnly=true)
	public int idCheck(String user_id) {
		return userdao.idCheck(user_id);
	}

	@Override
	@Transactional(readOnly=true)
	public User login(User user) {
		return userdao.login(user);
	}

	

}
