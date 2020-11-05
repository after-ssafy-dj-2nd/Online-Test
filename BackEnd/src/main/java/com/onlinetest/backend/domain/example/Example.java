package com.onlinetest.backend.domain.example;

import com.onlinetest.backend.domain.question.Question;

import javax.persistence.*;

@Entity
public class Example {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column
    private String content;

    @Column
    private Boolean correct;

    public Example() {
    }

    public Example(int id, Question question, String content, Boolean correct) {
        this.id = id;
        this.question = question;
        this.content = content;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public Boolean getCorrect() {
        return correct;
    }
}
