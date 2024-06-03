package ru.relex.park.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.relex.park.dao.UserDao;
import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;
import ru.relex.park.dto.auth.JwtResponse;
import ru.relex.park.entity.Role;
import ru.relex.park.entity.User;
import ru.relex.park.mapper.UserMapper;
import ru.relex.park.security.JwtTokenProvider;
import ru.relex.park.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private SecurityServiceImpl securityService;


    @Test
    void testLoginSuccess() {
        JwtRequest jwtRequest = new JwtRequest("testLogin", "testPassword");
        User user = getDefaultUser();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userService.getByLogin(jwtRequest.getLogin()))
                .thenReturn(user);
        when(jwtTokenProvider.generateToken(user.getId(),
                user.getLogin(),
                user.getRole()))
                .thenReturn("testToken");

        JwtResponse result = securityService.login(jwtRequest);

        assertEquals(1, result.getId());
        assertEquals("testLogin", result.getLogin());
        assertEquals("testToken", result.getToken());
    }

    @Test
    void testLoginIfBadCredentials() {
        JwtRequest jwtRequest = new JwtRequest("testLogin", "testPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            securityService.login(jwtRequest);
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    void testRegistrationSuccess() {
        UserDto userDto = getDefaultUserDto();
        User entity = getDefaultUser();
        when(userDao.findByLogin(userDto.getLogin()))
                .thenReturn(Optional.empty());
        when(userMapper.toEntity(userDto))
                .thenReturn(entity);
        when(passwordEncoder.encode(userDto.getPassword()))
                .thenReturn("testPassword");
        when(userDao.save(entity))
                .thenReturn(1);

        Integer result = securityService.registration(userDto);


        assertEquals(1, result);
        verify(userDao).save(entity);
    }


    @Test
    void registration_shouldThrowExceptionIfUserAlreadyExists() {
        UserDto userDto = getDefaultUserDto();
        when(userDao.findByLogin(userDto.getLogin()))
                .thenReturn(Optional.of(new User()));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            securityService.registration(userDto);
        });

        assertEquals("User with login testLogin already exists", exception.getMessage());
    }

    private UserDto getDefaultUserDto() {
        return UserDto.builder()
                .name("FirstName")
                .login("testLogin")
                .password("testPassword")
                .build();
    }

    private User getDefaultUser() {
        return User.builder()
                .id(1)
                .name("FirstName")
                .password("testPassword")
                .login("testLogin")
                .role(Role.ROLE_DRIVER)
                .build();
    }
}