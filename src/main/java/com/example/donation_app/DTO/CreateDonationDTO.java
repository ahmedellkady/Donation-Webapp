package com.example.donation_app.DTO;

import java.time.LocalDate;

import com.example.donation_app.Enum.DonationType;

public class CreateDonationDTO {
    private DonationType type;
    private Integer quantity;
    private LocalDate expiryDate;
    private String description;
    private Long charityId;
    private PickupDTO pickup;
    private Long needId;
    
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
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getCharityId() {
        return charityId;
    }
    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }
    public PickupDTO getPickup() {
        return pickup;
    }
    public void setPickup(PickupDTO pickup) {
        this.pickup = pickup;
    }
    public Long getNeedId() {
        return needId;
    }
    public void setNeedId(Long needId) {
        this.needId = needId;
    }
}
