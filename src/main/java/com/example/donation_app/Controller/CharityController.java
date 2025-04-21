package com.example.donation_app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Service.CharityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/charity")
public class CharityController {
    
    private final CharityService charityService;

    @Autowired
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
    
}
