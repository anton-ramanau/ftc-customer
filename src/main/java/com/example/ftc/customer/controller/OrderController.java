package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CargoService cargoService;
    private final UserService userService;

    public OrderController(OrderService orderService, CargoService cargoService, UserService userService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.userService = userService;
    }

    @GetMapping("/user/order/new")
    public String createNewOrder(Principal principal, Model model) {
        OrderCommand order = new OrderCommand();
        UserCommand userCommand = userService.findUserCommandByUsername(principal.getName());
        CargoCommand cargo = new CargoCommand();
        order.setUser(userCommand);
        model.addAttribute("order", order);
        model.addAttribute("cargo", cargo);

        return "order/newOrder";
    }

    @PostMapping
    public String createNewOrder() {

        return "redirect:user/index";
    }

    @PostMapping("/user/order/cargo/new")
    public String createNewCargo(OrderCommand order, CargoCommand cargo) {
        return "";
    }
}
