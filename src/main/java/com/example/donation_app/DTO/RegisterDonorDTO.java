package com.example.donation_app.DTO;

import java.util.Set;

import com.example.donation_app.Enum.DonationType;

public class RegisterDonorDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String city;
    private String neighborhood;
    private Set<DonationType> preferredTypes;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
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
    public Set<DonationType> getPreferredTypes() {
        return preferredTypes;
    }
    public void setPreferredTypes(Set<DonationType> preferredTypes) {
        this.preferredTypes = preferredTypes;
    }

    
}
