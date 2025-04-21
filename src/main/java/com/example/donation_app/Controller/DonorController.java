package com.example.donation_app.Controller;

import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Service.DonorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerDonor(@RequestBody DonorDTO donorDTO) {
        Donor savedDonor = donorService.registerDonor(donorDTO);
        return ResponseEntity.ok("Donor registered successfully with ID: " + savedDonor.getId());
    }
}
