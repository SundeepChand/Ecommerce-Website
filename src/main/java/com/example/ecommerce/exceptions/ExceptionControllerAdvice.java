package com.example.ecommerce.exceptions;

import com.example.ecommerce.common.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = UserNotCreatedException.class)
    public final ResponseEntity<ApiResponse> handleUserNotCreatedException(UserNotCreatedException e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = AuthenticationFailedException.class)
    public final ResponseEntity<ApiResponse> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = InternalError.class)
    public final ResponseEntity<ApiResponse> handleInternalServerErrorException(InternalError e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
