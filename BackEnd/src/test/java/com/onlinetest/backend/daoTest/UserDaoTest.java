package com.onlinetest.backend.daoTest;

import com.onlinetest.backend.dao.UserDaoImpl;
import com.onlinetest.backend.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDaoImpl userDao;

    @Test
    public void logInTest(){
        // given
        String user_id = "test";
        String password = "test";
        String wrong_user_id = "admin";
        String wrong_password = "abdfs";

        User right_user = new User(user_id, password);
        User wrong_user1 = new User(wrong_user_id, wrong_password);
        User wrong_user2 = new User(user_id, wrong_password);
        User wrong_user3 = new User(wrong_user_id, password);

        // when
        User right_case = userDao.login(right_user);
        User wrong_case1 = userDao.login(wrong_user1);
        User wrong_case2 = userDao.login(wrong_user2);
        User wrong_case3 = userDao.login(wrong_user3);

        // then
        assertThat(wrong_case1).isNull();
        assertThat(wrong_case2).isNull();
        assertThat(wrong_case3).isNull();
        assertThat(right_case.getId()).isNotNull();
        assertThat(right_case.getUser_id()).isEqualTo(user_id);
        assertThat(right_case.getAuth()).isNotNull();
        assertThat(right_case.getName()).isNotNull();
        assertThat(right_case.getEmail()).isNotNull();
    }

    @Test
    public void idCheckTest(){
        // given
        String user_id = "test";
        String wrong_user_id = "test123";

        // when
        int right_case = userDao.idCheck(user_id);
        int wrong_case1 = userDao.idCheck(wrong_user_id);

        // then
        assertThat(wrong_case1).isEqualTo(0);
        assertThat(right_case).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void signUpTest(){
        // given
        String user_id = "unitTestCase";
        String password = "test";
        int auth = 0;
        String name = "unitTestCase";
        String email = "unitTestCase@onlineTest.com";
        User user = new User(user_id, password, auth, name, email);

        // when
        userDao.signUp(user);

        // then
        User loginUser = userDao.login(user);
        assertThat(loginUser.getUser_id()).isEqualTo(user_id);
        assertThat(loginUser.getAuth()).isEqualTo(auth);
        assertThat(loginUser.getName()).isEqualTo(name);
        assertThat(loginUser.getEmail()).isEqualTo(email);
    }

    @Test
    @Rollback
    public void getEmailTest(){
        // given
        User user = new User("unitTestCase", "test", 0, "unitTestCase", "unitTestCase@onlineTest.com");
        userDao.signUp(user);

        // when
        String email = userDao.getEmail(user.getUser_id());

        // then
        assertThat(email).isEqualTo(user.getEmail());
    }

    @Test
    @Rollback
    @Transactional
    public void updatePwdTest(){
        // given
        User user = new User("unitTestCase", "test", 0, "unitTestCase", "unitTestCase@onlineTest.com");
        userDao.signUp(user);
        String newPwd = "123test123";
        user.setPassword(newPwd);

        // when
        userDao.updatePwd(user);

        // then
        User loginUser = userDao.login(user);
        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getUser_id()).isEqualTo(user.getUser_id());
        assertThat(loginUser.getAuth()).isEqualTo(user.getAuth());
        assertThat(loginUser.getName()).isEqualTo(user.getName());
        assertThat(loginUser.getEmail()).isEqualTo(user.getEmail());


    }

//    @Test
//    @Rollback
//    public void getUserName(){
//        // given
//        User user = new User("unitTestCase", "test", 0, "unitTestCase", "unitTestCase@onlineTest.com");
//        userDao.signUp(user);
//
//        // when
//        String name = userDao.getUserName(user.getId());
//
//        // then
//        assertThat(name).isEqualTo(user.getName());
//    }
}

