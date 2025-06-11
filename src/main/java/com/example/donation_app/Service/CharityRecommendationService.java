package com.example.donation_app.Service;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Repository.CharityRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CharityRecommendationService {

    private final RestTemplate restTemplate;
    private final CharityRepository charityRepository;
    private final String FLASK_API_URL = "http://127.0.0.1:5002/recommend";

    public CharityRecommendationService(CharityRepository charityRepository) {
        this.restTemplate = new RestTemplate();
        this.charityRepository = charityRepository;
    }

    public List<CharityDTO> getRecommendedCharitiesForDonor(Long donorId) {
        // 1. Prepare request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("donor_id", donorId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 2. Call the Flask API
        ResponseEntity<Map> response = restTemplate.exchange(
                FLASK_API_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class);

        // 3. Extract charity IDs from recommendations (ignore score)
        List<List<Object>> recommendations = (List<List<Object>>) response.getBody().get("recommendations");

        if (recommendations == null || recommendations.isEmpty())
            return Collections.emptyList();

        // 4. Map to CharityDTOs in the same order
        List<CharityDTO> result = new ArrayList<>();

        for (List<Object> rec : recommendations) {
            Long charityId = ((Number) rec.get(0)).longValue();
            Charity charity = charityRepository.findById(charityId).orElse(null);

            if (charity != null) {
                result.add(mapToDTO(charity));
            }
        }

        return result;
    }

    private CharityDTO mapToDTO(Charity charity) {
        CharityDTO dto = new CharityDTO();
        dto.setId(charity.getId());
        dto.setName(charity.getName());
        dto.setDescription(charity.getDescription());
        dto.setPhone(charity.getPhone());
        dto.setCity(charity.getCity());
        dto.setPreferredTypes(charity.getPreferredTypes());

        return dto;
    }
}
