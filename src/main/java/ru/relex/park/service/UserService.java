package ru.relex.park.service;

import ru.relex.park.entity.User;

public interface UserService {

    User getByLogin(String username);
}
