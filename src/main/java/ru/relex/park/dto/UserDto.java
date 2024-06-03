package ru.relex.park.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Login cannot be null")
    private String login;

    @NotNull(message = "Password cannot be null")
    private String password;
}
