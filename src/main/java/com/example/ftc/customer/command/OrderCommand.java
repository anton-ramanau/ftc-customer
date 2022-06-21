package com.example.ftc.customer.command;

import com.example.ftc.customer.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderCommand {

    public OrderCommand() {
    }

    private Long id;
    private UserCommand user;
    private Set<CargoCommand> cargos;
    private OrderStatus orderStatus;
    private Double price;
}