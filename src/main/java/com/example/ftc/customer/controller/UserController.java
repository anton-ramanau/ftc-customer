package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;
    private final UserToUserCommand userToUserCommand;

    public UserController(UserService userService, UserToUserCommand userToUserCommand) {
        this.userService = userService;
        this.userToUserCommand = userToUserCommand;
    }

    @GetMapping("/user")
    public String userMain(HttpServletRequest request, Model model) {
        User user = userService.findUserById(ServerUtils.getSessionUserId(request));
        UserCommand userCommand = userToUserCommand.convert(user);
        model.addAttribute("user", userCommand);
        return "user/index";
    }
}
