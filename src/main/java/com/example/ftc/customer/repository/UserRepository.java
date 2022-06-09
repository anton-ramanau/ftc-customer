package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
