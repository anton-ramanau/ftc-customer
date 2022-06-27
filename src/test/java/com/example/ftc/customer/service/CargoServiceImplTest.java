package com.example.ftc.customer.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CargoServiceImplTest {

    @Autowired
    CargoServiceImpl cargoService;

    @Test
    void deleteCargoByCargoIdAndOrderId() {
        cargoService.deleteCargoByCargoIdAndOrderId(16L, 9L);

    }
}