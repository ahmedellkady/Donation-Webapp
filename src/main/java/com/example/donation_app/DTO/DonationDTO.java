package com.example.donation_app.DTO;

import java.time.LocalDate;
import java.util.List;

public class DonationDTO {
    private Long id;
    private String type;
    private Integer quantity;
    private LocalDate expiryDate;
    private String status;
    private List<String> donationItems;
    private FeedbackDTO donorFeedback;
    private FeedbackDTO charityFeedback;
    private PickupDTO pickup;
    private Long donorId;
    private Long charityId;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<String> getDonationItems() {
        return donationItems;
    }
    public void setDonationItems(List<String> donationItems) {
        this.donationItems = donationItems;
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
    public Long getDonorId() {
        return donorId;
    }
    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }
    public Long getCharityId() {
        return charityId;
    }
    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }

    
}
