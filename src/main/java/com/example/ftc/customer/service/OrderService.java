package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;

public interface OrderService {

    Iterable<OrderCommand> findOrdersCommandByUserId(Long userId);

    Order saveOrder(Order order);

    Order findOrderById(Long orderId);

    Order findOrderByIdAndUserId(Long orderId, Long userId);

    OrderCommand findOrderCommandByIdAndUserId(Long orderId, Long userId);
}
