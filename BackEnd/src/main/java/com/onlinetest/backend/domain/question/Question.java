package com.onlinetest.backend.domain.question;

import com.onlinetest.backend.domain.example.Example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @Column
    private String description;

    @Column
    private String commentary;

    @Column
    private Boolean type;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Example> examples = new ArrayList<>();

    public Question() {
    }

    public Question(int id, String content, String description, String commentary, Boolean type, List<Example> examples) {
        this.id = id;
        this.content = content;
        this.description = description;
        this.commentary = commentary;
        this.type = type;
        this.examples = examples;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getCommentary() {
        return commentary;
    }

    public Boolean getType() {
        return type;
    }

    public List<Example> getExamples() {
        return examples;
    }
}
