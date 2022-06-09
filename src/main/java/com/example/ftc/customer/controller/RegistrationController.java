package com.example.ftc.customer.controller;

import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String sendForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login/registration";
    }

    @PostMapping("/registration")
    public String createNewUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()) == null) {
            user.setActive(true);
            userRepository.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/registration";
        }
    }
}
