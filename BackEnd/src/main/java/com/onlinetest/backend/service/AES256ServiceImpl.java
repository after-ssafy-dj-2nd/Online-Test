package com.onlinetest.backend.service;


import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AES256ServiceImpl implements IAES256Service{
    private static final byte[] key = "onlinetestaeskey".getBytes(StandardCharsets.UTF_8);
    private static final String ALGORITHM = "AES";
    private static AES256ServiceImpl instance = new AES256ServiceImpl();

    @Override
    public String encrypt(String str) throws Exception{
        return Base64.getEncoder().encodeToString(this.encrypt(str.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decrypt(String encText) throws Exception {
        return new String(this.decrypt(Base64.getDecoder().decode(encText)), StandardCharsets.UTF_8);
    }

    private byte[] encrypt(byte[] plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainText);
    }

    private byte[] decrypt(byte[] cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(cipherText);
    }
}
