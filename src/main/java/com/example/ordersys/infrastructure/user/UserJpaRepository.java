package com.example.ordersys.infrastructure.user;

import com.example.ordersys.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
}