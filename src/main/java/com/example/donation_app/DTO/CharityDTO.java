package com.example.donation_app.DTO;

import java.util.List;
import java.util.Set;

public class CharityDTO extends UserDTO {
    private String city;
    private String neighborhood;
    private Set<String> preferredTypes;
    private List<String> uploadedDocuments;
    private String status;
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
    public Set<String> getPreferredTypes() {
        return preferredTypes;
    }
    public void setPreferredTypes(Set<String> preferredTypes) {
        this.preferredTypes = preferredTypes;
    }
    public List<String> getUploadedDocuments() {
        return uploadedDocuments;
    }
    public void setUploadedDocuments(List<String> uploadedDocuments) {
        this.uploadedDocuments = uploadedDocuments;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
