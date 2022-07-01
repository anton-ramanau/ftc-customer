package com.example.ftc.customer.exception;

import lombok.Getter;

@Getter
public class CargoNotFoundException extends RuntimeException{

    private final Long cargoId;
    private final Long orderId;

    public CargoNotFoundException(Long cargoId, Long orderId) {
        super("Cargo not found");
        this.cargoId = cargoId;
        this.orderId = orderId;
    }

}
