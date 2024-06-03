package ru.relex.park.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.relex.park.security.JwtEntity;

@Component
public class UserCredentials {

    public JwtEntity getUser() {
        return (JwtEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    public Integer getUserId() {
        return getUser().getId();
    }
}

