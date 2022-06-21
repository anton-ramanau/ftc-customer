package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;

public interface OrderService {

    Iterable<OrderCommand> findOrdersCommandByUserId(Long userId);

    Iterable<OrderCommand> findOrdersCommandByUserName(String userName);

    Order save(Order order);
}
