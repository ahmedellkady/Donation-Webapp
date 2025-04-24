package com.example.donation_app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Need;

public interface NeedRepository extends JpaRepository<Need, Long> {
    
    List<Need> findByStatus(VerificationStatus status);

    List<Need> findByCharity(Charity charity);
}
