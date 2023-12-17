package com.example.ecommerce.dto;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CartItemDto {
    private Integer id;

    private @NotNull User user;

    private @NotNull Product product;

    private @NotNull Integer productQuantity;

    private Date createdAt;

    public CartItemDto() {}

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.user = cartItem.getUser();
        this.product = cartItem.getProduct();
        this.productQuantity = cartItem.getQuantity();
        this.createdAt = cartItem.getCreatedAt();
    }

    public CartItemDto(User user, Product product, Integer productQuantity) {
        this.user = user;
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
