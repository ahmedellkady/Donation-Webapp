package com.example.donation_app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.DTO.LoginDTO;
import com.example.donation_app.DTO.RegisterCharityDTO;
import com.example.donation_app.Enum.Role;
import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Exception.EmailAlreadyUsedException;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.ResourceNotFoundException;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Repository.CharityRepository;

@Service
public class CharityService {
    
    private final CharityRepository charityRepository;

    public CharityService(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    private CharityDTO map(Charity charity) {
        CharityDTO dto = new CharityDTO();

        dto.setId(charity.getId());
        dto.setName(charity.getName());
        dto.setEmail(charity.getEmail());
        dto.setPhone(charity.getPhone());
        dto.setCity(charity.getCity());
        dto.setNeighborhood(charity.getNeighborhood());
        dto.setPreferredTypes(charity.getPreferredTypes());
        dto.setStatus(charity.getStatus());

        return dto;
    }

    public CharityDTO registerCharity(RegisterCharityDTO dto) {
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

        charityRepository.save(charity);

        return map(charity);
    }

    public CharityDTO loginCharity(LoginDTO dto) {
        Charity charity = charityRepository.findByEmail(dto.getEmail())
          .orElseThrow(() -> new ResourceNotFoundException("Charity not found"));

        if (!charity.getPassword().equals(dto.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return map(charity);
    }

    public List<CharityDTO> getPendingCharities() {
        List<Charity> charities = charityRepository.findByStatus(VerificationStatus.PENDING);

        if (charities.isEmpty()) {
            throw new ResourceNotFoundException("No pending charities found");
        }

        return charities.stream().map(this::map).collect(Collectors.toList());
    }

    public void deleteCharity(Long charityId) {
        charityRepository.deleteById(charityId);
    }
}
