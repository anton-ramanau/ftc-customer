package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    private final UserService userService;
    private final UserToUserCommand userToUserCommand;
    private final OrderService orderService;
    private final OrderToOrderCommand orderToOrderCommand;
    private final CargoService cargoService;
    private final CargoToCargoCommand cargoToCargoCommand;

    public UserController(UserService userService, UserToUserCommand userToUserCommand, OrderService orderService, OrderToOrderCommand orderToOrderCommand, CargoService cargoService, CargoToCargoCommand cargoToCargoCommand) {
        this.userService = userService;
        this.userToUserCommand = userToUserCommand;
        this.orderService = orderService;
        this.orderToOrderCommand = orderToOrderCommand;
        this.cargoService = cargoService;
        this.cargoToCargoCommand = cargoToCargoCommand;
    }

    @GetMapping("/user")
    public String userMain(HttpServletRequest request, Model model) {
        User user = userService.findUserById(ServerUtils.getSessionUserId(request));
        Iterable<Order> orders = orderService.findOrdersByUserId(user.getId());
        Set<OrderCommand> orderCommands = new HashSet<>();
        orders.forEach(order -> orderCommands.add(orderToOrderCommand.convert(order)));
        UserCommand userCommand = userToUserCommand.convert(user);
        model.addAttribute("user", userCommand);
        Map<OrderCommand, Set<CargoCommand>> orderCargoMap = new HashMap<>();
        Consumer<OrderCommand> consumer = new Consumer<OrderCommand>() {
            @Override
            public void accept(OrderCommand orderCommand) {
                Set<CargoCommand> cargoes = new HashSet<>();
                cargoService.findAllByOrderId(orderCommand.getId()).forEach(cargo -> cargoes.add(cargoToCargoCommand.convert(cargo)));
                orderCargoMap.put(orderCommand, cargoes);
            }
        };
        orderCommands.forEach(consumer);
        model.addAttribute("orderCargoMap", orderCargoMap);
        return "user/index";
    }
}
