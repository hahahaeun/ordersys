package com.example.ordersys.domain.product.exception;

public class SoldOutException extends RuntimeException{
    public SoldOutException(String message) {
        super(message);
    }
}
