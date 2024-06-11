package ru.relex.park.integration.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.relex.park.annotation.IT;
import ru.relex.park.dao.UserDao;
import ru.relex.park.entity.Role;
import ru.relex.park.entity.User;

import java.util.Optional;


@RequiredArgsConstructor
@IT
class UserDaoTest {

    private final UserDao userDao;

    @Test
    void saveUserShouldReturnGeneratedId() {
        var testUser = getTestUser();
        Integer generatedId = userDao.save(testUser);
        Assertions.assertNotNull(generatedId);

        User savedUser = userDao.findById(generatedId).get();

        Assertions.assertEquals(testUser.getName(), savedUser.getName());
        Assertions.assertEquals(testUser.getLogin(), savedUser.getLogin());
        Assertions.assertEquals(testUser.getPassword(), savedUser.getPassword());
        Assertions.assertEquals(testUser.getRole(), savedUser.getRole());
    }

    @Test
    void findByLoginShouldReturnUser() {
        var testUser = getTestUser();
        Integer generatedId = userDao.save(testUser);
        Assertions.assertNotNull(generatedId);

        Optional<User> foundUserOpt = userDao.findByLogin(testUser.getLogin());
        Assertions.assertTrue(foundUserOpt.isPresent());

        User foundUser = foundUserOpt.get();
        Assertions.assertEquals(testUser.getName(), foundUser.getName());
        Assertions.assertEquals(testUser.getLogin(), foundUser.getLogin());
        Assertions.assertEquals(testUser.getPassword(), foundUser.getPassword());
        Assertions.assertEquals(testUser.getRole(), foundUser.getRole());
    }

    @Test
    void findByLoginShouldEmptyOptional() {
        var testUser = getTestUser();
        Integer generatedId = userDao.save(testUser);
        Assertions.assertNotNull(generatedId);

        Optional<User> foundUserOpt = userDao.findByLogin(testUser.getLogin() + "dummy");
        Assertions.assertTrue(foundUserOpt.isEmpty());
    }

    private static User getTestUser() {
        return User.builder()
                .name("Test User")
                .login("testLogin")
                .password("testPassword")
                .role(Role.ROLE_DRIVER)
                .build();
    }
}