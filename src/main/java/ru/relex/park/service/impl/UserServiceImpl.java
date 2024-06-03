package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.relex.park.dao.UserDao;
import ru.relex.park.entity.User;
import ru.relex.park.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public User getByLogin(String email) {
        return userDao.findByLogin(email)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));
    }
}
