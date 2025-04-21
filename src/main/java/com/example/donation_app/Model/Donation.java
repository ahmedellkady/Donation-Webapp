package com.example.donation_app.Model;

import java.time.LocalDate;
import java.util.List;

import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Enum.DonationType;

import jakarta.persistence.*;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "donation_type")
    private DonationType type;

    private Integer quantity;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "donation_status")
    private DonationStatus status;

    @ElementCollection
    @CollectionTable(name = "donation_items", joinColumns = @JoinColumn(name = "donation_id"))
    @Column(name = "item")
    private List<String> donationItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_feedback_id")
    private Feedback donorFeedback;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charity_feedback_id")
    private Feedback charityFeedback;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_id")
    private Pickup pickup;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }

    public List<String> getDonationItems() {
        return donationItems;
    }

    public void setDonationItems(List<String> donationItems) {
        this.donationItems = donationItems;
    }

    public Feedback getDonorFeedback() {
        return donorFeedback;
    }

    public void setDonorFeedback(Feedback donorFeedback) {
        this.donorFeedback = donorFeedback;
    }

    public Feedback getCharityFeedback() {
        return charityFeedback;
    }

    public void setCharityFeedback(Feedback charityFeedback) {
        this.charityFeedback = charityFeedback;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;
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

    

}

