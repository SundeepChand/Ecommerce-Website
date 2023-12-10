package com.example.ecommerce.controller;

import com.example.ecommerce.common.responses.ApiResponse;
import com.example.ecommerce.dto.auth.SigninRequestDto;
import com.example.ecommerce.dto.auth.SigninResponseDto;
import com.example.ecommerce.dto.auth.SignupDto;
import com.example.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignupDto signupDto) {
        authService.signUp(signupDto);
        return new ResponseEntity<>(
                new ApiResponse(true, "successfully created user"),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signIn(@RequestBody SigninRequestDto signinDto) {
        SigninResponseDto signinResponse = authService.signIn(signinDto);
        return new ResponseEntity<>(
                signinResponse,
                HttpStatus.OK
        );
    }
}
