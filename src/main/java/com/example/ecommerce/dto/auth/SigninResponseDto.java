package com.example.ecommerce.dto.auth;

public class SigninResponseDto {
    private boolean isSuccess;
    private String token;

    public SigninResponseDto(boolean isSuccess, String token) {
        this.isSuccess = isSuccess;
        this.token = token;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
