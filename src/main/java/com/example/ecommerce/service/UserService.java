package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.models.User;
import com.example.ecommerce.respository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void createUser(UserDto userDto) {
        userRepo.save(new User(userDto));
    }
}
