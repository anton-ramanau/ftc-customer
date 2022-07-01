package com.example.ftc.customer.exception;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException() {
        super("Order not found");
    }
}
