package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.DonationDTO;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Service.DonationService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/donations")
public class DonationController {
    
    private final DonationService donationService;

    @Autowired
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
    
    
}
