package com.mstradingcards.ServiceAuth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mstradingcards.ServiceAuth.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Optional<Long> getUserIdByUsername(@Param("username") String username);		
}

