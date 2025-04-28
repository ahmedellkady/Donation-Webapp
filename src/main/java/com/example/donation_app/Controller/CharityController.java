package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.DTO.LoginDTO;
import com.example.donation_app.DTO.RegisterCharityDTO;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Service.CharityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/charity")
public class CharityController {

    private final CharityService charityService;

    public CharityController(CharityService charityService) {
        this.charityService = charityService;
    }

    @PostMapping("/register")
    public ResponseEntity<CharityDTO> postMethodName(@RequestBody RegisterCharityDTO dto) {
        CharityDTO savedCharity = charityService.registerCharity(dto);
        return ResponseEntity.ok(savedCharity);
    }

    @PostMapping("/login")
    public ResponseEntity<CharityDTO> loginCharity(@RequestBody LoginDTO dto) {
        CharityDTO loggedInCharity = charityService.loginCharity(dto);
        return ResponseEntity.ok(loggedInCharity);
    }

    @GetMapping("/{charityId}")
    public ResponseEntity<CharityDTO> getCharity(@PathVariable Long charityId) {
        CharityDTO charity = charityService.getCharity(charityId);
        return ResponseEntity.ok(charity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharityDTO>> getAllCharities() {
        List<CharityDTO> charities = charityService.getAllCharities();
        return ResponseEntity.ok(charities);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CharityDTO>> filterCharities(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category) {

        DonationType donationType = null;

        if (category != null && !category.isBlank()) {
            try {
                donationType = DonationType.valueOf(category.toUpperCase()); 
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(List.of());
            }
        }

        List<CharityDTO> filteredCharities = charityService.filterCharities(city, donationType);

        if (filteredCharities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(filteredCharities);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CharityDTO>> getPendingCharities() {
        List<CharityDTO> pendingCharities = charityService.getPendingCharities();

        if (pendingCharities.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pendingCharities);
        }
    }

    @DeleteMapping("/delete/{charityId}")
    public ResponseEntity<String> deleteCharity(@PathVariable Long charityId) {
        charityService.deleteCharity(charityId);
        return ResponseEntity.ok("Charity deleted successfully");
    }
}
