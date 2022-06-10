package com.example.ftc.customer.converter;

import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.command.UserCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    @Override
    public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }
        User user = new User();
        user.setId(userCommand.getId());
        user.setUsername(userCommand.getName());
        return user;
    }
}
