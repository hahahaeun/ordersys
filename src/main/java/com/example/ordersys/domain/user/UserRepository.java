package com.example.ordersys.domain.user;

import com.example.ordersys.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository{
    User save(User user);
    Optional<User> findByUserId(String userId);
}
