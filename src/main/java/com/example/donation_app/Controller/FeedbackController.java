package com.example.donation_app.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // @PostMapping("/submit")
    // public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO dto) {
    //     feedbackService.submitFeedback(dto);
    //     return ResponseEntity.ok(dto);
    // }

    @PostMapping("/submit")
    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO dto) {
        FeedbackDTO savedDto = feedbackService.submitFeedback(dto);
        return ResponseEntity.ok(savedDto);
    }


    @GetMapping("/charity/{charityId}/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksForCharity(@PathVariable Long charityId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksForCharity(charityId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/donor/{donorId}/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksForDonor(@PathVariable Long donorId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksForDonor(donorId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/donor/{donationId}/feedback")
    public ResponseEntity<FeedbackDTO> getDonorFeedbackByDonationId(@PathVariable Long donationId) {
        FeedbackDTO feedback = feedbackService.getDonorFeedbackByDonationId(donationId);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/charity/{donationId}/feedback")
    public ResponseEntity<FeedbackDTO> getCharityFeedbackByDonationId(@PathVariable Long donationId) {
        FeedbackDTO feedback = feedbackService.getCharityFeedbackByDonationId(donationId);
        return ResponseEntity.ok(feedback);
    }
}
