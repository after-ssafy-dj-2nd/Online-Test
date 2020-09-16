package com.onlinetest.backend.dto;

public class Question {
    private int id;
    private String content;
    private String description;
    private String commentary;
    private boolean type;
    private int writer_id;
    private int score;

    public Question() {
        super();
    }

    public Question(int id, String content, String description, String commentary, boolean type, int writer_id) {
        super();
        this.id = id;
        this.content = content;
        this.description = description;
        this.commentary = commentary;
        this.type = type;
        this.writer_id = writer_id;
    }

    public Question(int id, String content, String description, boolean type, int score) {
		super();
		this.id = id;
		this.content = content;
		this.description = description;
		this.type = type;
		this.score = score;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(int writer_id) {
        this.writer_id = writer_id;
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
    
}


