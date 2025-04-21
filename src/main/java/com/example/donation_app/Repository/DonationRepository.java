package com.example.donation_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Donor;

import java.util.List;


public interface DonationRepository extends JpaRepository<Donation, Long> {
    
    List<Donation> findByCharity(Charity charity);

    List<Donation> findByDonor(Donor donor);
}
