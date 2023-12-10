package com.example.ecommerce.service;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import com.example.ecommerce.models.Wishlist;
import com.example.ecommerce.respository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepo wishlistRepo;

    // TODO: Use ProductDTO instead of Product here.
    public List<Product> getAllProducts(User user) {
        List<Wishlist> wishlistItems = wishlistRepo.findAllByUserOrderByCreatedDateDesc(user);

        List<Product> products = new ArrayList<>();
        for (Wishlist wishlist: wishlistItems) {
            products.add(wishlist.getProduct());
        }
        return products;
    }

    public void create(Wishlist wishlist) {
        try {
            wishlistRepo.save(wishlist);
        } catch (Exception e) {
            throw new InternalError("could not create wishlist item");
        }
    }
}
