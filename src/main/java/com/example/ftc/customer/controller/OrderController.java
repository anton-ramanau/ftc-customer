package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/user/order/new")
    public String createNewOrder(Principal principal) {
        Order order = new Order();
        order.setUser(userService.findUserByUsername(principal.getName()));
        orderService.saveOrder(order);

        return "redirect:/user/orders";
    }

    @GetMapping("/user/orders")
    public String getOrdersView(Principal principal, Model model) {
        Iterable<OrderCommand> orders = orderService.findOrdersCommandByUserName(principal.getName());
        UserCommand userCommand = userService.findUserCommandByUsername(principal.getName());
        model.addAttribute("orders", orders);
        model.addAttribute("user", userCommand);

        return "order/ordersList";
    }

    @GetMapping("user/order/{orderId}/update")
    public String getOrderUpdateView(@PathVariable Long orderId, Principal principal, Model model) {
        OrderCommand orderCommand = orderService.findOrderCommandByUserNameAndOrderId(principal.getName(), orderId);
        if (orderCommand == null) {
            throw new RuntimeException("Order doesn't exists");
        } else {
            model.addAttribute("order", orderCommand);
            return "order/orderUpdate";
        }
    }
}
