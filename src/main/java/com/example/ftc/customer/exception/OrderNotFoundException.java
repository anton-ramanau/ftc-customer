package com.example.ftc.customer.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    private final Long orderId;

    public OrderNotFoundException(Long orderId) {
        super("Order not found");
        this.orderId = orderId;
    }
}
