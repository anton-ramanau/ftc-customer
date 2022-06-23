package com.example.ftc.customer.service;

import com.example.ftc.customer.FtcCustomerApplication;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.repository.OrderRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Test
    void findOrderByIdAndUserId() {
        Order order = orderService.findOrderByIdAndUserId(1L, 4L);
        assertEquals(1l, order.getId());
        assertEquals(4l, order.getUser().getId());
    }

    @Test
    void deleteByIdAndUserId() {
        orderService.deleteOrderByIdAndUserId(1L, 4L);
    }
}