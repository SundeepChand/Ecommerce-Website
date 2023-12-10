package com.example.ecommerce.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "wishlist", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "product_id"}) })
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_date")
    private Date createdDate;

    public Wishlist() {
    }

    public Wishlist(Integer id, User user, Product product, Date createdDate) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.createdDate = createdDate;
    }

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
