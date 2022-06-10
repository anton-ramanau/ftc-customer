package com.example.ftc.customer.controller;

import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final OrderService orderService;
    private final UserService userService;

    public UserController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userMain(Principal principal, Model model) {
        model.addAttribute("orders", orderService.findOrdersByUserName(principal.getName()));
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        return "user/index";
    }
}
