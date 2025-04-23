package com.example.donation_app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.Enum.Role;
import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Exception.EmailAlreadyUsedException;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.UserNotFoundException;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Repository.CharityRepository;

@Service
public class CharityService {
    
    private final CharityRepository charityRepository;

    public CharityService(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    public Charity registerCharity(CharityDTO dto) {
        if (charityRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException("Email already used");
        }

        Charity charity = new Charity();

        charity.setName(dto.getName());
        charity.setEmail(dto.getEmail());
        charity.setPhone(dto.getPhone());
        charity.setPassword(dto.getPassword());
        charity.setCity(dto.getCity());
        charity.setNeighborhood(dto.getNeighborhood());
        charity.setPreferredTypes(dto.getPreferredTypes());
        charity.setStatus(VerificationStatus.PENDING);
        charity.setRole(Role.CHARITY);

        return charityRepository.save(charity);
    }

    public Charity loginCharity(CharityDTO dto) {
        Charity charity = charityRepository.findByEmail(dto.getEmail())
          .orElseThrow(() -> new UserNotFoundException("Charity not found"));

        if (!charity.getPassword().equals(dto.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return charity;
    }

    public List<Charity> getPendingCharities() {
        return charityRepository.findByStatus(VerificationStatus.PENDING);
    }
}
