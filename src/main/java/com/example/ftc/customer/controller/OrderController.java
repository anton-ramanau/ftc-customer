package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.converter.OrderCommandToOrder;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.exception.AccessForbiddenException;
import com.example.ftc.customer.exception.OrderNotFoundException;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderToOrderCommand orderToOrderCommand;
    private final OrderCommandToOrder orderCommandToOrder;
    private final UserToUserCommand userToUserCommand;
    private final CargoService cargoService;
    private final CargoToCargoCommand cargoToCargoCommand;

    public OrderController(OrderService orderService, UserService userService, OrderToOrderCommand orderToOrderCommand, OrderCommandToOrder orderCommandToOrder, UserToUserCommand userToUserCommand, CargoService cargoService, CargoToCargoCommand cargoToCargoCommand) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderToOrderCommand = orderToOrderCommand;
        this.orderCommandToOrder = orderCommandToOrder;
        this.userToUserCommand = userToUserCommand;
        this.cargoService = cargoService;
        this.cargoToCargoCommand = cargoToCargoCommand;
    }

    @GetMapping("/user/order/new")
    public String getNewOrderView(Model model) {
        OrderCommand orderCommand = new OrderCommand();
        model.addAttribute("order");
        return "/order/orderNew";
    }

    @PostMapping("/user/order/new")
    public String createNewOrder(@Valid OrderCommand orderCommand, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/order/orderNew";
        }
        User user = userService.findUserById(ServerUtils.getSessionUserId(request));
        Order order = orderCommandToOrder.convert(orderCommand);
        order.setUser(user);
        orderService.saveOrder(order);
        return "redirect:/user";
    }

    @PostMapping("/user/order/delete")
    public String deleteOrder(@RequestParam Long orderId, HttpServletRequest request) {
        Order orderDB = orderService.findOrderById(orderId);
        if (orderDB == null) {
            throw new OrderNotFoundException(orderId);
        }
        if (!orderDB.getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        cargoService.deleteAllByOrderId(orderId);
        orderService.deleteOrder(orderDB);
        return "redirect:/user";
    }

    @GetMapping("user/order/{orderId}/details")
    public String getOrderUpdateView(@PathVariable Long orderId, HttpServletRequest request, Model model) {
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        if (!order.getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        OrderCommand orderCommand = orderToOrderCommand.convert(order);
        Iterable<Cargo> cargoes = cargoService.findAllByOrderId(order.getId());
        Set<CargoCommand> cargoCommands = new HashSet<>();
        cargoes.forEach((c) -> cargoCommands.add(cargoToCargoCommand.convert(c)));
        model.addAttribute("order", orderCommand);
        model.addAttribute("cargoes", cargoCommands);
        return "/order/orderDetails";
    }
}
