package com.example.donation_app.Model;

import jakarta.persistence.*;

import java.util.*;

import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.VerificationStatus;

@Entity
@Table(name = "charities")
public class Charity extends User {

    private String city;

    private String description;

    @ElementCollection(targetClass = DonationType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "charity_preferred_types", joinColumns = @JoinColumn(name = "charity_id"))
    @Column(name = "donation_type")
    private Set<DonationType> preferredTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "charity")
    private List<Need> needs;

    @ElementCollection
    @CollectionTable(name = "charity_uploaded_documents", joinColumns = @JoinColumn(name = "charity_id"))
    @Column(name = "document")
    private List<String> uploadedDocuments;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus status;

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations;

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonorActivity> activities;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DonationType> getPreferredTypes() {
        return preferredTypes;
    }

    public void setPreferredTypes(Set<DonationType> preferredTypes) {
        this.preferredTypes = preferredTypes;
    }

    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    public List<String> getUploadedDocuments() {
        return uploadedDocuments;
    }

    public void setUploadedDocuments(List<String> uploadedDocuments) {
        this.uploadedDocuments = uploadedDocuments;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<DonorActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<DonorActivity> activities) {
        this.activities = activities;
    }

    
}

