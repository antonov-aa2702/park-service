package ru.relex.park.service;

import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;
import ru.relex.park.dto.auth.JwtResponse;

public interface SecurityService {

    JwtResponse login(JwtRequest jwtRequest);

    Integer registration(UserDto userDto);
}
