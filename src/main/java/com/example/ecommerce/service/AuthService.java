package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.dto.auth.SigninRequestDto;
import com.example.ecommerce.dto.auth.SigninResponseDto;
import com.example.ecommerce.dto.auth.SignupDto;
import com.example.ecommerce.exceptions.AuthenticationFailedException;
import com.example.ecommerce.exceptions.UserNotCreatedException;
import com.example.ecommerce.models.AuthToken;
import com.example.ecommerce.models.User;
import com.example.ecommerce.respository.AuthTokenRepo;
import com.example.ecommerce.respository.UserRepo;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthTokenRepo authTokenRepo;

    @Transactional
    public void signUp(SignupDto signupDto) {
        if (Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
            throw new UserNotCreatedException("user already exists");
        }

        User user = new User(getUserDtoFromSignupDto(signupDto));
        userRepo.save(user);
        authTokenRepo.save(new AuthToken(user));
    }

    public SigninResponseDto signIn(SigninRequestDto signinRequestDto) {
        User user = userRepo.findByEmail(signinRequestDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailedException("invalid credentials");
        }

        String hashedPassword = "";
        try {
            hashedPassword = hashPassword(signinRequestDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new InternalError("internal server error");
        }

        if (!hashedPassword.equals(user.getPasswordHash())) {
            throw new AuthenticationFailedException("invalid credentials");
        }

        AuthToken authToken = authTokenRepo.findByUser(user);
        if (Objects.isNull(authToken)) {
            throw new AuthenticationFailedException("failed to authenticate user");
        }

        return new SigninResponseDto(true, authToken.getToken());
    }

    private User getUserFromToken(String token) {
        AuthToken authToken = authTokenRepo.findByToken(token);
        if (Objects.isNull(authToken) || Objects.isNull(authToken.getUser())) {
            throw new AuthenticationFailedException("invalid token");
        }
        return authToken.getUser();
    }

    public User authenticate(String token) {
        if (Objects.isNull(token)) {
            throw new AuthenticationFailedException("could not verify identity of the user");
        }
        token = extractTokenFromAuthHeader(token);
        return getUserFromToken(token);
    }

    private UserDto getUserDtoFromSignupDto(SignupDto signupDto) {
        UserDto userDto = new UserDto();
        userDto.setName(signupDto.getName());
        userDto.setEmail(signupDto.getEmail());

        String passwordHash = "";
        try {
            passwordHash = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new UserNotCreatedException("user password could not be hashed");
        }
        userDto.setPasswordHash(passwordHash);
        return userDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    private String extractTokenFromAuthHeader(String authToken) {
        try {
            authToken = authToken.substring("Bearer ".length());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("could not authenticate user");
        }
        return authToken;
    }
}
