package com.example.donation_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.DonorActivity;

public interface DonorActivityRepository extends JpaRepository<DonorActivity, Long> {
    
}
