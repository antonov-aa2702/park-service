package ru.relex.park.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import ru.relex.park.annotation.IT;
import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;
import ru.relex.park.dto.auth.JwtResponse;
import ru.relex.park.service.SecurityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor
@IT
class SecurityServiceTest {

    private final SecurityService securityService;

    @Test
    void testLoginIfUserExists() {
        JwtRequest jwtRequest = JwtRequest.builder()
                .login("admin")
                .password("admin")
                .build();

        JwtResponse actualResult = assertDoesNotThrow(() -> securityService.login(jwtRequest));

        assertThat(actualResult)
                .isNotNull();
        assertThat(actualResult.getId())
                .isEqualTo(1);
        assertThat(actualResult.getLogin())
                .isEqualTo("admin");
        assertThat(actualResult.getToken())
                .isNotNull();
    }

    @Test
    void testLoginIfUserDoesNotExist() {
        JwtRequest jwtRequest = JwtRequest.builder()
                .login("dummy")
                .password("dummy")
                .build();

        assertThatExceptionOfType(InternalAuthenticationServiceException.class)
                .isThrownBy(() -> securityService
                        .login(jwtRequest))
                .withMessage("Пользователь не найден");
    }

    @Test
    void testLoginFailIfPasswordIncorrect() {
        JwtRequest jwtRequest = JwtRequest.builder()
                .login("admin")
                .password("dummy")
                .build();

        assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> securityService
                        .login(jwtRequest))
                .withMessage("Неверные учетные данные пользователя");
    }


    @Test
    void testRegistrationIfUserDoesNotExist() {
        UserDto userDto = UserDto.builder()
                .name("Test User")
                .login("testLogin")
                .password("testPassword")
                .build();
        Integer actualResult = assertDoesNotThrow(() -> securityService.registration(userDto));

        assertNotNull(actualResult);
    }

    @Test
    void testRegistrationIfUserExist() {
        UserDto userDto = UserDto.builder()
                .name("admin")
                .login("admin")
                .password("admin")
                .build();

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> securityService.registration(userDto));
        assertThat(exception.getMessage())
                .isEqualTo("User with login admin already exists");
    }
}
