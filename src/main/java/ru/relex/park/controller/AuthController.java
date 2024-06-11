package ru.relex.park.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;
import ru.relex.park.dto.auth.JwtResponse;
import ru.relex.park.service.SecurityService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    /**
     * Аутентификация пользователя
     *
     * @param jwtRequest данные для аутентификации
     * @return токен
     */
    @PostMapping(value = "/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return securityService.login(jwtRequest);
    }

    /**
     * Регистрация пользователя. Только пользователь с ролью "USER" может зарегистрироваться.
     *
     * @param userDto данные для регистрации
     * @return идентификатор регистрируемого пользователя
     */
    @PostMapping(value = "/registration")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Integer registration(@Validated @RequestBody UserDto userDto) {
        return securityService.registration(userDto);
    }
}
