package com.example.donation_app.Controller;

import com.example.donation_app.DTO.ChangePasswordDTO;
import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.DTO.LoginDTO;
import com.example.donation_app.DTO.RegisterDonorDTO;
import com.example.donation_app.DTO.UpdateDonorProfileDTO;
import com.example.donation_app.Service.DonorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/register")
    public ResponseEntity<DonorDTO> registerDonor(@RequestBody RegisterDonorDTO donorDTO) {
        DonorDTO savedDonor = donorService.registerDonor(donorDTO);
        return ResponseEntity.ok(savedDonor);
    }

    @PostMapping("/login")
    public ResponseEntity<DonorDTO> loginDonor(@RequestBody LoginDTO loginDTO) {
        DonorDTO loggedInDonor = donorService.loginDonor(loginDTO);
        return ResponseEntity.ok(loggedInDonor);
    }

    @PutMapping("/update-profile/{donorId}")
    public ResponseEntity<DonorDTO> updateProfile(@PathVariable Long donorId,
            @RequestBody UpdateDonorProfileDTO dto) {
        DonorDTO updated = donorService.updateDonorProfile(donorId, dto);
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
