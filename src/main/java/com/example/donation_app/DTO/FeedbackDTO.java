package com.example.donation_app.DTO;

public class FeedbackDTO {
    private Long donationId;
    private int rating;
    private String comment;
    private String forRole;
    private int isUrgent;
    
    public Long getDonationId() {
        return donationId;
    }
    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getForRole() {
        return forRole;
    }
    public void setForRole(String forRole) {
        this.forRole = forRole;
    }

    public int getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(int isUrgent) {
        this.isUrgent = isUrgent;
    }
}
