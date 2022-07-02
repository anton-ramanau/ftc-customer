package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.converter.OrderCommandToOrder;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.exception.OrderNotFoundException;
import com.example.ftc.customer.repository.CargoRepository;
import com.example.ftc.customer.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderToOrderCommand orderToOrderCommand;
    private final OrderCommandToOrder orderCommandToOrder;
    private final CargoRepository cargoRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, OrderToOrderCommand orderToOrderCommand, OrderCommandToOrder orderCommandToOrder, CargoRepository cargoRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderToOrderCommand = orderToOrderCommand;
        this.orderCommandToOrder = orderCommandToOrder;
        this.cargoRepository = cargoRepository;
    }

    @Override
    public Iterable<Order> findOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order findOrderByIdAndUserId(Long orderId, Long userId) {
        return orderRepository.findOrderByIdAndUserId(orderId, userId).orElse(null);
    }

}
