package com.example.donation_app.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.donation_app.DTO.DonorPredictionRequest;
import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.Need;
import com.example.donation_app.Repository.DonationRepository;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.NeedRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class NeedMatchingService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final NeedRepository needRepository;
    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;

    public NeedMatchingService(DonorRepository donorRepository, DonationRepository donationRepository,
            NeedRepository needRepository) {
        this.donorRepository = donorRepository;
        this.donationRepository = donationRepository;
        this.needRepository = needRepository;
    }

    static class FlaskResponse {
        @JsonProperty("needID")
        private Long id;

        @JsonProperty("PredictedScore")
        private String predictedScore;
    }

    public Page<NeedDTO> getSuggestedNeedsForDonor(Long donorId, int page, int size) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        DonorPredictionRequest requestPayload = new DonorPredictionRequest();
        requestPayload.setDonorCity(donor.getCity());
        requestPayload.setDonorPreferences(donor.getPreferredTypes());
        requestPayload.setDonorDonationTypes(donationRepository.findDonorDonationTypesByDonorId(donorId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DonorPredictionRequest> entity = new HttpEntity<>(requestPayload, headers);
        ResponseEntity<FlaskResponse[]> response = restTemplate.postForEntity(
                "http://localhost:5000/predict",
                entity,
                FlaskResponse[].class);

        FlaskResponse[] predictions = response.getBody();

        if (predictions == null || predictions.length == 0) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        }

        Map<Long, String> scoreMap = Arrays.stream(predictions)
                .collect(Collectors.toMap(p -> p.id, p -> p.predictedScore));

        List<Need> needs = needRepository.findAllById(scoreMap.keySet());

        List<NeedDTO> sortedDtos = needs.stream().map(need -> {
            NeedDTO dto = new NeedDTO();
            dto.setId(need.getId());
            dto.setDescription(need.getDescription());
            dto.setType(need.getType());
            dto.setQuantity(need.getQuantity());
            dto.setUrgency(need.getUrgency());
            dto.setCharityName(need.getCharity().getName());
            dto.setCity(need.getCharity().getCity());
            dto.setPredictedScore(scoreMap.get(need.getId()));
            return dto;
        }).sorted((a, b) -> {
            double scoreA = Double.parseDouble(a.getPredictedScore().replace("%", ""));
            double scoreB = Double.parseDouble(b.getPredictedScore().replace("%", ""));
            return Double.compare(scoreB, scoreA);
        }).toList();

        int start = Math.min(page * size, sortedDtos.size());
        int end = Math.min(start + size, sortedDtos.size());

        List<NeedDTO> pageContent = sortedDtos.subList(start, end);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), sortedDtos.size());
    }
}
