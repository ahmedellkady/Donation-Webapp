package com.example.donation_app.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long donationId;
    
    private int rating;

    @Column(name = "feedback_comment")
    private String comment;

    @Column(name = "is_urgent")
    private int isUrgent; // 0 or 1

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(int isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Long getDonationId() {
        return donationId;
    }
    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }
    
}

