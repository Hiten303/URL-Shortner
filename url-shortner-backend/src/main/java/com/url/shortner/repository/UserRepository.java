package com.url.shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortner.entities.user;

public interface UserRepository extends JpaRepository<user,Long> {
    Optional<user> findByUsername(String username);
}
