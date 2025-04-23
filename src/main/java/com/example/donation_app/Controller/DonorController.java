package com.example.donation_app.Controller;

import com.example.donation_app.DTO.ChangePasswordDTO;
import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.DTO.UpdateDonorProfileDTO;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginDonor(@RequestBody DonorDTO donorDTO) {
        Donor loggedInDonor = donorService.loginDonor(donorDTO);
        return ResponseEntity.ok("Welcome " + loggedInDonor.getName() + "! You are logged in.");
    }

    @PutMapping("/update-profile/{donorId}")
    public ResponseEntity<Donor> updateProfile(@PathVariable Long donorId,
            @RequestBody UpdateDonorProfileDTO dto) {
        Donor updated = donorService.updateDonorProfile(donorId, dto);
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/change-password/{donorId}")
    public ResponseEntity<String> changePassword(@PathVariable Long donorId,
            @RequestBody ChangePasswordDTO dto) {
        donorService.changePassword(donorId, dto);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @DeleteMapping("/delete/{donorId}")
    public ResponseEntity<String> deleteDonor(@PathVariable Long donorId) {
        donorService.deleteDonor(donorId);
        return ResponseEntity.ok("Donor deleted successfully.");
    }

}
