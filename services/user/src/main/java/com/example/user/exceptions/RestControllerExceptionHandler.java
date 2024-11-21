package com.example.user.exceptions;

import com.example.user.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice(basePackages = "com.example.user.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleException(UserAlreadyExistsException e) {
        var message = e.getMessage();
        logger.error(message);
        return getErrorDTOResponseEntity(HttpStatus.CONFLICT, message);
    }

    private ResponseEntity<ErrorDto> getErrorDTOResponseEntity(HttpStatus httpStatus, String message) {
        final var response = new ErrorDto(message, httpStatus.value() + "");
        return new ResponseEntity<>(response, httpStatus);
    }
}
