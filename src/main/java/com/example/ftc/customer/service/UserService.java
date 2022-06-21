package com.example.ftc.customer.service;

import com.example.ftc.customer.command.UserCommand;
import com.example.ftc.customer.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User save(User user);

    User findUserByUsername(String username);

    UserCommand findUserCommandByUsername(String username);
}
