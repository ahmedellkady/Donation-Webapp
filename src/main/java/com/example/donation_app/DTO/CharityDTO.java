package com.example.donation_app.DTO;

import java.util.List;
import java.util.Set;

import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.VerificationStatus;

public class CharityDTO extends UserDTO {
    private String city;
    private String description;
    private Set<DonationType> preferredTypes;
    private List<String> uploadedDocuments;
    private VerificationStatus status;
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

    
}
