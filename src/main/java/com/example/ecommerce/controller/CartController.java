package com.example.ecommerce.controller;

import com.example.ecommerce.common.constants.Constants;
import com.example.ecommerce.common.responses.ApiResponse;
import com.example.ecommerce.dto.CartItemDto;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@SecurityRequirement(name = "JWT")
public class CartController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    // TODO: Improve error handling by using proper DTOs for each of the requests.
    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCart(@RequestHeader(value = "Authorization") String authToken) {
        User user = authService.authenticate(authToken);
        List<CartItem> cartItems = cartService.getAllCartItemsForUser(user);

        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            cartItemDtos.add(new CartItemDto(cartItem));
        }

        return new ResponseEntity<>(cartItemDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCartItem(
            @RequestHeader(value = Constants.AUTH_HEADER_FIELD) String authToken,
            @RequestBody CartItemDto cartItemDto
    ) {
        User user = authService.authenticate(authToken);
        CartItem cartItem = new CartItem(user, cartItemDto.getProduct(), cartItemDto.getProductQuantity());
        cartService.createCartItem(cartItem);
        return new ResponseEntity<>(
                new ApiResponse(true, "successfully created cart item"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(
            @RequestHeader(value = Constants.AUTH_HEADER_FIELD) String authToken,
            @PathVariable(name = "cartItemId") Integer cartItemId,
            @RequestBody CartItemDto cartItemDto
    ) {
        User user = authService.authenticate(authToken);
        CartItem cartItem = new CartItem(
                cartItemId,
                user,
                cartItemDto.getProduct(),
                cartItemDto.getCreatedAt(),
                cartItemDto.getProductQuantity()
        );
        cartService.updateCartItem(cartItem, user);
        return new ResponseEntity<>(
                new ApiResponse(true, "successfully updated cart item"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @RequestHeader(value = Constants.AUTH_HEADER_FIELD) String authToken,
            @PathVariable(name = "cartItemId") Integer cartItemId
    ) {
        User user = authService.authenticate(authToken);
        cartService.deleteCartItem(cartItemId, user.getId());
        return new ResponseEntity<>(
                new ApiResponse(true, "successfully deleted cart item"),
                HttpStatus.OK
        );
    }
}
