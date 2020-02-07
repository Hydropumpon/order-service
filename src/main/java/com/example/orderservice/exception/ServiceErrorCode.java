package com.example.orderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceErrorCode {
    NOT_FOUND(1),
    ALREADY_EXIST(2),
    UNAVAILABLE(3);

    private int errorCode;
}
