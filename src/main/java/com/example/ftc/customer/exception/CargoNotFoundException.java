package com.example.ftc.customer.exception;

public class CargoNotFoundException extends Exception{

    public CargoNotFoundException() {
        super("Cargo not found");
    }
}
