package com.example.ftc.customer.domain;

import lombok.Getter;

@Getter
public enum CargoType {
    FULL_LOAD("Full load"), GROUPAGE ("Part load");

    private String shortDescription;

    CargoType(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
