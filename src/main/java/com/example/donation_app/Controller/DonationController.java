package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.DonationDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
import com.example.donation_app.Model.Donation;
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
    public ResponseEntity<Donation> addDonation(@PathVariable Long donorId, @RequestBody DonationDTO dto) {
        Donation donation = donationService.addDonation(donorId, dto);
        return ResponseEntity.ok(donation);
    }

    @GetMapping("/history/{donorId}")
    public ResponseEntity<List<Donation>> getDonationHistory(@PathVariable Long donorId) {
        List<Donation> donations = donationService.getDonationHistory(donorId);
        return ResponseEntity.ok(donations);
    }
    
    @PutMapping("/cancel/{donationId}")
    public ResponseEntity<Donation> cancelDonation(@PathVariable Long donationId) {
        Donation cancelled = donationService.cancelDonation(donationId);
        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/upcoming-pickup/{donorId}")
    public ResponseEntity<Donation> getUpcomingPickup(@PathVariable Long donorId) {
        Donation donation = donationService.getUpcomingPickup(donorId);
        return ResponseEntity.ok(donation);
    }

    @GetMapping("/details/{donationId}")
    public ResponseEntity<DonationDetailsDTO> getDonationDetails(@PathVariable Long donationId) {
        DonationDetailsDTO details = donationService.getDonationDetails(donationId);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/confirm-pickup/{donationId}")
    public ResponseEntity<DonationDetailsDTO> confirmPickup(@PathVariable Long donationId) {
        DonationDetailsDTO confirmed = donationService.confirmPickup(donationId);
        return ResponseEntity.ok(confirmed);
    }

    @GetMapping("/charity/{charityId}")
    public ResponseEntity<List<DonationDetailsDTO>> getDonationsForCharity(@PathVariable Long charityId) {
        List<DonationDetailsDTO> donations = donationService.getDonationsForCharity(charityId);
        return ResponseEntity.ok(donations);
    }
    
}
