package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderToOrderCommand orderToOrderCommand;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, OrderToOrderCommand orderToOrderCommand) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderToOrderCommand = orderToOrderCommand;
    }

    @Override
    public Iterable<OrderCommand> findOrdersByUserId(Long userId) {
        Set<OrderCommand> orders = new HashSet<>();
        orderRepository.findOrdersByUserId(userId).forEach(order -> orders.add(orderToOrderCommand.convert(order)));
        return orders;
    }

    @Override
    public Iterable<OrderCommand> findOrdersByUserName(String userName) {
        User user = userService.findUserByUsername(userName);
        return findOrdersByUserId(user.getId());
    }
}
