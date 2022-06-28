package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.domain.User;
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
import java.util.HashSet;
import java.util.Set;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderToOrderCommand orderToOrderCommand;
    private final UserToUserCommand userToUserCommand;
    private final CargoService cargoService;
    private final CargoToCargoCommand cargoToCargoCommand;

    public OrderController(OrderService orderService, UserService userService, OrderToOrderCommand orderToOrderCommand, UserToUserCommand userToUserCommand, CargoService cargoService, CargoToCargoCommand cargoToCargoCommand) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderToOrderCommand = orderToOrderCommand;
        this.userToUserCommand = userToUserCommand;
        this.cargoService = cargoService;
        this.cargoToCargoCommand = cargoToCargoCommand;
    }

    //todo: make possibility to create order with data
    @PostMapping("/user/order/new")
    public String createNewOrder(HttpServletRequest request) {
        Order order = new Order();
        User user = userService.findUserById(ServerUtils.getSessionUserId(request));
        order.setUser(user);
        orderService.saveOrder(order);
        return "redirect:/user/orders";
    }

    @GetMapping("/user/orders")
    public String getOrdersView(HttpServletRequest request, Model model) {
        Long userId = ServerUtils.getSessionUserId(request);
        Iterable<Order> orders = orderService.findOrdersByUserId(ServerUtils.getSessionUserId(request));
        Set<OrderCommand> orderCommands = new HashSet<>();
        orders.forEach(order -> orderCommands.add(orderToOrderCommand.convert(order)));
        model.addAttribute("orders", orders);
        User user = userService.findUserById(userId);
        UserCommand userCommand = userToUserCommand.convert(user);
        model.addAttribute("user", userCommand);
        return "order/ordersList";
    }

    @PostMapping("/user/order/{orderId}/delete")
    public String deleteOrder(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.deleteOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request));
        return "redirect:/user/orders";
    }

    //todo PostController for updating orderData
    @GetMapping("user/order/{orderId}/update")
    public String getOrderUpdateView(@PathVariable Long orderId, HttpServletRequest request, Model model) {
        Order order = orderService.findOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request));
        OrderCommand orderCommand = orderToOrderCommand.convert(order);
        Iterable<Cargo> cargoes = cargoService.findAllByOrderId(orderId);
        Set<CargoCommand> cargoCommands = new HashSet<>();
        cargoes.forEach((c) -> cargoCommands.add(cargoToCargoCommand.convert(c)));
        model.addAttribute("order", orderCommand);
        model.addAttribute("cargoes", cargoCommands);
        return "order/orderUpdate";
    }
}
