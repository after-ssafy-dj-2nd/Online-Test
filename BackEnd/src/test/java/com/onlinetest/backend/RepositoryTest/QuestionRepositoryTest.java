package com.onlinetest.backend.RepositoryTest;

import com.onlinetest.backend.domain.example.Example;
import com.onlinetest.backend.domain.question.Question;
import com.onlinetest.backend.domain.question.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @Test
    public void readQuestions(){
        List<Question> questionList = questionRepository.findAll();
        for (Question question: questionList) {
            System.out.println("-------" + question.getId() + "번 문제" + "-------");
            for (Example example: question.getExamples()) {
                System.out.println(example.getContent());
            }
        }
        assertThat(questionList.size()).isEqualTo(3);
    }
}