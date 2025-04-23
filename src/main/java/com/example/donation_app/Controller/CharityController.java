package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Service.CharityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/charity")
public class CharityController {
    
    private final CharityService charityService;

    public CharityController(CharityService charityService) {
        this.charityService = charityService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postMethodName(@RequestBody CharityDTO dto) {
        Charity savedCharity = charityService.registerCharity(dto);
        return ResponseEntity.ok("Charity registered successfully with ID: " + savedCharity.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCharity(@RequestBody CharityDTO dto) {
        Charity loggedInCharity = charityService.loginCharity(dto);
        return ResponseEntity.ok("Welcome " + loggedInCharity.getName() + "! You are logged in.");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Charity>> getPendingCharities() {
        List<Charity> pendingCharities = charityService.getPendingCharities();

        if (pendingCharities.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pendingCharities);
        }
    }
    
    
}
