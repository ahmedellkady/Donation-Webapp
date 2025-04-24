package com.example.donation_app.Service;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.ChangePasswordDTO;
import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.DTO.LoginDTO;
import com.example.donation_app.DTO.RegisterDonorDTO;
import com.example.donation_app.DTO.UpdateDonorProfileDTO;
import com.example.donation_app.Enum.Role;
import com.example.donation_app.Exception.EmailAlreadyUsedException;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.UserNotFoundException;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Repository.DonorRepository;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    private DonorDTO map(Donor donor) {
        DonorDTO dto = new DonorDTO();
        dto.setId(donor.getId());
        dto.setName(donor.getName());
        dto.setEmail(donor.getEmail());
        dto.setPhone(donor.getPhone());
        dto.setCity(donor.getCity());
        dto.setNeighborhood(donor.getNeighborhood());
        dto.setRegisteredAt(donor.getRegisteredAt());
        dto.setPreferredTypes(donor.getPreferredTypes());
        return dto;
    }

    public DonorDTO registerDonor(RegisterDonorDTO dto) {
        if (donorRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException("Email already used");
        }

        Donor donor = new Donor();
        donor.setName(dto.getName());
        donor.setEmail(dto.getEmail());
        donor.setPhone(dto.getPhone());
        donor.setPassword(dto.getPassword());
        donor.setCity(dto.getCity());
        donor.setNeighborhood(dto.getNeighborhood());
        donor.setPreferredTypes(dto.getPreferredTypes());
        donor.setRegisteredAt(java.time.LocalDateTime.now());
        donor.setRole(Role.DONOR);
    
        donorRepository.save(donor);

        return map(donor);
    }
    
    public DonorDTO loginDonor(LoginDTO dto) {
        Donor donor = donorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!donor.getPassword().equals(dto.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return map(donor);
    }

    public DonorDTO updateDonorProfile(Long donorId, UpdateDonorProfileDTO dto) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        donor.setName(dto.getName());
        donor.setPhone(dto.getPhone());
        donor.setCity(dto.getCity());
        donor.setNeighborhood(dto.getNeighborhood());
        donor.setPreferredTypes(dto.getPreferredTypes());

        donorRepository.save(donor);

        return map(donor);
    }

    public void changePassword(Long donorId, ChangePasswordDTO dto) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!donor.getPassword().equals(dto.getCurrentPassword())) {
            throw new InvalidCredentialsException("Invalid old password");
        }

        if (dto.getCurrentPassword().equals(dto.getNewPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        donor.setPassword(dto.getNewPassword());
        donorRepository.save(donor);
    }

    public void deleteDonor(Long donorId) {
        donorRepository.deleteById(donorId);
    }

}
