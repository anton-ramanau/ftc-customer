package com.example.ftc.customer.command;

import com.example.ftc.customer.domain.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class OrderCommand {

    public OrderCommand() {
    }

    private Long id;

    @NotBlank
    private String name;
    private UserCommand user;
    private OrderStatus orderStatus;
    private Double price;
}
