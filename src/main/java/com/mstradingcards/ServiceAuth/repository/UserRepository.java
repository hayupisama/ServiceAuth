package com.mstradingcards.ServiceAuth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mstradingcards.ServiceAuth.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // Add custom queries as needed
}

