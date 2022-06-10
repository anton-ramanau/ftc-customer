package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findOrdersByUserId(Long userId);
}
