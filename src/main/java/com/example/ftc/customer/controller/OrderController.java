package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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

    //todo: make possibility to create order with data
    @PostMapping("/user/order/new")
    public String createNewOrder(HttpServletRequest request) {
        Order order = new Order();
        order.setUser(userService.findUserById(ServerUtils.getSessionUserId(request)));
        orderService.saveOrder(order);

        return "redirect:/user/orders";
    }

    @GetMapping("/user/orders")
    public String getOrdersView(HttpServletRequest request, Model model) {
        Long userId = ServerUtils.getSessionUserId(request);
        Iterable<OrderCommand> orders = orderService.findOrdersCommandByUserId(userId);
        UserCommand userCommand = userService.findUserCommandById(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("user", userCommand);

        return "order/ordersList";
    }

    @PostMapping("/user/order/{orderId}/delete")
    public String deleteOrder(@PathVariable Long orderId, HttpServletRequest request, Model model) {
        orderService.deleteOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request));
        return "redirect:/user/orders";
    }

    @GetMapping("user/order/{orderId}/update")
    public String getOrderUpdateView(@PathVariable Long orderId, HttpServletRequest request, Model model) {
        OrderCommand orderCommand = orderService.findOrderCommandByIdAndUserId(orderId, ServerUtils.getSessionUserId(request));
        if (orderCommand == null) {
            throw new RuntimeException("Order doesn't exists");
        } else {
            model.addAttribute("order", orderCommand);
            return "order/orderUpdate";
        }
    }
}
