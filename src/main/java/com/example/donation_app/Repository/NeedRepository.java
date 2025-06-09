package com.example.donation_app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Need;

public interface NeedRepository extends JpaRepository<Need, Long> {
    
    List<Need> findByStatusOrderByIdDesc(VerificationStatus status);

    List<Need> findByCharityOrderByIdDesc(Charity charity);

    List<Need> findByCharityAndStatusOrderByIdDesc(Charity charity, VerificationStatus approved);

    long countByCharityAndStatus(Charity charity, VerificationStatus approved);
}
