package com.example.donation_app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.CreateDonationDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
import com.example.donation_app.DTO.PickupDTO;
import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Exception.ResourceNotFoundException;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.Need;
import com.example.donation_app.Model.Pickup;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.DonationRepository;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.NeedRepository;
import com.example.donation_app.Repository.PickupRepository;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final CharityRepository charityRepository;
    private final PickupRepository pickupRepository;
    private final NeedRepository needRepository;

    public DonationService(DonationRepository donationRepository, DonorRepository donorRepository,
            CharityRepository charityRepository, PickupRepository pickupRepository, NeedRepository needRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.charityRepository = charityRepository;
        this.pickupRepository = pickupRepository;
        this.needRepository = needRepository;
    }

    private PickupDTO mapToPickupDTO(Pickup pickup) {
        PickupDTO dto = new PickupDTO();
        dto.setId(pickup.getId());
        dto.setScheduledDate(pickup.getScheduledDate());
        dto.setLocation(pickup.getLocation());
        return dto;
    }

    private DonationDetailsDTO mapToDetailsDTO(Donation donation) {
        DonationDetailsDTO dto = new DonationDetailsDTO();
        dto.setId(donation.getId());
        dto.setType(donation.getType());
        dto.setQuantity(donation.getQuantity());
        dto.setDescription(donation.getDescription());
        dto.setStatus(donation.getStatus());
        dto.setExpiryDate(donation.getExpiryDate());
        dto.setCharityName(donation.getCharity().getName());
        dto.setDonorName(donation.getDonor().getName());
        dto.setPickup(mapToPickupDTO(donation.getPickup()));

        return dto;
    }

    public DonationDetailsDTO addDonation(Long donorId, CreateDonationDTO dto) {
        Donor donor = donorRepository.findById(donorId).get();
        Charity charity = charityRepository.findById(dto.getCharityId()).get();
        Need need = needRepository.findById(dto.getNeedId()).get();

        Donation donation = new Donation();
        donation.setType(dto.getType());
        donation.setQuantity(dto.getQuantity());
        // donation.setExpiryDate(dto.getExpiryDate());
        // donation.setDonationItems(dto.getDonationItems());
        donation.setDescription(dto.getDescription());
        donation.setStatus(DonationStatus.PENDING);
        donation.setDonor(donor);
        donation.setCharity(charity);

        Pickup pickup = new Pickup();
        pickup.setLocation(dto.getPickup().getLocation());
        pickup.setScheduledDate(dto.getPickup().getScheduledDate());

        pickupRepository.save(pickup);
        donation.setPickup(pickup);

        donationRepository.save(donation);

        decrementNeed(need, dto.getQuantity());

        return mapToDetailsDTO(donation);
    }

    private void decrementNeed(Need need, Integer quantity) {
        need.setQuantity(need.getQuantity() - quantity);

        if (need.getQuantity() == 0) {
            needRepository.delete(need);
            return;
        }
        
        needRepository.save(need);
    }

    public DonationDetailsDTO getDonationDetails(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("Donation not found"));

        return mapToDetailsDTO(donation);
    }

    public List<DonationDetailsDTO> getDonationsForDonor(Long donorId) {
        List<Donation> donations = donationRepository.findByDonor(donorRepository.findById(donorId).get());

        if (donations.isEmpty()) {
            throw new ResourceNotFoundException("No donation found for this donor");
        }

        return donations.stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public List<DonationDetailsDTO> getDonationsForCharity(Long charityId) {
        List<Donation> donations = donationRepository.findByCharity(charityRepository.findById(charityId).get());

        if (donations.isEmpty()) {
            throw new ResourceNotFoundException("No donations found for this charity");
        }

        return donations.stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public DonationDetailsDTO getUpcomingPickup(Long donorId) {
        List<Donation> donations = donationRepository.findUpcomingPickupForDonor(donorId);

        if (donations.isEmpty()) {
            throw new ResourceNotFoundException("No upcoming pickups found for this donor");
        }

        return mapToDetailsDTO(donations.get(0));
    }

    public DonationDetailsDTO confirmPickup(Long donationId) {
        Donation donation = donationRepository.findById(donationId).get();

        if (donation.getStatus() != DonationStatus.PENDING) {
            throw new IllegalArgumentException("Only Pending donations can be confirmed");
        }

        donation.setStatus(DonationStatus.SCHEDULED);
        donationRepository.save(donation);

        return mapToDetailsDTO(donation);
    }

    public DonationDetailsDTO cancelDonation(Long donationId) {
        Donation donation = donationRepository.findById(donationId).get();

        if (donation.getStatus() == DonationStatus.SCHEDULED || donation.getStatus() == DonationStatus.PENDING) {
            donation.setStatus(DonationStatus.CANCELED);
            donationRepository.save(donation);
        } else {
            throw new IllegalArgumentException("Donation cannot be canceled");
        }

        return mapToDetailsDTO(donation);
    }
}
