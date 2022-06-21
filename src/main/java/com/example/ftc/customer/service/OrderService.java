package com.example.ftc.customer.service;

import com.example.ftc.customer.command.OrderCommand;

public interface OrderService {

    Iterable<OrderCommand> findOrdersCommandByUserId(Long userId);

    Iterable<OrderCommand> findOrdersCommandByUserName(String userName);
}
