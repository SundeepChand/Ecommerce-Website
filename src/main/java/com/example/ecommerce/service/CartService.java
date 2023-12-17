package com.example.ecommerce.service;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.User;
import com.example.ecommerce.respository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepository;

    public List<CartItem> getAllCartItemsForUser(User user) {
        return cartRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    public void createCartItem(CartItem cartItem) {
        try {
            cartRepository.save(cartItem);
        } catch (Exception e) {
            throw new InternalError("could not create cart item");
        }
    }

    private CartItem getCartItemById(int cartItemId) {
        Optional<CartItem> cartItemOptional = cartRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new IllegalArgumentException("Cart item with id = " + cartItemId + " does not exist.");
        }

        return cartItemOptional.get();
    }

    public void updateCartItem(CartItem newCartItem, User user) {
        CartItem cartItem = getCartItemById(newCartItem.getId());
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("cart item does not belong to user");
        }

        cartItem.setQuantity(newCartItem.getQuantity());
        cartRepository.save(cartItem);
    }

    public void deleteCartItem(int cartItemId, int userId) {
        CartItem cartItem = getCartItemById(cartItemId);
        if (cartItem.getUser().getId() != userId) {
            throw new IllegalArgumentException("cart item does not belong to user");
        }
        cartRepository.deleteById(cartItemId);
    }
}
