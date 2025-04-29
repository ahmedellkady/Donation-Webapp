package com.example.donation_app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.DonorSessionDTO;
import com.example.donation_app.Service.DonorSessionService;


@RestController
@RequestMapping("/api/sessions")
public class DonorSessionController {
    private final DonorSessionService donorSessionService;

    public DonorSessionController(DonorSessionService donorSessionService) {
        this.donorSessionService = donorSessionService;
    }

    @PostMapping("/start/{donorId}")
    public ResponseEntity<DonorSessionDTO> startSession(@PathVariable Long donorId) {
        DonorSessionDTO session = donorSessionService.startSession(donorId);
        donorSessionService.maintainsSessionLimit(donorId, 5);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<String> endSession(@PathVariable Long sessionId) {
        donorSessionService.endSession(sessionId);
        return ResponseEntity.ok("Session ended successfully.");
    }
    
}
