package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findOrdersByUserId(Long userId);

    Optional <Order> findOrderByIdAndUserId(Long orderId, Long userId);

    void deleteOrderByIdAndUserId(Long orderId, Long user_id);
}
