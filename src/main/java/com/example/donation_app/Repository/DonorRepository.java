package com.example.donation_app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    
    boolean existsByEmail(String email);

    Optional<Donor> findByEmail(String email);
}
