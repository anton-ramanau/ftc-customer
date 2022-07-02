package com.example.ftc.customer.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserCommand {

    public UserCommand() {
    }

    private Long id;
    private String username;
}
