package com.example.donation_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Donor;

import java.util.List;
import java.util.Set;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByCharity(Charity charity);

    @Query("SELECT d FROM Donation d WHERE d.donor = :donor ORDER BY d.pickup.scheduledDate DESC")
    List<Donation> findByDonorOrderByPickupDateDesc(Donor donor);

    @Query("""
                SELECT d FROM Donation d
                WHERE d.donor.id = :donorId
                AND d.status = 'SCHEDULED'
                AND d.pickup.scheduledDate > CURRENT_TIMESTAMP
                ORDER BY d.pickup.scheduledDate ASC
            """)
    List<Donation> findUpcomingPickupForDonor(@Param("donorId") Long donorId);

    @Query("SELECT distinct d.type FROM Donation d WHERE d.donor.id = :donorId")
    Set<DonationType> findDonorDonationTypesByDonorId(Long donorId);

    Long countByDonorId(Long id);

    List<Donation> findByDonorIdAndStatusOrderByPickup_ScheduledDateDesc(Long donorId, DonationStatus status);
}
