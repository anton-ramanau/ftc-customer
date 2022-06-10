package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;

public interface OrderService {

    Iterable<OrderCommand> findOrdersByUserId(Long userId);

    Iterable<OrderCommand> findOrdersByUserName(String userName);
}
