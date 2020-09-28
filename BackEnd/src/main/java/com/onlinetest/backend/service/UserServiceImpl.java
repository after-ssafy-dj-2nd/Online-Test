package com.onlinetest.backend.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinetest.backend.dao.UserDaoImpl;
import com.onlinetest.backend.dto.User;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserDaoImpl userdao;
	
	@Autowired
	private JavaMailSender javaMailSender;

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

	@Override
	@Transactional(readOnly=true)
	public String getEmail(String user_id) {
		return userdao.getEmail(user_id);
	}

	@Override
	@Transactional
	public void updatePwd(User user) {
		userdao.updatePwd(user);
	}

	@Override
	public boolean sendEamil(String subject, String pwdMsg, String to) {
		MimeMessage message = javaMailSender.createMimeMessage();
		 
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(pwdMsg, true);
            helper.setFrom("onlinetest901@gmail.com");
            helper.setTo(to);
 
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
	}

}
