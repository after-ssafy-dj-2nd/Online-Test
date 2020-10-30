package com.onlinetest.backend.service;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailServiceImpl implements IMailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendEmail(String subject, String msg, List<String> to) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(msg, true);
            helper.setFrom("onlinetest901@gmail.com");
//            helper.setTo(to);

            InternetAddress[] toAddr = new InternetAddress[to.size()];
            for (int i = 0; i < toAddr.length; i++) {
                toAddr[i] = new InternetAddress(to.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, toAddr);

            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

}