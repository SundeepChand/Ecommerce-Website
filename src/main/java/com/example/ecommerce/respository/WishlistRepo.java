package com.example.ecommerce.respository;

import com.example.ecommerce.models.User;
import com.example.ecommerce.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);
}
