package com.example.donation_app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.donation_app.DTO.FeedbackDTO;
import com.example.donation_app.Service.FeedbackService;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {
    
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/submit")
    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO dto) {
        feedbackService.submitFeedback(dto);
        return ResponseEntity.ok(dto);
    }
}
