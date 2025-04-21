package com.example.donation_app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Charity;

public interface CharityRepository extends JpaRepository<Charity, Long> {
    
    boolean existsByEmail(String email);

    Optional<Charity> findByEmail(String email);
}
