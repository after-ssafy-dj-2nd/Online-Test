package com.onlinetest.backend.dto;

import java.util.List;

public class Question {
    private int id;
    private String content;
    private String description;
    private String commentary;
    private boolean type;
    private int writer_id;
    private List<Example> examples;

    public Question() {
    }

    public Question(int id, String content, String description, String commentary, boolean type, int writer_id, List<Example> examples) {
        this.id = id;
        this.content = content;
        this.description = description;
        this.commentary = commentary;
        this.type = type;
        this.writer_id = writer_id;
        this.examples = examples;
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

    public boolean isType() {
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

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
}



