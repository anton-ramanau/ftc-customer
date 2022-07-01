package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final UserToUserCommand userToUserCommand;
    private final OrderService orderService;
    private final OrderToOrderCommand orderToOrderCommand;

    public UserController(UserService userService, UserToUserCommand userToUserCommand, OrderService orderService, OrderToOrderCommand orderToOrderCommand) {
        this.userService = userService;
        this.userToUserCommand = userToUserCommand;
        this.orderService = orderService;
        this.orderToOrderCommand = orderToOrderCommand;
    }

    @GetMapping("/user")
    public String userMain(HttpServletRequest request, Model model) {
        User user = userService.findUserById(ServerUtils.getSessionUserId(request));
        Iterable<Order> orders = orderService.findOrdersByUserId(user.getId());
        Set<OrderCommand> orderCommands = new HashSet<>();
        orders.forEach(order -> orderCommands.add(orderToOrderCommand.convert(order)));
        model.addAttribute("orders", orders);
        UserCommand userCommand = userToUserCommand.convert(user);
        model.addAttribute("user", userCommand);
        return "user/index";
    }
}
