package com.example.donation_app.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Need;

public interface NeedRepository extends JpaRepository<Need, Long> {

    List<Need> findByStatusOrderByIdDesc(VerificationStatus status);

    List<Need> findByCharityOrderByIdDesc(Charity charity);

    List<Need> findByCharityAndStatusOrderByIdDesc(Charity charity, VerificationStatus approved);

    long countByCharityAndStatus(Charity charity, VerificationStatus approved);

    @Query("SELECT n FROM Need n WHERE " +
            "n.status = :status AND " +
            "(:city IS NULL OR LOWER(n.charity.city) = LOWER(:city)) AND " +
            "(:category IS NULL OR n.type = :category) AND " +
            "(:urgency IS NULL OR n.urgency = :urgency)" + "ORDER BY n.id DESC")
    Page<Need> findFiltered(@Param("city") String city,
            @Param("category") DonationType category,
            @Param("urgency") NeedUrgency urgency,
            @Param("status") VerificationStatus status,
            Pageable pageable);

}
