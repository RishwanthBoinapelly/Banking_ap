package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConstraintViolation(DataIntegrityViolationException ex) {
        String message;

        if (ex.getMessage().contains("Duplicate entry")) {
            message = "Email must be unique";
        } else {
            message = "Database constraint violation";
        }

        return new ResponseEntity<>(new ErrorResponse("Validation Failed", message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder messageBuilder = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            messageBuilder.append(error.getDefaultMessage()).append(". ");
        }

        String message = messageBuilder.toString().trim();

        if (message.isEmpty()) {
            message = "Unknown validation error";
        } 

        return new ResponseEntity<>(new ErrorResponse("Validation Failed", message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse("Database Error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponse> handleCustomRestException(RestException ex) {
        return new ResponseEntity<>(new ErrorResponse("Application Error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Unexpected Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
