package com.onlinetest.backend.dto;

import java.util.List;

public class QuestionExample extends Question {

    private List<Example> examples;

    public QuestionExample() {
        super();
    }

    public QuestionExample(Question question) {
        super(question.getId(), question.getContent(), question.getDescription(), question.getCommentary(), question.getType(), question.getWriter_id());
    }

    public QuestionExample(int id, String content, String description, String commentary, boolean type, int writer_id, List<Example> examples) {
        super(id, content, description, commentary, type, writer_id);
        this.examples = examples;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
}
