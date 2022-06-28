package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderCommand implements Converter<Order, OrderCommand> {
    private final UserToUserCommand userToUserCommand;

    public OrderToOrderCommand(UserToUserCommand userToUserCommand) {
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public OrderCommand convert(Order order) {
        if (order == null) {
            return null;
        }
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setId(order.getId());
        orderCommand.setUser(userToUserCommand.convert(order.getUser()));
        orderCommand.setOrderStatus(order.getOrderStatus());
        orderCommand.setPrice(order.getPrice());
        return orderCommand;
    }
}
