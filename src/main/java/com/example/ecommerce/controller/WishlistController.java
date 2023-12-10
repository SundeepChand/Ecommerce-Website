package com.example.ecommerce.controller;

import com.example.ecommerce.common.responses.ApiResponse;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import com.example.ecommerce.models.Wishlist;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@SecurityRequirement(name = "JWT")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private AuthService authService;

    // TODO: Use ProductDTO instead of Product here.
    @GetMapping
    public ResponseEntity<List<Product>> fetchWishListItems(@RequestHeader(value = "Authorization") String authToken) {
        User user = authService.authenticate(authToken);
        return new ResponseEntity<>(wishlistService.getAllProducts(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProductToWishList(
            @RequestHeader(value = "Authorization") String authToken,
            @RequestBody Product product
    ) {
        User user = authService.authenticate(authToken);
        Wishlist wishlist = new Wishlist(user, product);

        wishlistService.create(wishlist);

        return new ResponseEntity<>(
                new ApiResponse(true, "successfully created wishlist item"),
                HttpStatus.CREATED
        );
    }
}
