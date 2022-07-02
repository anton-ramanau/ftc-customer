package com.example.ftc.customer.exception;

import lombok.Getter;

@Getter
public class CargoNotFoundException extends RuntimeException{

    private final Long cargoId;

    public CargoNotFoundException(Long cargoId) {
        super("Cargo not found");
        this.cargoId = cargoId;
    }

}
