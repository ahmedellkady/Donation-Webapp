package com.example.donation_app.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.example.donation_app.Enum.VerificationStatus;
import com.example.donation_app.Exception.ResourceNotFoundException;
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

    private NeedDTO map(Need need) {
        NeedDTO dto = new NeedDTO();
        dto.setId(need.getId());
        dto.setDescription(need.getDescription());
        dto.setType(need.getType());
        dto.setQuantity(need.getQuantity());
        dto.setCreatedAt(need.getCreatedAt());
        dto.setStatus(need.getStatus());
        dto.setUrgency(need.getUrgency());
        dto.setCharityName(need.getCharity().getName());
        dto.setCity(need.getCharity().getCity());
        dto.setCharityId(need.getCharity().getId());

        return dto;
    }

    public NeedDTO postNeed(Long charityId, NeedDTO dto) {
        Charity charity = charityRepository.findById(charityId).orElse(null);

        Need need = new Need();
        need.setDescription(dto.getDescription());
        need.setType(dto.getType());
        need.setQuantity(dto.getQuantity());
        need.setCreatedAt(LocalDateTime.now());
        need.setStatus(VerificationStatus.PENDING);
        need.setUrgency(dto.getUrgency());
        need.setCharity(charity);

        needRepository.save(need);
        return map(need);
    }

    public NeedDTO getNeed(Long id) {
        Need need = needRepository.findById(id).orElse(null);

        if (need == null) {
            throw new ResourceNotFoundException("Need not found");
        }

        return map(need);
    }

    public List<NeedDTO> getNeeds() {
        List<Need> needs = needRepository.findByStatusOrderByIdDesc(VerificationStatus.APPROVED);

        if (needs.isEmpty()) {
            throw new ResourceNotFoundException("No needs found");
        }

        return needs.stream().map(this::map).toList();
    }

    public List<NeedDTO> filterNeeds(String city, DonationType category, NeedUrgency urgency) {
        List<Need> needs = needRepository.findByStatusOrderByIdDesc(VerificationStatus.APPROVED);

        List<Need> filtered = needs.stream()
                .filter(n -> n != null)
                .filter(n -> city == null || (n.getCharity() != null && n.getCharity().getCity() != null
                        && n.getCharity().getCity().equalsIgnoreCase(city)))
                .filter(n -> category == null || (n.getType() != null && n.getType().equals(category)))
                .filter(n -> urgency == null || (n.getUrgency() != null && n.getUrgency().equals(urgency)))
                .toList();

        return filtered.stream().map(this::map).toList();
    }

    public List<NeedDTO> getPendingNeeds() {
        List<Need> needs = needRepository.findByStatusOrderByIdDesc(VerificationStatus.PENDING);
        return needs.stream().map(this::map).toList();
    }

    public List<NeedDTO> getNeedsForCharity(Long charityId) {
        Charity charity = charityRepository.findById(charityId).orElse(null);
        List<Need> needs = needRepository.findByCharityOrderByIdDesc(charity);

        if (needs.isEmpty()) {
            throw new ResourceNotFoundException("No needs found for this charity");
        }

        return needs.stream().map(this::map).toList();
    }

    public List<NeedDTO> getActiveNeedsForCharity(Long charityId) {
        Charity charity = charityRepository.findById(charityId)
                .orElseThrow(() -> new ResourceNotFoundException("Charity not found"));

        List<Need> activeNeeds = needRepository.findByCharityAndStatusOrderByIdDesc(charity, VerificationStatus.APPROVED);

        if (activeNeeds.isEmpty()) {
            throw new ResourceNotFoundException("No active needs found for this charity");
        }

        return activeNeeds.stream().map(this::map).toList();
    }

    public long countActiveNeedsForCharity(Long charityId) {
        Charity charity = charityRepository.findById(charityId)
                .orElseThrow(() -> new ResourceNotFoundException("Charity not found"));

        return needRepository.countByCharityAndStatus(charity, VerificationStatus.APPROVED);
    }

    public boolean deleteNeed(Long id) {
        Need need = needRepository.findById(id).orElse(null);

        if (need == null) {
            throw new ResourceNotFoundException("Need not found");
        }

        needRepository.delete(need);
        
        return true;
    }

}
