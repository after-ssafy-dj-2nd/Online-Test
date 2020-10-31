package com.onlinetest.backend.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public interface IAES256Service {

    String encrypt(String str) throws Exception;

    String decrypt(String str) throws Exception;
}
