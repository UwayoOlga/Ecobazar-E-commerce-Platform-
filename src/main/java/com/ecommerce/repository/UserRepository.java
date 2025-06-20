package com.ecommerce.repository;

import com.ecommerce.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {
    // Custom query example:
    Optional<user> findByEmail(String email);
    boolean existsByEmail(String email);
}

