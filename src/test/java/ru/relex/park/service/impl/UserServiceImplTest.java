package ru.relex.park.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.relex.park.dao.UserDao;
import ru.relex.park.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testGetByLoginShouldReturnUser() {
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setLogin(email);
        when(userDao.findByLogin(email))
                .thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getByLogin(email);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testGetByLoginShouldThrowExceptionIfUserNotFound() {
        String email = "test@example.com";
        when(userDao.findByLogin(email)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.getByLogin(email);
        });

        assertEquals("Пользователь не найден", exception.getMessage());
    }
}