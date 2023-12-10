package com.example.ecommerce.respository;

import com.example.ecommerce.models.AuthToken;
import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepo extends JpaRepository<AuthToken, Integer> {
    AuthToken findByUser(User user);
}
