package com.onlinetest.backend.service;

import java.util.List;

public interface IMailService {

    boolean sendEmail(String subject, String pwdMsg, List<String> email);

}