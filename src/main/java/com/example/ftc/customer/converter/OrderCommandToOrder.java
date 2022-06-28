package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandToOrder implements Converter<OrderCommand, Order> {
    private final UserCommandToUser userCommandToUser;

    public OrderCommandToOrder(UserCommandToUser userCommandToUser) {
        this.userCommandToUser = userCommandToUser;
    }

    @Override
    public Order convert(OrderCommand orderCommand) {
        if (orderCommand == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderCommand.getId());
        order.setOrderStatus(orderCommand.getOrderStatus());
        order.setUser(userCommandToUser.convert(orderCommand.getUser()));
        order.setPrice(orderCommand.getPrice());
        return order;
    }
}
