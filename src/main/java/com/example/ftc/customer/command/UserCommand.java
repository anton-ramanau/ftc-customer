package com.example.ftc.customer.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommand {

    public UserCommand() {
    }

    private Long id;
    private String username;
}
