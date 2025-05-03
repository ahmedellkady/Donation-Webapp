package com.example.donation_app.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.DonorActivityDTO;
import com.example.donation_app.Service.DonorActivityService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/donor-activity")
public class DonorActivityController {

    private final DonorActivityService donorActivityService;

    public DonorActivityController(DonorActivityService donorActivityService) {
        this.donorActivityService = donorActivityService;
    }

    @PostMapping("/record")
    public ResponseEntity<Map<String, String>> recordActivity(@RequestBody DonorActivityDTO dto) {
        donorActivityService.recordActivity(dto);
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        response.put("message", "Activity recorded successfully");
        return ResponseEntity.ok(response);
    }

}
