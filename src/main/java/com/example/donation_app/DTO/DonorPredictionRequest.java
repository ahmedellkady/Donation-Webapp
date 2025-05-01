package com.example.donation_app.DTO;

import java.util.Set;

import com.example.donation_app.Enum.DonationType;

public class DonorPredictionRequest {
    private String donorCity;
    private Set<DonationType> donorPreferences;
    private Set<DonationType> donorDonationTypes;
    public String getDonorCity() {
        return donorCity;
    }
    public void setDonorCity(String donorCity) {
        this.donorCity = donorCity;
    }
    public Set<DonationType> getDonorPreferences() {
        return donorPreferences;
    }
    public void setDonorPreferences(Set<DonationType> donorPreferences) {
        this.donorPreferences = donorPreferences;
    }
    public Set<DonationType> getDonorDonationTypes() {
        return donorDonationTypes;
    }
    public void setDonorDonationTypes(Set<DonationType> donorDonationTypes) {
        this.donorDonationTypes = donorDonationTypes;
    }

    
}
