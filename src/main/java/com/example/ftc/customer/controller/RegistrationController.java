package com.example.ftc.customer.controller;

import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String sendForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login/registration";
    }

    @PostMapping("/registration")
    public String createNewUser(User user) {
        if (userService.findUserByUsername(user.getUsername()) == null) {
            user.setActive(true);
            userService.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/registration";
        }
    }
}
