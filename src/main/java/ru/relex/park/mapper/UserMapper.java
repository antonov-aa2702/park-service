package ru.relex.park.mapper;

import org.springframework.stereotype.Component;
import ru.relex.park.dto.UserDto;
import ru.relex.park.entity.User;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User toEntity(UserDto from) {
        return User.builder()
                .id(from.getId())
                .name(from.getName())
                .login(from.getLogin())
                .password(from.getPassword())
                .build();
    }

    @Override
    public UserDto toDto(User from) {
        return null;
    }
}
