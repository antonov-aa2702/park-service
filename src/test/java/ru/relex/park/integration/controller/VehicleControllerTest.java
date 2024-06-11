package ru.relex.park.integration.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.relex.park.annotation.IT;
import ru.relex.park.dto.VehicleDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
@AutoConfigureMockMvc
@IT
class VehicleControllerTest {

    private final MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @WithMockUser(
            username = "admin",
            password = "admin",
            roles = "ADMIN")
    @Test
    void createSuccess() throws Exception {
        var vehicleDto = VehicleDto.builder()
                .name("test")
                .year(2020)
                .build();

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto)))
                .andExpect(status().isCreated());
    }


    @WithMockUser(
            username = "admin",
            password = "admin",
            roles = "ADMIN")
    @Test
    void createFailIfDataIsInvalid() throws Exception {
        var vehicleDto = VehicleDto.builder()
                .name("test")
                .year(2025)
                .build();

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errors.year")
                        .value("Year cannot be greater than 2024"));
    }
}