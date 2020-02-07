package com.example.orderservice.controller;

import com.example.orderservice.exception.ErrorResponse;
import com.example.orderservice.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ServiceException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getErrorCode().getErrorCode(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

}
