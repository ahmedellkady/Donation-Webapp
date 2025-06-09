package com.example.donation_app.Model;

import java.time.LocalDateTime;

import com.example.donation_app.Enum.ActionType;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "donor_activities")
public class DonorActivity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private DonorSession session;

    @Enumerated(EnumType.STRING)
    private DonationType needType;

    @Enumerated(EnumType.STRING)
    private NeedUrgency needUrgency;

    private Integer quantityDonated;

    private LocalDateTime needCreatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DonorSession getSession() {
        return session;
    }

    public void setSession(DonorSession session) {
        this.session = session;
    }

    public DonationType getNeedType() {
        return needType;
    }

    public void setNeedType(DonationType needType) {
        this.needType = needType;
    }

    public NeedUrgency getNeedUrgency() {
        return needUrgency;
    }

    public void setNeedUrgency(NeedUrgency needUrgency) {
        this.needUrgency = needUrgency;
    }

    public Integer getQuantityDonated() {
        return quantityDonated;
    }

    public void setQuantityDonated(Integer quantityDonated) {
        this.quantityDonated = quantityDonated;
    }

    public LocalDateTime getNeedCreatedAt() {
        return needCreatedAt;
    }

    public void setNeedCreatedAt(LocalDateTime needCreatedAt) {
        this.needCreatedAt = needCreatedAt;
    }

    

}
