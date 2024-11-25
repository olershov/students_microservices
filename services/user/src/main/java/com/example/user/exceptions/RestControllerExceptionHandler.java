package com.example.user.exceptions;

import com.example.user.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice(basePackages = "com.example.user.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleException(UserAlreadyExistsException e) {
        return getErrorDTOResponseEntity(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(UsernameNotFoundException e) {
        return getErrorDTOResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ErrorDto> getErrorDTOResponseEntity(HttpStatus httpStatus, String message) {
        final var response = new ErrorDto(message, httpStatus.value() + "");
        return new ResponseEntity<>(response, httpStatus);
    }
}
