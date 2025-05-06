package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.CreateDonationDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
import com.example.donation_app.DTO.PickupDTO;
import com.example.donation_app.Service.DonationService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/donations")
public class DonationController {
    
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/add/{donorId}")
    public ResponseEntity<DonationDetailsDTO> addDonation(@PathVariable Long donorId,
            @RequestBody CreateDonationDTO dto) {
        DonationDetailsDTO donation = donationService.addDonation(donorId, dto);
        return ResponseEntity.ok(donation);
    }
    
    @GetMapping("/details/{donationId}")
    public ResponseEntity<DonationDetailsDTO> getDonationDetails(@PathVariable Long donationId) {
        DonationDetailsDTO details = donationService.getDonationDetails(donationId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/history/{donorId}")
    public ResponseEntity<List<DonationDetailsDTO>> getDonationHistory(@PathVariable Long donorId) {
        List<DonationDetailsDTO> donations = donationService.getDonationsForDonor(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/charity/{charityId}")
    public ResponseEntity<List<DonationDetailsDTO>> getDonationsForCharity(@PathVariable Long charityId) {
        List<DonationDetailsDTO> donations = donationService.getDonationsForCharity(charityId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/upcoming-pickup/{donorId}")
    public ResponseEntity<DonationDetailsDTO> getUpcomingPickup(@PathVariable Long donorId) {
        DonationDetailsDTO donation = donationService.getUpcomingPickup(donorId);
        return ResponseEntity.ok(donation);
    }

    @PutMapping("/confirm-pickup/{donationId}")
    public ResponseEntity<DonationDetailsDTO> confirmPickup(@PathVariable Long donationId) {
        DonationDetailsDTO confirmed = donationService.confirmPickup(donationId);
        return ResponseEntity.ok(confirmed);
    }

    @PutMapping("/cancel/{donationId}")
    public ResponseEntity<DonationDetailsDTO> cancelDonation(@PathVariable Long donationId) {
        DonationDetailsDTO cancelled = donationService.cancelDonation(donationId);
        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/count/{donorId}")
    public ResponseEntity<Long> getDonationCountForDonor(@PathVariable Long donorId) {
        Long count = donationService.getDonationCountForDonor(donorId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/latest-pickup/{donorId}")
    public ResponseEntity<PickupDTO> getLatestPickupForDonor(@PathVariable Long donorId) {
        PickupDTO pickup = donationService.getLatestPickupForDonor(donorId);
        return ResponseEntity.ok(pickup);
    }
}
