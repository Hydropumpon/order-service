package com.example.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;
    private final ServiceErrorCode errorCode;

    public ServiceException(String message, HttpStatus status, ServiceErrorCode errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return String.format("Exception message: {%s}, http status code {%s}, error code {%s}", super.getMessage(),
                             status.toString(), errorCode.name());
    }
}
