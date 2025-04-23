package com.example.donation_app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.Enum.Role;
import com.example.donation_app.Exception.EmailAlreadyUsedException;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.UserNotFoundException;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Repository.DonorRepository;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor registerDonor(DonorDTO dto) {
        Donor donor = new Donor();

        if (donorRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException("Email already used");
        }

        donor.setName(dto.getName());
        donor.setEmail(dto.getEmail());
        donor.setPhone(dto.getPhone());
        donor.setPassword(dto.getPassword());
        donor.setCity(dto.getCity());
        donor.setNeighborhood(dto.getNeighborhood());
        donor.setPreferredTypes(dto.getPreferredTypes());
        donor.setRegisteredAt(java.time.LocalDateTime.now());
        donor.setRole(Role.DONOR);
    
        return donorRepository.save(donor);
    }
    
    public Donor loginDonor(DonorDTO dto) {
        Donor donor = donorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!donor.getPassword().equals(dto.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return donor;
    }

}
