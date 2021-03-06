package com.onlinetest.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public int idCheck(String email) {
		return userdao.idCheck(email);
	}

	@Override
	@Transactional(readOnly=true)
	public User login(User user) {
		return userdao.login(user);
	}

	@Override
	@Transactional
	public void updatePwd(User user) {
		userdao.updatePwd(user);
	}

	public boolean signUpCheck(String email) {
		if (userdao.signUpCheck(email)!= 0)
			return true;
		else
			return false;
	}

	@Override
	public int getUserPk(String email){
		return userdao.getUserPk(email);
	}

	@Override
	public String getUserName(int id) {
		return userdao.getUserName(id);
	}

	@Override
	public void setAuthenticate(int id) {
		userdao.setAuthenticate(id);
	}
}