package ru.relex.park.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping(value = "/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return securityService.login(jwtRequest);
    }

    @PostMapping(value = "/registration")
    public Integer registration(@Validated @RequestBody UserDto userDto) {
        return securityService.registration(userDto);
    }
}
