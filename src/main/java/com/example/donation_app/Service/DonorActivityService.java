package com.example.donation_app.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.DonorActivityDTO;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.DonorActivity;
import com.example.donation_app.Model.DonorSession;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.DonorActivityRepository;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.DonorSessionRepository;

@Service
public class DonorActivityService {
    private final DonorActivityRepository donorActivityRepository;
    private final DonorRepository donorRepository;
    private final CharityRepository charityRepository;
    private final DonorSessionRepository donorSessionRepository;

    public DonorActivityService(DonorActivityRepository donorActivityRepository,
            DonorSessionRepository donorSessionRepository, DonorRepository donorRepository,
            CharityRepository charityRepository) {
        this.donorActivityRepository = donorActivityRepository;
        this.donorSessionRepository = donorSessionRepository;
        this.donorRepository = donorRepository;
        this.charityRepository = charityRepository;
    }

    // private DonorActivityDTO map(DonorActivity activity) {
    // DonorActivityDTO dto = new DonorActivityDTO();
    // dto.setId(activity.getId());
    // dto.setDonorId(activity.getDonorId());
    // dto.setCharityId(activity.getCharityId());
    // dto.setActionType(activity.getActionType());
    // dto.setTimestamp(activity.getTimestamp());
    // dto.setSessionId(activity.getSession().getId());
    // return dto;
    // }

    public void recordActivity(DonorActivityDTO dto) {
        DonorSession session = donorSessionRepository.findById(dto.getSessionId()).get();
        Donor donor = donorRepository.findById(dto.getDonorId()).get();
        Charity charity = charityRepository.findById(dto.getCharityId()).get();

        DonorActivity activity = new DonorActivity();
        activity.setDonor(donor);
        activity.setCharity(charity);
        activity.setActionType(dto.getActionType());
        activity.setTimestamp(LocalDateTime.now());
        activity.setSession(session);

        donorActivityRepository.save(activity);
    }
}
