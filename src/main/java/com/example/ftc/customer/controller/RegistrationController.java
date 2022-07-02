package com.example.ftc.customer.controller;

import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String sendForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login/registration";
    }

    //todo: check user name without upper chars
    //todo: return to view password error
    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, @RequestParam("password-rep") String passwordRep, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "login/registration";
        }
        if (!user.getPassword().equals(passwordRep)) {
            model.addAttribute("user", user);
            model.addAttribute("difPasswordMessage", "Passwords are different");
            return "login/registration";
        }
        if (userService.findUserByUsername(user.getUsername()) == null) {
            String passwordEncoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncoded);
            user.setActive(true);
            userService.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/registration";
        }
    }
}
