package com.example.ftc.customer.controller;

import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CargoService cargoService;

    public OrderController(OrderService orderService, CargoService cargoService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
    }
}
