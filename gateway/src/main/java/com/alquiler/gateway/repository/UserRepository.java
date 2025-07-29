package com.alquiler.gateway.repository;

import com.alquiler.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}
