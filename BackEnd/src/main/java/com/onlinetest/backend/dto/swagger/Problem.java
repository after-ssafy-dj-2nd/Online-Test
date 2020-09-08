package com.onlinetest.backend.dto.swagger;

public class Problem {
    private int id;
    private String content;
    private String description;
    private int score;
    private String type;

    public Problem() {
        super();
    }

	public Problem(int id, String content, String description, int score, String type) {
		super();
		this.id = id;
		this.content = content;
		this.description = description;
		this.score = score;
		this.type = type;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
