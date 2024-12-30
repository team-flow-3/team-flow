package com.currency.teamflow.global.error.handler;

import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import com.currency.teamflow.global.error.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ce) {
        return ErrorResponse.toResponseEntity(ce.getErrorCode());
    }

    /**
     * Security와 관련된 AuthenticationException 예외 처리.
     *
     * @param e AuthenticationException 인스턴스
     * @return {@code ResponseEntity<CommonResponseBody<Void>>}
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthException(
            AuthenticationException e) {
        ErrorCode statusCode = e instanceof BadCredentialsException
                ? ErrorCode.FORBIDDEN_PERMISSION
                : ErrorCode.UNAUTHORIZED;

        return ErrorResponse.toResponseEntity(statusCode);
    }

    /**
     * Security와 관련된 AccessDeniedException 예외 처리.
     *
     * @param e AccessDeniedException 인스턴스
     * @return {@code ResponseEntity<CommonResponseBody<Void>>}
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException e) {

        return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }

    /**
     * Security와 관련된 AuthorizationDeniedException 예외 처리.
     *
     * @param e AuthorizationDeniedException 인스턴스
     * @return {@code ResponseEntity<CommonResponseBody<Void>>}
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
            AuthorizationDeniedException e) {

        return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }

    /**
     * JWT와 관련된 JwtException 예외 처리.
     *
     * @param e JwtException 인스턴스
     * @return {@code ResponseEntity<CommonResponseBody<Void>>}
     */
    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {

        ErrorCode statusCode = e instanceof ExpiredJwtException
                ? ErrorCode.FORBIDDEN_PERMISSION
                : ErrorCode.UNAUTHORIZED;

        return ErrorResponse.toResponseEntity(statusCode);
    }
}
