package com.example.donation_app.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.donation_app.Enum.DonationType;

public class CreateDonationDTO {
    private DonationType type;
    private Integer quantity;
    private LocalDate expiryDate;
    private List<String> donationItems;
    private Long charityId;
    private PickupDTO pickup;
    
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
    public List<String> getDonationItems() {
        return donationItems;
    }
    public void setDonationItems(List<String> donationItems) {
        this.donationItems = donationItems;
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

    
}
