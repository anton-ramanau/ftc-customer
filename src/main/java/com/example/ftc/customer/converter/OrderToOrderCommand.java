package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderCommand implements Converter<Order, OrderCommand> {
    private final UserToUserCommand userToUserCommand;
    private final CargoToCargoCommand cargoToCargoCommand;

    public OrderToOrderCommand(UserToUserCommand userToUserCommand, CargoToCargoCommand cargoToCargoCommand) {
        this.userToUserCommand = userToUserCommand;
        this.cargoToCargoCommand = cargoToCargoCommand;
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

        if (order.getCargos() != null && order.getCargos().size() > 0) {
            order.getCargos().forEach(cargo -> orderCommand.getCargos().add(cargoToCargoCommand.convert(cargo)));
        }
        return orderCommand;
    }
}
