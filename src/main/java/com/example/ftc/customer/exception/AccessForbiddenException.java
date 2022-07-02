package com.example.ftc.customer.exception;

public class AccessForbiddenException extends RuntimeException {

    public AccessForbiddenException() {
        super("Access forbidden");
    }
}
