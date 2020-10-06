package com.onlinetest.backend.daoTest;

import com.onlinetest.backend.dao.ExamDaoImpl;
import com.onlinetest.backend.dao.UserDaoImpl;
import com.onlinetest.backend.dto.Exam;
import com.onlinetest.backend.dto.swagger.ExamQuestionsSwagger;
import com.onlinetest.backend.dto.swagger.ExamSwagger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamDaoTest {
    // Exam Dao Test

    @Autowired
    ExamDaoImpl examDao;

    @Autowired
    UserDaoImpl userDao;

    @Test
    public void getExamsTest() {
        // 유저가 만든 모든 테스트 가져오는 부분 테스트
        // 없는 유저 일때 null 인지 확인!
        // given
        int wrongUserId = 1;
        int rightUserId = 2;

        // when
        List<ExamSwagger> wrongExamList = examDao.getExams(wrongUserId);
        List<ExamSwagger> rightExamList = examDao.getExams(rightUserId);

        // then
        assertThat(wrongExamList.isEmpty()).isEqualTo(true);
        for (ExamSwagger exam: rightExamList) {
            assertThat(exam.getTeacher_id()).isEqualTo(rightUserId);
            assertThat(exam.getId()).isNotNull();
            assertThat(exam.getStarttime()).isNotNull();
            assertThat(exam.getEndtime()).isNotNull();
            assertThat(exam.getTeacher_id()).isNotNull();
            assertThat(exam.getTeacher_name()).isNotNull();
            assertThat(exam.getName()).isNotNull();

        }
    }

    @Test
    public void getExamTest() {
        // 유저가 만든 모든 테스트 가져오는 부분 테스트
        // 없는 유저 일때 null 인지 확인!
        // given
        int userId = 2;
        int wrongUserId = 1;
        int examId = 8;
        int wrongExamId = 1;

        // 정상 케이스
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", examId);
        paramMap.put("user_id", userId);
        // 유저 pk 잘못 넣었을 때
        Map<String, Integer> wrongParamMap1 = new HashMap<>();
        wrongParamMap1.put("id", examId);
        wrongParamMap1.put("user_id", wrongUserId);
        // 시험 pk 잘못 넣었을 때
        Map<String, Integer> wrongParamMap2 = new HashMap<>();
        wrongParamMap2.put("id", wrongExamId);
        wrongParamMap2.put("user_id", userId);

        // when
        ExamSwagger exam = examDao.getExam(paramMap);
        ExamSwagger wrongExam1 = examDao.getExam(wrongParamMap1);
        ExamSwagger wrongExam2 = examDao.getExam(wrongParamMap2);

        // then
        List<ExamSwagger> examList = examDao.getExams(userId);
        ExamSwagger origin = examList.get(examList.size()-1);
        assertThat(wrongExam1).isNull();
        assertThat(wrongExam2).isNull();
        assertThat(exam.getId()).isEqualTo(origin.getId()).isEqualTo(examId);
        assertThat(exam.getName()).isEqualTo(origin.getName()).isNotNull();
        assertThat(exam.getStarttime()).isEqualTo(origin.getStarttime()).isNotNull();
        assertThat(exam.getEndtime()).isEqualTo(origin.getEndtime()).isNotNull();
        assertThat(exam.getTeacher_name()).isEqualTo(origin.getTeacher_name()).isNotNull();
        assertThat(exam.getTeacher_id()).isEqualTo(origin.getTeacher_id()).isEqualTo(userId);
    }

    @Test
    public void getExamById(){
        // given
        int userId = 2;
        int examId = 8;
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", examId);
        paramMap.put("user_id", userId);
        ExamSwagger origin = examDao.getExam(paramMap);

        // when
        ExamSwagger exam = examDao.getExamById(examId);

        // then
        assertThat(origin.getId()).isEqualTo(exam.getId()).isEqualTo(examId);
        assertThat(origin.getTeacher_id()).isEqualTo(exam.getTeacher_id()).isEqualTo(userId);
        assertThat(origin.getTeacher_name()).isEqualTo(exam.getTeacher_name()).isNotNull();
        assertThat(origin.getStarttime()).isEqualTo(exam.getStarttime()).isNotNull();
        assertThat(origin.getEndtime()).isEqualTo(exam.getEndtime()).isNotNull();
        assertThat(origin.getName()).isEqualTo(exam.getName()).isNotNull();
        assertThat(origin.getQuestions().size()).isEqualTo(exam.getQuestions().size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createExamTest(){
        // given
        String name = "시험 이름";
        LocalDateTime starttime = LocalDateTime.of(2020, 10, 6, 13,0,0);
        LocalDateTime endtime = LocalDateTime.of(2020, 10, 6, 15,0,0);
        int teacher_id = 4;

        // when
        examDao.createExam(new ExamQuestionsSwagger(name, starttime, endtime, teacher_id));

        // then
        List<ExamSwagger> examList = examDao.getExams(teacher_id);

        int len = examList.size();
        ExamSwagger exam = examList.get(len-1);
        assertThat(exam.getName()).isEqualTo(name);
        assertThat(exam.getStarttime()).isEqualTo(starttime);
        assertThat(exam.getEndtime()).isEqualTo(endtime);
        assertThat(exam.getTeacher_id()).isEqualTo(teacher_id);
        assertThat(exam.getTeacher_name()).isEqualTo(userDao.getUserName(teacher_id)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateExamTest(){
        // given
        int teacher_id = 4;
        ExamQuestionsSwagger origin = new ExamQuestionsSwagger( "시험 이름", LocalDateTime.of(2020, 10, 6, 13,0,0),
                        LocalDateTime.of(2020, 10, 6, 15,0,0), teacher_id );
        examDao.createExam(origin);

        String name = "수정된 시험 이름";
        LocalDateTime starttime = LocalDateTime.of(2020, 10, 7, 10,0,0);
        LocalDateTime endtime = LocalDateTime.of(2020, 10, 7, 12,0,0);

        // when
        examDao.updateExam(new ExamQuestionsSwagger(origin.getId(), name, starttime, endtime, teacher_id));

        // then
        ExamSwagger exam = examDao.getExamById(origin.getId());
        assertThat(exam.getName()).isEqualTo(name);
        assertThat(exam.getStarttime()).isEqualTo(starttime);
        assertThat(exam.getEndtime()).isEqualTo(endtime);
        assertThat(exam.getTeacher_id()).isEqualTo(teacher_id);
        assertThat(exam.getTeacher_name()).isEqualTo(userDao.getUserName(teacher_id)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateWrongExamTest(){
        // given
        int teacher_id = 4;
        ExamQuestionsSwagger origin = new ExamQuestionsSwagger( "시험 이름", LocalDateTime.of(2020, 10, 6, 13,0,0),
                LocalDateTime.of(2020, 10, 6, 15,0,0), teacher_id );
        examDao.createExam(origin);

        String name = "수정된 시험 이름";
        LocalDateTime starttime = LocalDateTime.of(2020, 10, 7, 10,0,0);
        LocalDateTime endtime = LocalDateTime.of(2020, 10, 7, 12,0,0);

        // when
        examDao.updateExam(new ExamQuestionsSwagger(origin.getId() + 20, name, starttime, endtime, teacher_id));

        // then
        List<ExamSwagger> examList = examDao.getExams(teacher_id);
        int len = examList.size();
        ExamSwagger exam = examList.get(len-1);
        assertThat(exam.getName()).isNotEqualTo(name).isEqualTo(origin.getName());
        assertThat(exam.getStarttime()).isNotEqualTo(starttime).isEqualTo(origin.getStarttime());
        assertThat(exam.getEndtime()).isNotEqualTo(endtime).isEqualTo(origin.getEndtime());
        assertThat(exam.getTeacher_id()).isEqualTo(teacher_id).isEqualTo(origin.getTeacher_id());
        assertThat(exam.getTeacher_name()).isEqualTo(userDao.getUserName(teacher_id));
    }
}

//    @Test
//    @Transactional
//    @Rollback(true)
//    public void deleteExamTest(){
//        // 문제 삭제 테스트
//        // given
//        int writer_id = 2;
//        int origin_len = questionDao.getQuestions(writer_id).getQuestion().size();
//        int last_id = questionDao.getQuestions(writer_id).getQuestion().get(origin_len-1).getId();
//        // 문제 생성을 기반으로 빌드
//        Question test_question = new Question("문제 제목", "문제 설명", "문제 해설", true, writer_id);
//        questionDao.createQuestion(test_question);
//        int questionId = test_question.getId();
//
//        // when
//        questionDao.deleteQuestion(questionId);
//
//        // then
//        // 문제 pk를 바탕으로 문제 가져오는 부분을 기반으로 테스트 진행
//        List<Question> questionList = questionDao.getQuestions(writer_id).getQuestion();
//        int len = questionList.size();
//        Question question = questionList.get(len-1);
//        assertThat(question.getId()).isNotEqualTo(questionId);
//        assertThat(len).isEqualTo(origin_len);
//        assertThat(question.getId()).isEqualTo(last_id);
//    }
//
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void createExampleTest(){
//        // 보기 생성 테스트
//        // given
//        int writer_id = 2;
//        // 문제 생성을 기반으로 빌드
//        Question test_question = new Question("문제 제목", "문제 설명", "문제 해설", true, writer_id);
//        questionDao.createQuestion(test_question);
//        int question_id = test_question.getId();
//
//        String content1 = "보기1";
//        Boolean correct1 = false;
//        String content2 = "보기2";
//        Boolean correct2 = true;
//
//        // when
//        Example example1 = new Example(test_question.getId(), content1, correct1);
//        Example example2 = new Example(test_question.getId(), content2, correct2);
//        questionDao.createExample(example1);
//        questionDao.createExample(example2);
//
//        // then
//        // 문제 pk를 바탕으로 문제 가져오는 부분을 기반으로 테스트 진행
//        List<Example> exampleList = questionDao.getQuestionById(question_id).getExamples();
//        Example test_example1 = exampleList.get(0);
//        Example test_example2 = exampleList.get(1);
//        assertThat(exampleList.size()).isEqualTo(2);
//        assertThat(test_example1.getQuestion_id()).isEqualTo(question_id);
//        assertThat(test_example1.getContent()).isEqualTo(content1);
//        assertThat(test_example1.getCorrect()).isEqualTo(correct1);
//        assertThat(test_example2.getQuestion_id()).isEqualTo(question_id);
//        assertThat(test_example2.getContent()).isEqualTo(content2);
//        assertThat(test_example2.getCorrect()).isEqualTo(correct2);
//    }
//
//    @Test
//    @Transactional
//    public void deleteQuestionExamTest(){
//        // given
//        int writer_id = 2;
//        // 문제 생성을 기반으로 빌드
//        Question test_question = new Question("문제 제목", "문제 설명", "문제 해설", true, writer_id);
//        questionDao.createQuestion(test_question);
//        int question_id = test_question.getId();
//
//        String content = "보기";
//        Boolean correct = false;
//        for (int i = 1; i < 5; i++) {
//            Example example = new Example(question_id, content.concat(Integer.toString(i)), correct);
//            questionDao.createExample(example);
//        }
//
//
//        // when
//        questionDao.deleteExamples(question_id);
//
//        // then
//        test_question = questionDao.getQuestionById(question_id);
//        assertThat(test_question.getExamples().isEmpty()).isEqualTo(true);
//    }
