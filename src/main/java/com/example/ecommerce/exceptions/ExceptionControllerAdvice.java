package com.example.ecommerce.exceptions;

import com.example.ecommerce.common.responses.ApiResponse;
import com.stripe.exception.StripeException;
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

    @ExceptionHandler(value = IllegalArgumentException.class)
    public final ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = WishlistItemNotCreatedException.class)
    public final ResponseEntity<ApiResponse> handleWishlistItemNotCreatedException(WishlistItemNotCreatedException e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = StripeException.class)
    public final ResponseEntity<ApiResponse> handleStripePaymentFailException(StripeException e) {
        return new ResponseEntity<>(
                new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
