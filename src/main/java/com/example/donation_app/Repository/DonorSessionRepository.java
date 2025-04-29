package com.example.donation_app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.donation_app.Model.DonorSession;

public interface DonorSessionRepository extends JpaRepository<DonorSession, Long> {
    
    @Query("SELECT d FROM DonorSession d WHERE d.donor.id = :donorId ORDER BY d.sessionStart DESC")
    List<DonorSession> findSessionsForDonor(Long donorId);
}
