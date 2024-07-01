package com.example.ordersys.domain.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    }
}