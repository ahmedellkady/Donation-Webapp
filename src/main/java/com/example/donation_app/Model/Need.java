package com.example.donation_app.Model;

import java.time.LocalDateTime;

import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.example.donation_app.Enum.VerificationStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "needs")
public class Need {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "need_type")
    private DonationType type;

    private Integer quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus status;

    @Enumerated(EnumType.STRING)
    private NeedUrgency urgency;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DonationType getType() {
        return type;
    }

    public void setType(DonationType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public NeedUrgency getUrgency() {
        return urgency;
    }

    public void setUrgency(NeedUrgency urgency) {
        this.urgency = urgency;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    
}

