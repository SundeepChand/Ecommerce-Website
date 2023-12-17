package com.example.ecommerce.respository;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByUserOrderByCreatedAtDesc(User user);

}
