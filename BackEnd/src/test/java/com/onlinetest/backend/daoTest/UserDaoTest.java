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
        String email = "test@test.com";
        String password = "test";
        String wrong_email = "admin";
        String wrong_password = "abdfs";

        User right_user = new User(email, password);
        User wrong_user1 = new User(wrong_email, wrong_password);
        User wrong_user2 = new User(email, wrong_password);
        User wrong_user3 = new User(wrong_email, password);

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
        assertThat(right_case.getAuth()).isNotNull();
        assertThat(right_case.getName()).isNotNull();
        assertThat(right_case.getEmail()).isNotNull();
    }

    @Test
    public void idCheckTest(){
        // given
        String email = "test@test.com";
        String wrong_email = "test123@test.com";

        // when
        int right_case = userDao.idCheck(email);
        int wrong_case1 = userDao.idCheck(wrong_email);

        // then
        assertThat(wrong_case1).isEqualTo(0);
        assertThat(right_case).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void signUpTest(){
        // given
        String email = "unitTestCase@onlineTest.com";
        String password = "test";
        int auth = 0;
        String name = "unitTestCase";
        User user = new User(email, password, auth, name);

        // when
        userDao.signUp(user);

        // then
        User loginUser = userDao.login(user);
        assertThat(loginUser.getEmail()).isEqualTo(email);
        assertThat(loginUser.getAuth()).isEqualTo(auth);
        assertThat(loginUser.getName()).isEqualTo(name);
    }

    @Test
    @Rollback
    @Transactional
    public void updatePwdTest(){
        // given
        User user = new User("unitTestCase@onlineTest.com", "test", 0, "unitTestCase");
        userDao.signUp(user);
        String newPwd = "123test123";
        user.setPassword(newPwd);

        // when
        userDao.updatePwd(user);

        // then
        User loginUser = userDao.login(user);
        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(loginUser.getAuth()).isEqualTo(user.getAuth());
        assertThat(loginUser.getName()).isEqualTo(user.getName());
    }
}

