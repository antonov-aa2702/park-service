package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.relex.park.dao.UserDao;
import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;
import ru.relex.park.dto.auth.JwtResponse;
import ru.relex.park.entity.Role;
import ru.relex.park.entity.User;
import ru.relex.park.mapper.UserMapper;
import ru.relex.park.security.JwtTokenProvider;
import ru.relex.park.service.SecurityService;
import ru.relex.park.service.UserService;

@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jwtRequest.getLogin(),
                jwtRequest.getPassword()));
        User user = userService.getByLogin(jwtRequest.getLogin());

        return JwtResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .token(jwtTokenProvider.generateToken(user.getId(), user.getLogin(), user.getRole()))
                .build();
    }

    @Override
    public Integer registration(UserDto userDto) {
        var userOpt = userDao.findByLogin(userDto.getLogin());

        if (userOpt.isPresent()) {
            throw new IllegalStateException("User with login " + userDto.getLogin() + " already exists");
        }

        var entity = userMapper.toEntity(userDto);
        entity.setRole(Role.ROLE_DRIVER);
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDao.save(entity);
    }
}
