package com.example.ftc.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/user")
public class UserController {

    @GetMapping
    public String userMain() {

        return "user_page";
    }
}
