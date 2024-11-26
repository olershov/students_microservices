package com.example.user.exceptions;

import com.example.user.controller.AuthController;
import com.example.user.model.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.logging.Logger;


@RestControllerAdvice(basePackages = "com.example.user.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(RestControllerExceptionHandler.class.getName());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final var errors = new ArrayList<String>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(objectError ->
                errors.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage()));

        final var error = new ErrorDto("The argument(s) have not been validated", errors);
        LOGGER.info(error.toString());
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleException(UserAlreadyExistsException e) {
        return getErrorDTOResponseEntity(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(UserNotFoundException e) {
        return getErrorDTOResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException e) {
        return getErrorDTOResponseEntity(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<ErrorDto> getErrorDTOResponseEntity(HttpStatus httpStatus, String message) {
        LOGGER.info(message);
        final var response = new ErrorDto(message, httpStatus.value() + "");
        return new ResponseEntity<>(response, httpStatus);
    }
}
