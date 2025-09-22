package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LoginRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailStartingWithIgnoreCase(String email);
    boolean existsByEmail(String email);
}