package com.currency.teamflow.global.error.handler;

import com.currency.teamflow.global.error.exception.CustomException;
import com.currency.teamflow.global.error.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ce) {
        return ErrorResponse.toResponseEntity(ce.getErrorCode());
    }
}
