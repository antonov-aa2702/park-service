package ru.relex.park.exception;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class ApplicationError {

    String message;

    Map<String, String> errors;
}
