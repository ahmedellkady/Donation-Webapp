package com.example.donation_app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);

    boolean existsById(Long id);

}
