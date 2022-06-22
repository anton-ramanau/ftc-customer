package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("user/order/{orderId}")
public class CargoController {
    private final OrderService orderService;
    private final CargoService cargoService;

    public CargoController(OrderService orderService, CargoService cargoService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
    }

    @GetMapping("/cargo/new")
    public String getNewCargoView(@PathVariable Long orderId, Model model) {
        CargoCommand cargoCommand = new CargoCommand();
        model.addAttribute("cargo", cargoCommand);
        model.addAttribute("orderId", orderId);
        return "order/cargo/newCargo";
    }

    @PostMapping("/cargo/new")
    public String addNewCargoView(@PathVariable Long orderId, Principal principal, @ModelAttribute CargoCommand cargoCommand) {
        log.debug("Post new cargoCommand {}", cargoCommand);
        cargoService.saveCargoCommand(principal.getName(), orderId, cargoCommand);

        return "redirect:/user/order/" + orderId + "/update";
    }
}
