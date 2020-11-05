package com.onlinetest.backend.RepositoryTest;

import com.onlinetest.backend.domain.example.Example;
import com.onlinetest.backend.domain.example.ExampleRepository;
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
public class ExampleRepositoryTest {
    @Autowired
    ExampleRepository exampleRepository;

    @Test
    public void readExamples(){
        List<Example> examplesList = exampleRepository.findAll();
        assertThat(examplesList.size()).isEqualTo(15);
    }
}
