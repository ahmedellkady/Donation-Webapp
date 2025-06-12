package com.example.donation_app.Controller;

import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.DTO.AdminDTO;
import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
import com.example.donation_app.Model.Admin;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.donation_app.DTO.FeedbackDTO;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/promote")
    public ResponseEntity<String> promoteDonorToAdmin(@RequestBody Map<String, Long> body) {
        Long donorId = body.get("donorId");
        adminService.promoteDonorToAdmin(donorId);
        return ResponseEntity.ok("Donor promoted to admin successfully");
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDTO adminDTO) {
        Admin loggedInAdmin = adminService.loginAdmin(adminDTO);
        return ResponseEntity.ok("Welcome " + loggedInAdmin.getName() + "! You are logged in as Admin.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out.");
    }


    @GetMapping("/donors")
    public ResponseEntity<List<DonorDTO>> getAllDonors() {
        List<DonorDTO> donorDTOs = adminService.getAllDonors();
        return ResponseEntity.ok(donorDTOs);
    }

    @GetMapping("/charities")
    public ResponseEntity<List<CharityDTO>> getAllCharities() {
        List<CharityDTO> charityDTOs = adminService.getAllCharities();
        return ResponseEntity.ok(charityDTOs);
    }

    @PostMapping("/add-donor")
    public ResponseEntity<Boolean> addDonor(@RequestBody DonorDTO dto) {
        boolean result = adminService.addDonor(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit-donor/{id}")
    public ResponseEntity<Boolean> editDonor(@PathVariable("id") Long donorId, @RequestBody DonorDTO dto) {
        boolean result = adminService.editDonor(donorId, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete-donor/{id}")
    public ResponseEntity<Boolean> deleteDonor(@PathVariable("id") Long donorId) {
        boolean result = adminService.deleteDonor(donorId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add-donation/{donorId}/{charityId}")
    public ResponseEntity<Boolean> addDonation(@PathVariable("donorId") Long donorId,
                                            @PathVariable("charityId") Long charityId,
                                            @RequestBody DonationDetailsDTO dto) {
        boolean result = adminService.addDonation(donorId, charityId, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit-donation/{donationId}")
    public ResponseEntity<Boolean> editDonation(@PathVariable Long donationId,
                                                @RequestBody DonationDetailsDTO dto) {
        boolean result = adminService.editDonation(donationId, dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add-charity")
    public ResponseEntity<String> addCharity(@RequestBody CharityDTO dto) {
        String result = adminService.addCharity(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit-charity/{id}")
    public ResponseEntity<Boolean> editCharity(@PathVariable("id") Long charityId,
                                            @RequestBody CharityDTO dto) {
        boolean result = adminService.editCharity(charityId, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/donations")
    public ResponseEntity<List<DonationDetailsDTO>> getAllDonations() {
        List<DonationDetailsDTO> donations = adminService.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    @PutMapping("/donations/{donationId}/status")
    public ResponseEntity<Boolean> updateDonationStatus(@PathVariable Long donationId,
                                                        @RequestBody DonationDetailsDTO dto) {
        boolean result = adminService.updateDonationStatus(donationId, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(adminService.getAllFeedbacks());
    }

}
