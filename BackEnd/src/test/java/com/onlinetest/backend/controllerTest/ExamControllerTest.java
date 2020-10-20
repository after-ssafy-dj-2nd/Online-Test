package com.onlinetest.backend.controllerTest;

import com.onlinetest.backend.controller.ExamController;
import com.onlinetest.backend.dao.ExamDaoImpl;
import com.onlinetest.backend.dao.QuestionDaoImpl;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.QuestionExam;
import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.dto.swagger.ExamListSwagger;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import com.onlinetest.backend.dto.swagger.ExamSwagger;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ExamControllerTest {

    @LocalServerPort
    private final int port = 8080;

    String url = "http://localhost:" + port + "/api";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExamController examController;

    @Autowired
    private ExamDaoImpl examDao;

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
        User userInfo = userService.login(new User("test", "test"));
        String token = jwtService.create("user", userInfo, "userInfo");
        headers = new HttpHeaders();
        headers.add("access-token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @After
    public void clean() throws Exception {
        for (int pk:used_pk_list) {
            examDao.deleteQuestionExam(pk);
            examDao.deleteExam(pk);
        }
        used_pk_list = new ArrayList<>();
    }

    @Test
    public void getExamsTest() {
        // 모든 시험 보기 기능
        // given
        String uri = url + "/exams";
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // when
        ResponseEntity<ExamListSwagger> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExamListSwagger.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExamSwagger> examList1 = examDao.getExams(user_id);
        List<ExamSwagger> examList2 = responseEntity.getBody().getExams();
        int len = examList1.size();
        assertThat(examList2.size()).isEqualTo(len);
        for (int i=0; i < len; i++){
            ExamSwagger exam1 = examList1.get(i);
            ExamSwagger exam2 = examList2.get(i);
            assertThat(exam1.getId()).isEqualTo(exam2.getId());
            assertThat(exam1.getStarttime()).isEqualTo(exam2.getStarttime());
            assertThat(exam1.getEndtime()).isEqualTo(exam2.getEndtime());
            assertThat(exam1.getTeacher_name()).isEqualTo(exam2.getTeacher_name());
            assertThat(exam1.getName()).isEqualTo(exam2.getName());
            assertThat(exam1.getQuestions().size()).isEqualTo(exam2.getQuestions().size());
        }
    }

    @Test
    public void getExamTest() {
        // 상세 시험 보기 기능
        // given
        int wrong_id1 = 1; // 작성하지 않은 시험
        int wrong_id2 = 3; // 존재하지 않는 시험
        int exam_id = 9; // 작성한 시험
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String wrong_uri1 = url + "/exam?id=" + wrong_id1;
        String wrong_uri2 = url + "/exam?id=" + wrong_id2;
        String uri = url + "/exam?id=" + exam_id;
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", exam_id);
        paramMap.put("user_id", user_id);
        ExamSwagger origin = examDao.getExam(paramMap);

        // when
        ResponseEntity<ExamSwagger> wrongResponseEntity1 = restTemplate.exchange(wrong_uri1, HttpMethod.GET, httpEntity, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity2 = restTemplate.exchange(wrong_uri2, HttpMethod.GET, httpEntity, ExamSwagger.class);
        ResponseEntity<ExamSwagger> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExamSwagger.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ExamSwagger exam = responseEntity.getBody();
        assertThat(exam.getId()).isEqualTo(origin.getId());
        assertThat(exam.getName()).isEqualTo(origin.getName());
        assertThat(exam.getStarttime()).isEqualTo(origin.getStarttime());
        assertThat(exam.getEndtime()).isEqualTo(origin.getEndtime());
        assertThat(exam.getTeacher_id()).isEqualTo(origin.getTeacher_id());
        assertThat(exam.getTeacher_name()).isEqualTo(origin.getTeacher_name());
        assertThat(exam.getQuestions().size()).isEqualTo(exam.getQuestions().size());
    }

    @Test
    public void createExamTest() {
        // 시험 생성 기능
        // given
        String uri = url + "/exam";

        String name = "문제 제목";
        LocalDateTime starttime = LocalDateTime.of(2020, 10, 6, 13,0,0);
        LocalDateTime endtime = LocalDateTime.of(2020, 10, 6, 15,0,0);

        int teacher_id = 4;


        int question_id = 74;
        int wrong_question_id1 = 18;
        int wrong_question_id2 = 19;
        int score = 5;

        // 정상 시험
        Exam exam = new Exam(name, starttime, endtime, teacher_id);
        // 접속한 사람과 시험 내는사람이 다른 경우

        // 시험의 이름이 없는 경우
        Exam wrong_exam2 = new Exam(starttime, endtime, teacher_id);
        // 시험의 시작시간이 없는 경우
        Exam wrong_exam3 = new Exam(name, endtime, teacher_id);
        // 시험의 종료시간이 없는 경우
        Exam wrong_exam4 = new Exam(name, starttime, teacher_id);

        // 정상적인 시험-문제
        List<QuestionExam> questionExamTable = new ArrayList<>();
        questionExamTable.add(new QuestionExam(question_id, score));
        // 점수가 없는 시험-문제
        List<QuestionExam> wrong_questionExamTable1 = new ArrayList<>();
        wrong_questionExamTable1.add(new QuestionExam(question_id));
        // 문제 출제자와 시험 만든이가 다른 시험-문제
        List<QuestionExam> wrong_questionExamTable2 = new ArrayList<>();
        wrong_questionExamTable2.add(new QuestionExam(wrong_question_id1, score));
        // 문제가 없는 시험-문제
        List<QuestionExam> wrong_questionExamTable3 = new ArrayList<>();
        wrong_questionExamTable3.add(new QuestionExam(wrong_question_id2, score));

        // 정상 케이스
        ExamQuestionsSwagger right_case = new ExamQuestionsSwagger(exam, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> httpEntity = new HttpEntity<>(right_case, headers);
        // 점수가 없는 케이스
        ExamQuestionsSwagger wrong_case1 = new ExamQuestionsSwagger(exam, wrong_questionExamTable1);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity1 = new HttpEntity<>(wrong_case1, headers);
        // 문제 출제자와 시험 만든이가 다른 케이스
        ExamQuestionsSwagger wrong_case2 = new ExamQuestionsSwagger(exam, wrong_questionExamTable2);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity2 = new HttpEntity<>(wrong_case2, headers);
        // 문제가 없는 케이스
        ExamQuestionsSwagger wrong_case3 = new ExamQuestionsSwagger(exam, wrong_questionExamTable3);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity3 = new HttpEntity<>(wrong_case3, headers);


        // 시험의 이름이 없는 경우
        ExamQuestionsSwagger wrong_case5 = new ExamQuestionsSwagger(wrong_exam2, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity5 = new HttpEntity<>(wrong_case5, headers);
        // 시험의 시작시간이 없는 경우
        ExamQuestionsSwagger wrong_case6 = new ExamQuestionsSwagger(wrong_exam3, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity6 = new HttpEntity<>(wrong_case6, headers);
        // 시험의 종료시간이 없는 경우
        ExamQuestionsSwagger wrong_case7 = new ExamQuestionsSwagger(wrong_exam4, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity7 = new HttpEntity<>(wrong_case7, headers);

        // when
        ResponseEntity<ExamSwagger> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ExamSwagger.class);
        used_pk_list.add(responseEntity.getBody().getId());
        ResponseEntity<ExamSwagger> wrongResponseEntity1 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity1, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity2 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity2, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity3 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity3, ExamSwagger.class);

        ResponseEntity<ExamSwagger> wrongResponseEntity5 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity5, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity6 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity6, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity7 = restTemplate.exchange(uri, HttpMethod.POST, wrong_httpEntity7, ExamSwagger.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(wrongResponseEntity3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity5.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity6.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity7.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExamSwagger> examList = examDao.getExams(teacher_id);
        ExamSwagger exam1 = examList.get(examList.size()-1);
        ExamSwagger exam2 = responseEntity.getBody();
        assertThat(exam1.getId()).isEqualTo(exam2.getId());
        assertThat(exam1.getName()).isEqualTo(exam2.getName());
        assertThat(exam1.getStarttime()).isEqualTo(exam2.getStarttime());
        assertThat(exam1.getEndtime()).isEqualTo(exam2.getEndtime());
        assertThat(exam1.getTeacher_id()).isEqualTo(exam2.getTeacher_id());
        assertThat(exam1.getTeacher_name()).isEqualTo(exam2.getTeacher_name());
        assertThat(exam1.getQuestions().size()).isEqualTo(exam2.getQuestions().size());
        for (int i = 0; i < exam1.getQuestions().size(); i++) {
            assertThat(exam1.getQuestions().get(i).getId()).isEqualTo(exam2.getQuestions().get(i).getId());
        }
    }

    @Test
    public void updateExamTest(){
        // given
        String uri = url + "/exam";

        int teacher_id = 4;

        List<QuestionExam> originQETable = new ArrayList<>();
        examDao.createExam(new ExamQuestionsSwagger(
                new Exam(
                        "문제 제목",
                        LocalDateTime.of(2020, 10, 6, 13, 0,0),
                        LocalDateTime.of(2020, 10, 6, 15,0,0),
                        teacher_id
                ))
        );
        List<ExamSwagger> originExamList = examDao.getExams(teacher_id);
        ExamSwagger origin = originExamList.get(originExamList.size()-1);
        int exam_id = originExamList.get(originExamList.size()-1).getId();
        examDao.createQuestionExam(new QuestionExam(exam_id, 74, 5));
        used_pk_list.add(exam_id);
        String name = "수정된 문제 제목";
        LocalDateTime starttime = LocalDateTime.of(2020, 10, 8, 13,0,0);
        LocalDateTime endtime = LocalDateTime.of(2020, 10, 8, 15,0,0);
        int wrong_teacher_id = 2;
        int question_id = 51;
        int wrong_question_id1 = 18;
        int wrong_question_id2 = 19;
        int score = 5;

        // 정상 시험
        Exam exam = new Exam(exam_id, name, starttime, endtime, teacher_id);

        // 시험의 이름이 없는 경우
        Exam wrong_exam2 = new Exam(exam_id, starttime, endtime, teacher_id);
        // 시험의 시작시간이 없는 경우
        Exam wrong_exam3 = new Exam(exam_id, name, endtime, teacher_id);
        // 시험의 종료시간이 없는 경우
        Exam wrong_exam4 = new Exam(exam_id, name, starttime, teacher_id);
        // 시험의 아이디가 없는 경우
        Exam wrong_exam5 = new Exam(name, starttime, endtime, teacher_id);

        // 정상적인 시험-문제
        List<QuestionExam> questionExamTable = new ArrayList<>();
        questionExamTable.add(new QuestionExam(question_id, score));
        // 점수가 없는 시험-문제
        List<QuestionExam> wrong_questionExamTable1 = new ArrayList<>();
        wrong_questionExamTable1.add(new QuestionExam(question_id));
        // 문제 출제자와 시험 만든이가 다른 시험-문제
        List<QuestionExam> wrong_questionExamTable2 = new ArrayList<>();
        wrong_questionExamTable2.add(new QuestionExam(wrong_question_id1, score));
        // 문제가 없는 시험-문제
        List<QuestionExam> wrong_questionExamTable3 = new ArrayList<>();
        wrong_questionExamTable3.add(new QuestionExam(wrong_question_id2, score));

        // 정상 케이스
        ExamQuestionsSwagger right_case = new ExamQuestionsSwagger(exam, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> httpEntity = new HttpEntity<>(right_case, headers);
        // 점수가 없는 케이스
        ExamQuestionsSwagger wrong_case1 = new ExamQuestionsSwagger(exam, wrong_questionExamTable1);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity1 = new HttpEntity<>(wrong_case1, headers);
        // 문제 출제자와 시험 만든이가 다른 케이스
        ExamQuestionsSwagger wrong_case2 = new ExamQuestionsSwagger(exam, wrong_questionExamTable2);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity2 = new HttpEntity<>(wrong_case2, headers);
        // 문제가 없는 케이스
        ExamQuestionsSwagger wrong_case3 = new ExamQuestionsSwagger(exam, wrong_questionExamTable3);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity3 = new HttpEntity<>(wrong_case3, headers);

        // 시험의 이름이 없는 경우
        ExamQuestionsSwagger wrong_case5 = new ExamQuestionsSwagger(wrong_exam2, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity5 = new HttpEntity<>(wrong_case5, headers);
        // 시험의 시작시간이 없는 경우
        ExamQuestionsSwagger wrong_case6 = new ExamQuestionsSwagger(wrong_exam3, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity6 = new HttpEntity<>(wrong_case6, headers);
        // 시험의 종료시간이 없는 경우
        ExamQuestionsSwagger wrong_case7 = new ExamQuestionsSwagger(wrong_exam4, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity7 = new HttpEntity<>(wrong_case7, headers);
        // 시험의 아이디값이 없는 경우
        ExamQuestionsSwagger wrong_case8 = new ExamQuestionsSwagger(wrong_exam5, questionExamTable);
        HttpEntity<ExamQuestionsSwagger> wrong_httpEntity8 = new HttpEntity<>(wrong_case8, headers);

        // when
        ResponseEntity<ExamSwagger> wrongResponseEntity1 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity1, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity2 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity2, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity3 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity3, ExamSwagger.class);

        ResponseEntity<ExamSwagger> wrongResponseEntity5 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity5, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity6 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity6, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity7 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity7, ExamSwagger.class);
        ResponseEntity<ExamSwagger> wrongResponseEntity8 = restTemplate.exchange(uri, HttpMethod.PUT, wrong_httpEntity8, ExamSwagger.class);
        ResponseEntity<ExamSwagger> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, ExamSwagger.class);

        // then
        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(wrongResponseEntity3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(wrongResponseEntity5.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity6.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity7.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(wrongResponseEntity8.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExamSwagger> examList = examDao.getExams(teacher_id);
        ExamSwagger exam1 = examList.get(examList.size()-1);
        ExamSwagger exam2 = responseEntity.getBody();
        assertThat(exam1.getId()).isEqualTo(exam2.getId()).isEqualTo(exam_id);
        assertThat(exam1.getName()).isEqualTo(exam2.getName()).isNotEqualTo(origin.getName());
        assertThat(exam1.getStarttime()).isEqualTo(exam2.getStarttime()).isNotEqualTo(origin.getStarttime());
        assertThat(exam1.getEndtime()).isEqualTo(exam2.getEndtime()).isNotEqualTo(origin.getEndtime());
        assertThat(exam1.getTeacher_id()).isEqualTo(exam2.getTeacher_id()).isEqualTo(origin.getTeacher_id());
        assertThat(exam1.getTeacher_name()).isEqualTo(exam2.getTeacher_name()).isEqualTo(origin.getTeacher_name());
        assertThat(exam1.getQuestions().size()).isEqualTo(exam2.getQuestions().size());
        for (int i = 0; i < exam1.getQuestions().size(); i++) {
            assertThat(exam1.getQuestions().get(i).getId()).isEqualTo(exam2.getQuestions().get(i).getId());
        }
    }

    @Test
    public void deleteExamTest(){
        String uri = url + "/exam?id=";
        List<QuestionExam> originQETable = new ArrayList<>();
        int teacher_id = 4;
        examDao.createExam(new ExamQuestionsSwagger(
                new Exam(
                        "문제 제목",
                        LocalDateTime.of(2020, 10, 6, 13, 0,0),
                        LocalDateTime.of(2020, 10, 6, 15,0,0),
                        teacher_id
                ))
        );
        List<ExamSwagger> originExamList = examDao.getExams(teacher_id);
        ExamSwagger origin = originExamList.get(originExamList.size()-1);
        int exam_id = originExamList.get(originExamList.size()-1).getId();
        examDao.createQuestionExam(new QuestionExam(exam_id, 74, 5));
        used_pk_list.add(exam_id);
        int wrongExam_id1 = 8;
        int wrongExam_id2 = 51;
        HttpEntity<ExamQuestionsSwagger> httpEntity = new HttpEntity<>(headers);

        // then
        // 작성자 잘못 되었을 때
        ResponseEntity<Boolean> wrongResponseEntity1 = restTemplate.exchange(uri+wrongExam_id1, HttpMethod.DELETE, httpEntity, Boolean.class);
        // 없는문제 입력되었을 때
        ResponseEntity<Boolean> wrongResponseEntity2 = restTemplate.exchange(uri+wrongExam_id2, HttpMethod.DELETE, httpEntity, Boolean.class);
        // 정상케이스
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(uri+exam_id, HttpMethod.DELETE, httpEntity, Boolean.class);


        assertThat(wrongResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(wrongResponseEntity1.getBody()).isFalse();
        assertThat(wrongResponseEntity2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(wrongResponseEntity2.getBody()).isFalse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isTrue();
        List<ExamSwagger> result = examDao.getExams(user_id);
        assertThat(result.size()).isEqualTo(originExamList.size()-1);
        assertThat(result.get(result.size()-1).getId()).isNotEqualTo(exam_id);
    }
}
