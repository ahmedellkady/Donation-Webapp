package com.example.donation_app.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.donation_app.Enum.DonationType;

public class DonorDTO extends UserDTO {
    private String city;
    private String neighborhood;
    private LocalDateTime registeredAt;
    private Set<DonationType> preferredTypes;
    
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

    
}
