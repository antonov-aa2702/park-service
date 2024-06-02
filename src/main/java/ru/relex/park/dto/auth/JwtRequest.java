package ru.relex.park.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель данных для аутентификации пользователя.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    @NotNull(message = "Логин не может отсутствовать")
    private String login;

    @NotNull(message = "Пароль не может отсутствовать")
    private String password;
}