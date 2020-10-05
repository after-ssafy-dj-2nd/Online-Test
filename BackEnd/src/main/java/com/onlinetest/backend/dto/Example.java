package com.onlinetest.backend.dto;

public class Example {
    private int id;
    private int question_id;
    private String content;
    private Boolean correct;

    public Example() {
        super();
    }

    public Example(int id, int question_id, String content, Boolean correct) {
        super();
        this.id = id;
        this.question_id = question_id;
        this.content = content;
        this.correct = correct;
    }

    public Example(int question_id, String content, Boolean correct) {
        super();
        this.question_id = question_id;
        this.content = content;
        this.correct = correct;
    }
    
    public Example(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}


