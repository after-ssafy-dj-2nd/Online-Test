package com.onlinetest.backend.controllerTest;

import com.onlinetest.backend.dao.UserDaoImpl;
import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @LocalServerPort
    private final int port = 8080;

    String url = "http://localhost:" + port + "/api";

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers;
    List<Integer> used_pk_list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void idCheckTest() throws Exception{
        // given
        String user_id = "test";
        String wrong_user_id = "testtesttesttest";
        String uri = url + "/idcheck?user_id=";
        HttpEntity<String> httpEntity1 = new HttpEntity<>(headers);
        HttpEntity<String> httpEntity2 = new HttpEntity<>(headers);

        // when
        ResponseEntity<Boolean> responseEntity1 = restTemplate.exchange(uri+user_id, HttpMethod.GET, httpEntity1, Boolean.class);
        ResponseEntity<Boolean> responseEntity2 = restTemplate.exchange(uri+wrong_user_id, HttpMethod.GET, httpEntity2, Boolean.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getBody()).isEqualTo(false);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getBody()).isEqualTo(true);
    }

    @Test
    public void logInTest() throws Exception{
        // given
        String uri = url + "/login";
        String user_id = "test";
        String password = "test";
        String wrong_user_id = "testtesttest";
        String wrong_password = "testtestest";

        User user1 = new User(user_id, password);
        User user2 = new User(wrong_user_id, password);
        User user3 = new User(user_id, wrong_password);
        HttpEntity<User> httpEntity1 = new HttpEntity<>(user1, headers);
        HttpEntity<User> httpEntity2 = new HttpEntity<>(user2, headers);
        HttpEntity<User> httpEntity3 = new HttpEntity<>(user3, headers);

        // when
        ResponseEntity<Map> responseEntity1 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity1, Map.class);
        ResponseEntity<Map> responseEntity2 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity2, Map.class);
        ResponseEntity<Map> responseEntity3 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity3, Map.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> result = responseEntity1.getBody();
        assertThat(responseEntity1.getHeaders().get("access-token")).isNotNull();
        assertThat(result.get("login")).isEqualTo(true);
        assertThat(result.get("userInfo")).isNotNull();
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
        result = responseEntity2.getBody();
        assertThat(result.get("login")).isEqualTo(false);
        assertThat(result.get("userInfo")).isNull();
        assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.OK);
        result = responseEntity3.getBody();
        assertThat(result.get("login")).isEqualTo(false);
        assertThat(result.get("userInfo")).isNull();
    }

    @Test
    @Rollback
    public void signUpTest() throws Exception{
        // given
        String uri = url + "/signup";
        String user_id = "test123";
        String wrong_user_id = "test";
        String password = "test";
        int auth = 0;
        String name = "test123";
        String email = "test@test.com";

        User user1 = new User(user_id, password, auth, name, email);
        User user2 = new User(wrong_user_id, password, auth, name, email);
        HttpEntity<User> httpEntity1 = new HttpEntity<>(user1, headers);
        HttpEntity<User> httpEntity2 = new HttpEntity<>(user2, headers);

        // when
        ResponseEntity<Boolean> responseEntity1 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity1, Boolean.class);
        ResponseEntity<Boolean> responseEntity2 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity2, Boolean.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getBody()).isEqualTo(true);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity2.getBody()).isEqualTo(false);
    }

    @Test
    public void findPwdTest(){
        // given
        String uri = url + "/findpwd";
        String user_id = "test123";
        String wrong_user_id = "test1234";
//        String password = "test";
//        int auth = 0;
        String name = "test123";
        String email = "test123@test.com";
        String wrong_email = "test111@test.com";
//        userDao.signUp(new User(user_id, password, auth, name, email));

        User user1 = new User();
        user1.setUser_id(user_id);
        user1.setEmail(email);
        User user2 = new User();
        user2.setUser_id(user_id);
        user2.setEmail(wrong_email);
        User user3 = new User();
        user3.setUser_id(wrong_user_id);
        user3.setEmail(email);
        HttpEntity<User> httpEntity1 = new HttpEntity<>(user1, headers);
        HttpEntity<User> httpEntity2 = new HttpEntity<>(user2, headers);
        HttpEntity<User> httpEntity3 = new HttpEntity<>(user3, headers);

        // when
        ResponseEntity<Map> responseEntity1 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity1, Map.class);
        ResponseEntity<Map> responseEntity2 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity2, Map.class);
        ResponseEntity<Map> responseEntity3 = restTemplate.exchange(uri, HttpMethod.POST, httpEntity3, Map.class);

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getBody().get("status")).isEqualTo(true);
        assertThat(responseEntity1.getBody().get("resultMsg")).isEqualTo("귀하의 이메일 주소로 새로운 임시 비밀번호를 발송 하였습니다.");
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity2.getBody().get("status")).isEqualTo(false);
        assertThat(responseEntity2.getBody().get("resultMsg")).isEqualTo("입력하신 이메일의 회원정보와 가입된 아이디가 일치하지 않습니다.");
        assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity3.getBody().get("status")).isEqualTo(false);
        assertThat(responseEntity3.getBody().get("resultMsg")).isEqualTo("귀하의 이메일로 가입된 아이디가 존재하지 않습니다.");
    }
}