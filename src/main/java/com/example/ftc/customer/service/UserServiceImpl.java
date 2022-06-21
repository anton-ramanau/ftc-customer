package com.example.ftc.customer.service;

import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.converter.UserToUserCommand;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository userRepository, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
       return userRepository.findUserByUsername(username);
    }

    @Override
    public UserCommand findUserCommandByUsername(String username) {
        return userToUserCommand.convert(findUserByUsername(username));
    }

    @Override
    public UserCommand findUserCommandById(Long userId) {
        User user = findUserById(userId);
        return userToUserCommand.convert(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
