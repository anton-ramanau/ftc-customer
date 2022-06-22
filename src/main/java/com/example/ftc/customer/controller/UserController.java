package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userMain(HttpServletRequest request, Model model) {
        UserCommand userCommand = userService.findUserCommandById(ServerUtils.getSessionUserId(request));
        model.addAttribute("user", userCommand);
        return "user/index";
    }
}
