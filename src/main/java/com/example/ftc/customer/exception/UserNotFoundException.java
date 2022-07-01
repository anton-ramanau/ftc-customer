package com.example.ftc.customer.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super("User not found");
    }
}
