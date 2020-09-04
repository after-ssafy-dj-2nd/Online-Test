package com.onlinetest.backend.dto;

public class Problem {
    private int id;
    private String content;
    private int answer;
    private String commentary;
    private String type;
    private int disclosure;
    private int writer;

    public Problem() {
        super();
    }
    public Problem(int id, String content, int answer, String commentary, String type, int disclosure, int writer) {
        super();
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.commentary = commentary;
        this.type = type;
        this.disclosure = disclosure;
        this.writer = writer;
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
    public void SetContent(String content) { this.content = content; }
    public int getAnswer() { return  answer; }
    public void setAnswer(int answer) { this.answer = answer; }
    public String getCommentary() { return commentary; }
    public void setCommentary(String commentary) { this.commentary = commentary; }
    public String getType() {
        return type;
    }
    public void setType(String type) { this.type = type; }
    public int getDisclosure() { return  disclosure; }
    public void setDisclosure(int disclosure) { this.disclosure = disclosure; }
    public int getWriter() { return  writer; }
    public void setWriter(int writer) { this.writer = writer; }
}
