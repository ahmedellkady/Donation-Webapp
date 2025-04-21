package com.example.donation_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Pickup;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
    
}
