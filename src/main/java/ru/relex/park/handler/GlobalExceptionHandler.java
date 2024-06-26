package ru.relex.park.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.relex.park.exception.ApplicationError;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApplicationError handleAccessDenied(AccessDeniedException exception) {
        log.severe(exception.getMessage());
        return ApplicationError.builder()
                .message("Доступ запрещён!")
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleFailedAuthentication(AuthenticationException exception) {
        log.severe(exception.getMessage());
        return ApplicationError.builder()
                .message("Аутентификация не удалась! Проверьте логин и пароль!")
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleIllegalState(IllegalStateException exception) {
        log.severe(exception.getMessage());
        return ApplicationError.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError handleInvalidFormatDate(MethodArgumentNotValidException exception) {
        log.severe(exception.getMessage());
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        return ApplicationError.builder()
                .message("Произошла ошибка при проверке введенных данных! Проверьте введенные данные и повторите попытку.")
                .errors(getCollectMapOfErrors(errors))
                .build();
    }

    private static Map<String, String> getCollectMapOfErrors(List<FieldError> errors) {
        return errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage));
    }
}

