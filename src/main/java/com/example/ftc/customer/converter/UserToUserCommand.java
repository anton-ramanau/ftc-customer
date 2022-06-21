package com.example.ftc.customer.converter;

import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.command.UserCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    @Override
    public UserCommand convert(User user) {
        if (user == null) {
            return null;
        }
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername(user.getUsername());
        return userCommand;
    }
}
