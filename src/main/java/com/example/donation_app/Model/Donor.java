package com.example.donation_app.Model;

import java.time.LocalDateTime;
import java.util.*;

import com.example.donation_app.Enum.DonationType;

import jakarta.persistence.*;

@Entity
@Table(name = "donors")
public class Donor extends User {

    private String city;

    private String neighborhood;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @ElementCollection(targetClass = DonationType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "donor_preferred_types", joinColumns = @JoinColumn(name = "donor_id"))
    @Column(name = "donation_type")
    private Set<DonationType> preferredTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donor")
    private List<Donation> donations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donor")
    private List<DonorActivity> activities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donor")
    private List<DonorSession> sessions;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Set<DonationType> getPreferredTypes() {
        return preferredTypes;
    }

    public void setPreferredTypes(Set<DonationType> preferredTypes) {
        this.preferredTypes = preferredTypes;
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
