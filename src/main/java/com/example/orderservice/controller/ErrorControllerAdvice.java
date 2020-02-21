package com.example.orderservice.controller;

import com.example.orderservice.exception.ConflictException;
import com.example.orderservice.exception.ErrorMessage;
import com.example.orderservice.exception.ErrorResponse;
import com.example.orderservice.exception.ServiceErrorCode;
import com.example.orderservice.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceExceptionHandler(ServiceException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getErrorCode().getErrorCode(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> sqlViolationHandler(DataIntegrityViolationException exception) {
        Throwable rootCause = exception.getRootCause();
        SQLException sqlException = (SQLException) rootCause;
        String message = rootCause.getMessage();
        if ((message != null) && (message.contains("customer_email_key")) &&
                sqlException.getSQLState().equals("23505")) {
            return serviceExceptionHandler(
                    new ConflictException(ErrorMessage.CUSTOMER_ALREADY_EXIST, ServiceErrorCode.ALREADY_EXIST));
        }
        return new ResponseEntity<>(
                new ErrorResponse(LocalDateTime.now(), ServiceErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(),
                                  exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


