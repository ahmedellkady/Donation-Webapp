package com.example.donation_app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.DonationDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
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
    
    public Donation cancelDonation(Long donationId) {
        Donation donation = donationRepository.findById(donationId).get();

        if (donation.getStatus() == DonationStatus.SCHEDULED || donation.getStatus() == DonationStatus.PENDING) {
            donation.setStatus(DonationStatus.CANCELED);
            return donationRepository.save(donation);
        } else {
            throw new IllegalArgumentException("Donation cannot be canceled");
        }
    }

    public Donation getUpcomingPickup(Long donorId) {
        List<Donation> donations = donationRepository.findUpcomingPickupForDonor(donorId);

        if (donations.isEmpty()) {
            throw new NoDonationFoundException("No upcoming pickups found for this donor");
        }

        return donations.get(0);
    }

    private DonationDetailsDTO map(Donation donation) {
        DonationDetailsDTO dto = new DonationDetailsDTO();
        dto.setId(donation.getId());
        dto.setType(donation.getType());
        dto.setQuantity(donation.getQuantity());
        dto.setExpiryDate(donation.getExpiryDate());
        dto.setDonationItems(donation.getDonationItems());
        dto.setStatus(donation.getStatus());
        dto.setScheduledDate(donation.getPickup().getScheduledDate());
        dto.setPickupLocation(donation.getPickup().getLocation());
        dto.setCharityName(donation.getCharity().getName());
        dto.setDonorName(donation.getDonor().getName());
        return dto;
    }

    public DonationDetailsDTO getDonationDetails(Long donationId) {
        Donation donation = donationRepository.findById(donationId).get();
        if (donation == null) {
            throw new NoDonationFoundException("No donation found with id " + donationId);
        }

        DonationDetailsDTO dto = map(donation);

        if (donation.getPickup() != null) {
            dto.setScheduledDate(donation.getPickup().getScheduledDate());
            dto.setPickupLocation(donation.getPickup().getLocation());
        }

        if (donation.getCharity() != null) {
            dto.setCharityName(donation.getCharity().getName());
        }

        if (donation.getDonor() != null) {
            dto.setDonorName(donation.getDonor().getName());
        }

        return dto;
    }
    
    public DonationDetailsDTO confirmPickup(Long donationId) {
        Donation donation = donationRepository.findById(donationId).get();

        if (donation.getStatus() != DonationStatus.SCHEDULED) {
            throw new IllegalArgumentException("Only scheduled donations can be confirmed");
        }

        donation.setStatus(DonationStatus.PENDING);
        donationRepository.save(donation);

        return map(donation);
    }

    public List<DonationDetailsDTO> getDonationsForCharity(Long charityId) {
        List<Donation> donations = donationRepository.findByCharity(charityRepository.findById(charityId).get());
        
        if (donations.isEmpty()) {
            throw new NoDonationFoundException("No donations found for this charity");
        }
        
        return donations.stream().map(this::map).collect(Collectors.toList());
    }
}
