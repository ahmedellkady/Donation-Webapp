package com.example.donation_app.Service;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.FeedbackDTO;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Feedback;
import com.example.donation_app.Repository.DonationRepository;

@Service
public class FeedbackService {

    private DonationRepository donationRepository;

    public FeedbackService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    private FeedbackDTO map(Feedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        return dto;
    }

    public FeedbackDTO submitFeedback(FeedbackDTO dto) {
        Donation donation = donationRepository.findById(dto.getDonationId()).get();

        Feedback feedback = new Feedback();
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());

        if (dto.getForRole().equals("DONOR")) {
            donation.setDonorFeedback(feedback);
        } else {
            donation.setCharityFeedback(feedback);
        }

        donationRepository.save(donation);

        return map(feedback);
    }
}
