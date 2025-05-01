package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.NeedDTO;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.example.donation_app.Service.NeedMatchingService;
import com.example.donation_app.Service.NeedService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/needs")
public class NeedController {

    private final NeedService needService;
    private final NeedMatchingService matchingService;

    public NeedController(NeedService needService, NeedMatchingService matchingService) {
        this.needService = needService;
        this.matchingService = matchingService;
    }

    @PostMapping("/post/{charityId}")
    public ResponseEntity<NeedDTO> postMethodName(@PathVariable Long charityId, @RequestBody NeedDTO dto) {
        NeedDTO need = needService.postNeed(charityId, dto);
        return ResponseEntity.ok(need);
    }

    @GetMapping("/all-needs")
    public ResponseEntity<List<NeedDTO>> getNeeds() {
        List<NeedDTO> needs = needService.getNeeds();
        return ResponseEntity.ok(needs);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<NeedDTO>> filterNeeds(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String urgency) {

        DonationType donationType = null;
        NeedUrgency needUrgency = null;

        if (category != null && !category.isBlank()) {
            try {
                donationType = DonationType.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(List.of()); // Invalid category string
            }
        }

        if (urgency != null && !urgency.isBlank()) {
            try {
                needUrgency = NeedUrgency.valueOf(urgency.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(List.of()); // Invalid urgency string
            }
        }

        List<NeedDTO> needs = needService.filterNeeds(city, donationType, needUrgency);

        if (needs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(needs);
    }

    @GetMapping("pending-needs")
    public ResponseEntity<List<NeedDTO>> getPendingNeeds() {
        List<NeedDTO> needs = needService.getPendingNeeds();
        if (needs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(needs);
        }
    }

    @GetMapping("charity/{charityId}")
    public ResponseEntity<List<NeedDTO>> getCharityNeeds(@PathVariable Long charityId) {
        List<NeedDTO> needs = needService.getNeedsForCharity(charityId);

        return ResponseEntity.ok(needs);
    }

    @GetMapping("/{donorId}/suggested-needs")
    public ResponseEntity<List<NeedDTO>> getSuggestedNeedsForDonor(@PathVariable Long donorId) {
        List<NeedDTO> needs = matchingService.getSuggestedNeedsForDonor(donorId);

        return ResponseEntity.ok(needs);
    }

}
