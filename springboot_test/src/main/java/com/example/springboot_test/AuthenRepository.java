package com.example.springboot_test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenRepository extends JpaRepository<AuthenEntity, Integer> {
    AuthenEntity findByUsername(String username);
    Boolean existsByUsername(String username);
}
