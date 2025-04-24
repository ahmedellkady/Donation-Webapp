package com.example.donation_app.DTO;

import java.time.LocalDate;
import java.util.*;

import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Enum.DonationType;

public class DonationDetailsDTO {
    private Long id;
    private DonationType type;
    private Integer quantity;
    private List<String> donationItems;
    private DonationStatus status;
    private LocalDate expiryDate;
    private FeedbackDTO donorFeedback;
    private FeedbackDTO charityFeedback;
    private String donorName;
    private String charityName;
    private PickupDTO pickup;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public List<String> getDonationItems() {
        return donationItems;
    }
    public void setDonationItems(List<String> donationItems) {
        this.donationItems = donationItems;
    }
    public DonationStatus getStatus() {
        return status;
    }
    public void setStatus(DonationStatus status) {
        this.status = status;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCharityName() {
        return charityName;
    }
    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }
    public String getDonorName() {
        return donorName;
    }
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    public FeedbackDTO getDonorFeedback() {
        return donorFeedback;
    }
    public void setDonorFeedback(FeedbackDTO donorFeedback) {
        this.donorFeedback = donorFeedback;
    }
    public FeedbackDTO getCharityFeedback() {
        return charityFeedback;
    }
    public void setCharityFeedback(FeedbackDTO charityFeedback) {
        this.charityFeedback = charityFeedback;
    }
    public PickupDTO getPickup() {
        return pickup;
    }
    public void setPickup(PickupDTO pickup) {
        this.pickup = pickup;
    }
    
}
