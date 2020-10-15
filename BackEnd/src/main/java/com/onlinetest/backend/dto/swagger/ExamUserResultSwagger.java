package com.onlinetest.backend.dto.swagger;

import java.util.List;

public class ExamUserResultSwagger {
    private String status;
    private List<String> notRegister;
    private List<String> register;

    public ExamUserResultSwagger(String status, List<String> notRegister, List<String> register) {
        super();
        this.status = status;
        this.notRegister = notRegister;
        this.register = register;
    }

    public ExamUserResultSwagger() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getNotRegister() {
        return notRegister;
    }

    public void setNotRegister(List<String> notRegister) {
        this.notRegister = notRegister;
    }

    public List<String> getRegister() {
        return register;
    }

    public void setRegister(List<String> register) {
        this.register = register;
    }
}
