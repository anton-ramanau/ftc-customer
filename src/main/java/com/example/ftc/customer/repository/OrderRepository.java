package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findOrdersByUserId(Long userId);

    Optional <Order> findOrderByIdAndUserId(Long orderId, Long userId);

    @Transactional
    void deleteOrderByIdAndUserId(Long orderId, Long user_id);

}
