package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.converter.OrderCommandToOrder;
import com.example.ftc.customer.converter.OrderToOrderCommand;
import com.example.ftc.customer.domain.Order;
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
    private final OrderCommandToOrder orderCommandToOrder;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, OrderToOrderCommand orderToOrderCommand, OrderCommandToOrder orderCommandToOrder) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderToOrderCommand = orderToOrderCommand;
        this.orderCommandToOrder = orderCommandToOrder;
    }

    @Override
    public Iterable<OrderCommand> findOrdersCommandByUserId(Long userId) {
        Set<OrderCommand> orders = new HashSet<>();
        orderRepository.findOrdersByUserId(userId).forEach(order -> orders.add(orderToOrderCommand.convert(order)));
        return orders;
    }

    @Override
    public Iterable<OrderCommand> findOrdersCommandByUserName(String userName) {
        User user = userService.findUserByUsername(userName);
        return findOrdersCommandByUserId(user.getId());
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderByUserNameAndOrderId(String userName, Long orderId) {
        Order order = findOrderById(orderId);
        return order == null ? null : order.getUser().getUsername().equals(userName)
                ? order : null;
    }

    @Override
    public OrderCommand findOrderCommandByUserNameAndOrderId(String userName, Long orderId) {

        return orderToOrderCommand.convert(findOrderByUserNameAndOrderId(userName, orderId));
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }


}
