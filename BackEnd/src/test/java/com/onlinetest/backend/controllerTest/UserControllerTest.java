package com.onlinetest.backend.controllerTest;

import com.onlinetest.backend.dto.User;
import com.onlinetest.backend.service.IJwtService;
import com.onlinetest.backend.service.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @LocalServerPort
    private final int port = 8080;

    String url = "http://localhost:" + port + "/api";

    @Autowired
    private IUserService userService;

    @Autowired
    private IJwtService jwtService;

    List<Integer> used_pk_list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void logInTest() throws Exception{

    }
}