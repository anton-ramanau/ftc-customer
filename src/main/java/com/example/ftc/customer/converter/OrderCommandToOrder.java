package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandToOrder implements Converter<OrderCommand, Order> {
    private final UserCommandToUser userCommandToUser;
    private final CargoCommandToCargo cargoCommandToCargo;

    public OrderCommandToOrder(UserCommandToUser userCommandToUser, CargoCommandToCargo cargoCommandToCargo) {
        this.userCommandToUser = userCommandToUser;
        this.cargoCommandToCargo = cargoCommandToCargo;
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

        if (orderCommand.getCargos() != null && orderCommand.getCargos().size() > 0) {
            orderCommand.getCargos().forEach(cargoCommand -> order.getCargos().add(cargoCommandToCargo.convert(cargoCommand)));
        }

        return order;
    }
}
