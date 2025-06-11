package com.example.donation_app.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.FeedbackDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.Feedback;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.DonationRepository;
import com.example.donation_app.Repository.DonorRepository;

@Service
public class FeedbackService {

    private DonationRepository donationRepository;
    private CharityRepository charityRepository;
    private DonorRepository donorRepository;

    public FeedbackService(DonationRepository donationRepository, CharityRepository charityRepository, DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.charityRepository = charityRepository;
        this.donorRepository = donorRepository;
    }

    private FeedbackDTO map(Feedback feedback, Long donationId) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        dto.setDonationId(donationId);
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

        return map(feedback, donation.getId());
    }

    public List<FeedbackDTO> getFeedbacksForCharity(Long charityId) {
        Charity charity = charityRepository.findById(charityId).get();

        List<Donation> donations = donationRepository.findByCharity(charity);

        List<FeedbackDTO> feedbacks = new ArrayList<>();

        for (Donation donation : donations) {
            Feedback feedback = donation.getDonorFeedback();

            if (feedback != null) {
                feedbacks.add(map(feedback, donation.getId()));
            }
        }

        return feedbacks.stream().collect(Collectors.toList());
    }

    public List<FeedbackDTO> getFeedbacksForDonor(Long donorId) {
        Donor donor = donorRepository.findById(donorId).get();

        List<Donation> donations = donationRepository.findByDonor(donor);

        List<FeedbackDTO> feedbacks = new ArrayList<>();

        for (Donation donation : donations) {
            Feedback feedback = donation.getCharityFeedback();

            if (feedback != null) {
                feedbacks.add(map(feedback, donation.getId()));
            }
        }

        return feedbacks.stream().collect(Collectors.toList());
    }

    public FeedbackDTO getDonorFeedbackByDonationId(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("No feedback found for donation ID: " + donationId));

        if (donation.getDonorFeedback() == null) {
            throw new RuntimeException("No feedback found for donation ID: " + donationId);
        } else {
            return map(donation.getDonorFeedback(), donationId);
        }
    }

    public FeedbackDTO getCharityFeedbackByDonationId(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("No feedback found for donation ID: " + donationId));

        if (donation.getCharityFeedback() == null) {
            throw new RuntimeException("No feedback found for donation ID: " + donationId);
        } else {
            return map(donation.getCharityFeedback(), donationId);
        }
    }
}
