package com.onlinetest.backend.daoTest;

import com.onlinetest.backend.dao.QuestionDaoImpl;
import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoTest {
    // question dao test code

    @Autowired
    private QuestionDaoImpl questionDao;

    @Test
    public void getQuestionsTest() {
        // 유저가 만든 문제 모두 보기 테스트
        // 없는 유저 일때 null 인지 확인!
        // given
        int wronguserId = 1;
        int rightUserId = 2;

        // when
        List<Question> wrongQuestionList = questionDao.getQuestions(wronguserId);
        List<Question> RightQuestionList = questionDao.getQuestions(rightUserId);

        // then
        assertThat(wrongQuestionList.isEmpty()).isEqualTo(true);
        for (Question question: RightQuestionList) {
            assertThat(question.getWriter_id()).isEqualTo(rightUserId);
        }
    }

    @Test
    public void getQuestionTest(){
        // 유저가 만든 문제 테스트
        // 유저가 만들지 않은 문제 테스트 필요!

        // given
        int user_id = 2;
        int wrongQuestionId = 1;
        int rightQuestionId = 17;
        Map<String, Integer> wrongQuestionMap = new HashMap<>();
        Map<String, Integer> rightQuestionMap = new HashMap<>();
        wrongQuestionMap.put("id", wrongQuestionId);
        wrongQuestionMap.put("user_id", user_id);
        rightQuestionMap.put("id", rightQuestionId);
        rightQuestionMap.put("user_id", user_id);

        // when
        Question wrongQuestion = questionDao.getQuestion(wrongQuestionMap);
        Question rightQuestion = questionDao.getQuestion(rightQuestionMap);

        // then
        assertThat(wrongQuestion).isEqualTo(null);
        assertThat(rightQuestion.getId()).isEqualTo(rightQuestionId);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createQuestionTest() {
        // given
        String content = "문제 제목";
        String description = "문제 설명";
        String commentary = "문제 해설";
        boolean type = true;
        int writer_id = 2;
        Question test_question = new Question(content, description, commentary, type, writer_id);
        questionDao.createQuestion(test_question);

        // when
        List<Question> questionList = questionDao.getQuestions(writer_id);

        // then
        int len = questionList.size();
        Question question = questionList.get(len-1);
        assertThat(question.getContent()).isEqualTo(content);
        assertThat(question.getDescription()).isEqualTo(description);
        assertThat(question.getCommentary()).isEqualTo(commentary);
        assertThat(question.isType()).isEqualTo(type);
        assertThat(question.getWriter_id()).isEqualTo(writer_id);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateExamTest(){
        String content = "문제 제목";
        String description = "문제 설명";
        String commentary = "문제 해설";
        boolean type = true;
        int writer_id = 2;

        String reverted_content = "변경된 문제 제목";
        String reverted_description = "변경된 문제 설명";
        String reverted_commentary = "변경된 문제 해설";
        boolean reverted_type = false;

        Question test_question = new Question(content, description, commentary, type, writer_id);
        questionDao.createQuestion(test_question);

        test_question.setContent(reverted_content);
        test_question.setDescription(reverted_description);
        test_question.setCommentary(reverted_commentary);
        test_question.setType(reverted_type);
        questionDao.updateQuestion(test_question);


        // when
        List<Question> questionList = questionDao.getQuestions(writer_id);

        // then
        int len = questionList.size();
        Question question = questionList.get(len-1);
        assertThat(question.getContent()).isEqualTo(reverted_content);
        assertThat(question.getDescription()).isEqualTo(reverted_description);
        assertThat(question.getCommentary()).isEqualTo(reverted_commentary);
        assertThat(question.isType()).isEqualTo(reverted_type);
        assertThat(question.getWriter_id()).isEqualTo(writer_id);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteExamTest(){
        // given
        String content = "문제 제목";
        String description = "문제 설명";
        String commentary = "문제 해설";
        boolean type = true;
        int writer_id = 2;

        int origin_len = questionDao.getQuestions(writer_id).size();
        int last_id = questionDao.getQuestions(writer_id).get(origin_len-1).getId();

        Question test_question = new Question(content, description, commentary, type, writer_id);
        questionDao.createQuestion(test_question);
        int questionId = test_question.getId();
        questionDao.deleteQuestion(questionId);

        // when
        List<Question> questionList = questionDao.getQuestions(writer_id);

        // then
        int len = questionList.size();
        Question question = questionList.get(len-1);
        assertThat(question.getId()).isNotEqualTo(questionId);
        assertThat(len).isEqualTo(origin_len);
        assertThat(question.getId()).isEqualTo(last_id);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void createExampleTest(){
        // given
        int writer_id = 2;
        List<Question> questionList = questionDao.getQuestions(writer_id);
        Question question = questionList.get(questionList.size()-1);

        String content1 = "보기1";
        Boolean correct1 = false;
        String content2 = "보기2";
        Boolean correct2 = true;
        Example example1 = new Example(question.getId(), content1, correct1);
        Example example2 = new Example(question.getId(), content2, correct2);

        questionDao.createExample(example1);
        questionDao.createExample(example2);

        // when
        List<Example> exampleList = questionDao.getQuestionById(question.getId()).getExamples();
        int len = exampleList.size();
        Example test_example1 = exampleList.get(len-2);
        Example test_example2 = exampleList.get(len-1);

        // then
        assertThat(test_example1.getQuestion_id()).isEqualTo(question.getId());
        assertThat(test_example1.getContent()).isEqualTo(content1);
        assertThat(test_example1.getCorrect()).isEqualTo(correct1);
        assertThat(test_example2.getQuestion_id()).isEqualTo(question.getId());
        assertThat(test_example2.getContent()).isEqualTo(content2);
        assertThat(test_example2.getCorrect()).isEqualTo(correct2);
    }

    @Test
    @Transactional
    public void deleteQuestionExamTest(){
        // given
        String question_content = "문제 제목";
        String question_description = "문제 설명";
        String question_commentary = "문제 해설";
        boolean type = true;
        int writer_id = 2;
        Question test_question = new Question(question_content, question_description, question_commentary, type, writer_id);
        questionDao.createQuestion(test_question);
        int question_id = test_question.getId();

        String content = "보기";
        Boolean correct = false;
        for (int i = 1; i < 5; i++) {
            Example example = new Example(question_id, content.concat(Integer.toString(i)), correct);
            questionDao.createExample(example);
        }


        // when
        questionDao.deleteExamples(question_id);

        // then
        test_question = questionDao.getQuestionById(question_id);
        assertThat(test_question.getExamples().isEmpty()).isEqualTo(true);
    }
}
