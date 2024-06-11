package ru.relex.park.integration.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.relex.park.annotation.IT;
import ru.relex.park.dto.UserDto;
import ru.relex.park.dto.auth.JwtRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@IT
class AuthControllerTest {

    private ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLoginIfUserExists() throws Exception {
        JwtRequest jwtRequest = JwtRequest.builder()
                .login("admin")
                .password("admin")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("admin"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLoginIfUserDoesNotExist() throws Exception {
        JwtRequest jwtRequest = JwtRequest.builder()
                .login("dummy")
                .password("dummy")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Аутентификация не удалась! Проверьте логин и пароль!"));
    }

    @Test
    void testRegistrationIfUserDoesNotExist() throws Exception {
        var userDto = UserDto.builder()
                .name("test1")
                .login("test1")
                .password("test")
                .build();

        mockMvc.perform(post("/api/v1/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegistrationIfUserExists() throws Exception {
        var userDto = UserDto.builder()
                .name("admin")
                .login("admin")
                .password("test")
                .build();

        mockMvc.perform(post("/api/v1/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("User with login admin already exists"));
    }
}
