package com.onlinetest.backend.controllerTest;

import com.onlinetest.backend.controller.QuestionController;
import com.onlinetest.backend.dao.QuestionDaoImpl;
import com.onlinetest.backend.dto.Example;
import com.onlinetest.backend.dto.Question;
import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.dto.swagger.QuestionList;
import com.onlinetest.backend.service.IJwtService;
import com.onlinetest.backend.service.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class QuestionControllerTest {
    // question 컨트롤러 테스트
    // dao를 통해 나온 값과 직접 비교하며 맞는지 확인한다. + http status까지!!


    @LocalServerPort
    private final int port = 8080;

    String url = "http://localhost:" + port + "/api";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuestionController questionController;

    @Autowired
    private QuestionDaoImpl questionDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private IJwtService jwtService;

    int user_id = 4;
    HttpHeaders headers;
    List<Integer> used_pk_list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        // 테스트 진행되기 전에 진행 되는 부분!
        // https://beomseok95.tistory.com/302 참조 바람.

        User userInfo = userService.login(new User("test", "test"));
        String token = jwtService.create("user", userInfo, "userInfo");
        headers = new HttpHeaders();
        headers.add("access-token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @After
    public void clean() throws Exception {
        for (int pk:used_pk_list) {
            questionDao.deleteExamples(pk);
            questionDao.deleteQuestion(pk);
        }
        used_pk_list = new ArrayList<>();
    }

    @Test
    public void getQuestionsTest() {
        // 모든 문제 보기 기능
        // given
        String uri = url + "/questions";
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // when
        // restTemplete exchange 메소드 사용법!
        // 순서대로 url, method, entity(header, params), return type
        ResponseEntity<QuestionList> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, QuestionList.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Question> questionList1 = questionDao.getQuestions(user_id).getQuestion();
        List<Question> questionList2 = responseEntity.getBody().getQuestion();
        int len = questionList1.size();
        assertThat(questionList2.size()).isEqualTo(len);
        for (int i=0; i < len; i++){
            Question question1 = questionList1.get(i);
            Question question2 = questionList2.get(i);
            assertThat(question1.getId()).isEqualTo(question2.getId());
            assertThat(question1.getContent()).isEqualTo(question2.getContent());
            assertThat(question1.getDescription()).isEqualTo(question2.getDescription());
            assertThat(question1.getCommentary()).isEqualTo(question2.getCommentary());
            assertThat(question1.getWriter_id()).isEqualTo(question2.getWriter_id());
            assertThat(question1.getExamples().size()).isEqualTo(question2.getExamples().size());
        }
    }

    @Test
    public void getQuestionTest() {
        // 상세 문제 보기 기능
        // given
        int wrong_id1 = 13; // 작성하지 않은 문제
        int wrong_id2 = 50; // 존재하지 않는 문제
        int question_id = 51; // 작성한 문제
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String wrong_uri1 = url + "/question?id=" + wrong_id1;
        String wrong_uri2 = url + "/question?id=" + wrong_id2;
        String uri = url + "/question?id=" + question_id;
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", question_id);
        paramMap.put("user_id", user_id);

        // when
        ResponseEntity<Question> wrongResponseEntity1 = restTemplate.exchange(wrong_uri1, HttpMethod.GET, httpEntity, Question.class);
        ResponseEntity<Question> wrongResponseEntity2 = restTemplate.exchange(wrong_uri2, HttpMethod.GET, httpEntity, Question.class);
        ResponseEntity<Question> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Question.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Question question1 = questionDao.getQuestion(paramMap);
        Question question2 = responseEntity.getBody();
        assertThat(question1.getId()).isEqualTo(question2.getId());
        assertThat(question1.getContent()).isEqualTo(question2.getContent());
        assertThat(question1.getDescription()).isEqualTo(question2.getDescription());
        assertThat(question1.getCommentary()).isEqualTo(question2.getCommentary());
        assertThat(question1.getWriter_id()).isEqualTo(question2.getWriter_id());
        assertThat(question1.getExamples().size()).isEqualTo(question2.getExamples().size());
    }

    @Test
    public void createQuestionTest() {
        // 문제 생성 기능
        // given
        String uri = url + "/question";

        String content = "문제 제목";
        String description = "문제 설명";
        String commentary = "문제 해설";
        boolean type = true;
        List<Example> exampleList = new ArrayList<>();
        exampleList.add(new Example("보기1", false));
        exampleList.add(new Example("보기2", false));
        exampleList.add(new Example("보기3", true));
        exampleList.add(new Example("보기4", false));
        exampleList.add(new Example("보기5", false));

        // 정상 케이스
        Question question = new Question(content, description, commentary, type, user_id, exampleList);
        HttpEntity<Question> httpEntity = new HttpEntity<>(question, headers);
        // 보기 없을 때
        Question wrongQuestion1 = new Question(content, description, commentary, type, user_id);
        HttpEntity<Question> wrong_httpEntity1 = new HttpEntity<>(wrongQuestion1, headers);

        // when
        ResponseEntity<Question> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Question.class);
        ResponseEntity<Question> wrongResponseEntity1 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity1, Question.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Question> questionList = questionDao.getQuestions(user_id).getQuestion();
        Question question1 = questionList.get(questionList.size()-1);
        Question question2 = responseEntity.getBody();
        assertThat(question1.getId()).isEqualTo(question2.getId());
        assertThat(question1.getContent()).isEqualTo(question2.getContent());
        assertThat(question1.getDescription()).isEqualTo(question2.getDescription());
        assertThat(question1.getCommentary()).isEqualTo(question2.getCommentary());
        assertThat(question1.getWriter_id()).isEqualTo(question2.getWriter_id());
        assertThat(question1.getExamples().size()).isEqualTo(question2.getExamples().size());
        used_pk_list.add(question2.getId());
    }

    @Test
    public void updateQuestionTest() {
        // 문제 수정 기능
        // given

        String uri = url + "/question";

        // 초기 question 생성
        Question test_question = new Question("문제 제목", "문제 설명", "문제 해설", true, user_id);
        questionDao.createQuestion(test_question);
        List<Question> questionList = questionDao.getQuestions(user_id).getQuestion();
        int question_id = questionList.get(questionList.size()-1).getId();
        used_pk_list.add(question_id);
        questionDao.createExample(new Example(question_id,"보기1", false));
        questionDao.createExample(new Example(question_id,"보기2", false));
        questionDao.createExample(new Example(question_id,"보기3", true));
        questionDao.createExample(new Example(question_id,"보기4", false));
        questionDao.createExample(new Example(question_id,"보기5", false));

        int wrong_question_id = question_id + 5;
        String content = "수정된 문제 제목";
        String description = "수정된 문제 설명";
        String commentary = "수정된 문제 해설";
        boolean type = false;
        int wrong_user_id = 2;
        List<Example> exampleList = new ArrayList<>();
        exampleList.add(new Example("수정된 보기1", true));
        exampleList.add(new Example("수정된 보기2", true));
        exampleList.add(new Example("수정된 보기3", false));
        exampleList.add(new Example("수정된 보기4", true));

        // 올바른 케이스
        Question question = new Question(question_id, content, description, commentary, type, user_id, exampleList);
        HttpEntity<Question> httpEntity = new HttpEntity<>(question, headers);
        // 보기 없을 때
        Question wrongQuestion1 = new Question(question_id, content, description, commentary, type, user_id);
        HttpEntity<Question> wrong_httpEntity1 = new HttpEntity<>(wrongQuestion1, headers);

        // pk가 잘못 들어 갔을 때
        Question wrongQuestion3 = new Question(wrong_question_id, content, description, commentary, type, user_id, exampleList);
        HttpEntity<Question> wrong_httpEntity3 = new HttpEntity<>(wrongQuestion3, headers);

        // when
        ResponseEntity<Question> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Question.class);
        ResponseEntity<Question> wrongResponseEntity1 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity1, Question.class);
        ResponseEntity<Question> wrongResponseEntity3 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity3, Question.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Question question1 = questionDao.getQuestionById(question_id);
        Question question2 = responseEntity.getBody();
        assertThat(question1.getId()).isEqualTo(question2.getId()).isEqualTo(question_id);
        assertThat(question1.getContent()).isEqualTo(question2.getContent()).isEqualTo(content);
        assertThat(question1.getDescription()).isEqualTo(question2.getDescription()).isEqualTo(description);
        assertThat(question1.getCommentary()).isEqualTo(question2.getCommentary()).isEqualTo(commentary);
        assertThat(question1.getWriter_id()).isEqualTo(question2.getWriter_id()).isEqualTo(user_id);
        assertThat(question1.getExamples().size()).isEqualTo(question2.getExamples().size()).isEqualTo(exampleList.size());
    }

    @Test
    public void deleteQuestionTest(){
        // 문제 삭제 기능
        // given
        String uri = url + "/question?id=";

        // 초기 question 생성
        Question test_question = new Question("문제 제목", "문제 설명", "문제 해설", true, user_id);
        questionDao.createQuestion(test_question);
        List<Question> questionList = questionDao.getQuestions(user_id).getQuestion();
        int question_id = questionList.get(questionList.size()-1).getId();
        used_pk_list.add(question_id);
        questionDao.createExample(new Example(question_id,"보기1", false));
        questionDao.createExample(new Example(question_id,"보기2", false));
        questionDao.createExample(new Example(question_id,"보기3", true));
        questionDao.createExample(new Example(question_id,"보기4", false));
        questionDao.createExample(new Example(question_id,"보기5", false));
        int wrong_question_id = question_id + 5;

        // 정상 케이스
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // 작성자가 잘못 되었을 때
        User userInfo = userService.login(new User("teacher1", "test"));
        String token = jwtService.create("user", userInfo, "userInfo");
        HttpHeaders wrong_headers = new HttpHeaders();
        wrong_headers.add("access-token", token);
        wrong_headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> wrong_httpEntity1 = new HttpEntity<>(wrong_headers);


        // when
        // 작성자 잘못 되었을 때
        ResponseEntity<Boolean> wrongResponseEntity1 = restTemplate.exchange(uri+question_id, HttpMethod.DELETE, wrong_httpEntity1, Boolean.class);
        // pk 잘못 입력되었을 때
        ResponseEntity<Boolean> wrongResponseEntity2 = restTemplate.exchange(uri+wrong_question_id, HttpMethod.DELETE, httpEntity, Boolean.class);
        // 정상케이스
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(uri+question_id, HttpMethod.DELETE, httpEntity, Boolean.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(wrongResponseEntity1.getBody()).isFalse();
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(wrongResponseEntity2.getBody()).isFalse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isTrue();
        List<Question> result = questionDao.getQuestions(user_id).getQuestion();
        assertThat(result.size()).isEqualTo(questionList.size()-1);
        assertThat(result.get(result.size()-1).getId()).isNotEqualTo(question_id);
    }
}
