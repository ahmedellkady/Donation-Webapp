package com.example.donation_app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.DonationDTO;
import com.example.donation_app.DTO.PickupDTO;
import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Exception.NoDonationFoundException;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Pickup;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.DonationRepository;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.PickupRepository;

@Service
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final CharityRepository charityRepository;
    private final PickupRepository pickupRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository, DonorRepository donorRepository,
            PickupRepository pickupRepository, CharityRepository charityRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.pickupRepository = pickupRepository;
        this.charityRepository = charityRepository;
    }

    public Donation addDonation(Long donorId, DonationDTO dto) {
        PickupDTO pickupDTO = dto.getPickup();
        Pickup pickup = new Pickup();
        pickup.setScheduledDate(pickupDTO.getScheduledDate());
        pickup.setLocation(pickupDTO.getLocation());
        pickupRepository.save(pickup);

        Donation donation = new Donation();
        donation.setType(dto.getType());
        donation.setQuantity(dto.getQuantity());
        donation.setExpiryDate(dto.getExpiryDate());
        donation.setStatus(DonationStatus.SCHEDULED);
        donation.setDonationItems(dto.getDonationItems());
        donation.setPickup(pickup);
        donation.setDonor(donorRepository.findById(donorId).get());
        donation.setCharity(charityRepository.findById(dto.getCharityId()).get());

        return donationRepository.save(donation);
    }

    public List<Donation> getDonationHistory(Long donorId) {
        List<Donation> donations = donationRepository.findByDonor(donorRepository.findById(donorId).get());
        
        if (donations.isEmpty()) {
            throw new NoDonationFoundException("No donation found for this donor");
        }

        return donations;
    }
}
