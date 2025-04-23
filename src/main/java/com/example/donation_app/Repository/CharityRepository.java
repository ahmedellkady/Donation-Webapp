package com.example.donation_app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Charity;
import java.util.List;
import com.example.donation_app.Enum.VerificationStatus;


public interface CharityRepository extends JpaRepository<Charity, Long> {
    
    boolean existsByEmail(String email);

    Optional<Charity> findByEmail(String email);

    List<Charity> findByStatus(VerificationStatus status);
}
