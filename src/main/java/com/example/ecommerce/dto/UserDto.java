package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    private @NotBlank String email;
    private @NotBlank String name;
    private @NotBlank String passwordHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
