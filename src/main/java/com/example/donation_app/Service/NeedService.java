package com.example.donation_app.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Need;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.NeedRepository;

@Service
public class NeedService {
    
    private final NeedRepository needRepository;
    private final CharityRepository charityRepository;

    public NeedService(NeedRepository needRepository, CharityRepository charityRepository) {
        this.needRepository = needRepository;
        this.charityRepository = charityRepository;
    }

    public Need postNeed(Long charityId, NeedDTO dto) {
        Charity charity = charityRepository.findById(charityId).orElse(null);

        Need need = new Need();
        need.setDescription(dto.getDescription());
        need.setType(dto.getType());
        need.setQuantity(dto.getQuantity());
        need.setCreatedAt(LocalDateTime.now());
        need.setStatus(VerificationStatus.PENDING);
        need.setUrgency(dto.getUrgency());
        need.setCharity(charity);

        return needRepository.save(need);

    }

    public List<Need> getPendingNeeds() {
        return needRepository.findByStatus(VerificationStatus.PENDING);
    }
}
