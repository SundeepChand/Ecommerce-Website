package com.example.ecommerce.controller;

import com.example.ecommerce.common.constants.Constants;
import com.example.ecommerce.dto.checkout.CheckoutItemDto;
import com.example.ecommerce.dto.checkout.CheckoutResponseDto;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "JWT")
public class OrderController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkoutList(
            @RequestHeader(value = Constants.AUTH_HEADER_FIELD) String authToken,
            @RequestBody List<CheckoutItemDto> checkoutItemDtoList
    ) throws StripeException {

        User user = authService.authenticate(authToken);

        Session session = orderService.createStripeSession(user, checkoutItemDtoList);
        CheckoutResponseDto checkoutResponse = new CheckoutResponseDto(session.getId(), session.getUrl());
        return new ResponseEntity<>(checkoutResponse, HttpStatus.TEMPORARY_REDIRECT);
    }
}
