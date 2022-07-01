package com.example.ftc.customer.service;

import com.example.ftc.customer.domain.Order;

public interface OrderService {

    Iterable<Order> findOrdersByUserId(Long userId);

    Order saveOrder(Order order);

    void deleteOrder(Order order);

    Order findOrderByIdAndUserId(Long orderId, Long userId);

}
