package com.onlinetest.backend.dto.swagger;

import java.util.List;

public class ExamUserSwagger {
    private int exam_id;
    private List<String> userList;

    public ExamUserSwagger(int exam_id, List<String> userList) {
        super();
        this.exam_id = exam_id;
        this.userList = userList;
    }

    public ExamUserSwagger() {
        super();
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public List<String> getUserList() {
        return userList;
    }
}
