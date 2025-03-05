package com.example.auth_service.Repository;

import com.example.auth_service.Entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findByUserName(String userName);
}
