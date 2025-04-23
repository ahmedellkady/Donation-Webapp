package com.example.donation_app.DTO;

import java.time.LocalDateTime;

import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.example.donation_app.Enum.VerificationStatus;

public class NeedDTO {
    private Long id;
    private String description;
    private DonationType type;
    private Integer quantity;
    private LocalDateTime createdAt;
    private VerificationStatus status;
    private NeedUrgency urgency;
    private Long charityId;
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
    public Long getCharityId() {
        return charityId;
    }
    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }

    
}
