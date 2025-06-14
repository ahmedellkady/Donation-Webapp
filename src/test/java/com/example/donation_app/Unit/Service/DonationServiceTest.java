package com.example.donation_app.Unit.Service;

import com.example.donation_app.DTO.CreateDonationDTO;
import com.example.donation_app.DTO.PickupDTO;
import com.example.donation_app.Enum.DonationStatus;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Model.*;
import com.example.donation_app.Repository.*;
import com.example.donation_app.Service.DonationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;
    @Mock
    private DonorRepository donorRepository;
    @Mock
    private CharityRepository charityRepository;
    @Mock
    private PickupRepository pickupRepository;
    @Mock
    private NeedRepository needRepository;

    @InjectMocks
    private DonationService donationService;

    private Donor donor;
    private Charity charity;
    private Need need;
    private Donation donation;

    @BeforeEach
    void setup() {
        donor = new Donor();
        donor.setId(1L);
        donor.setName("Donor Test");

        charity = new Charity();
        charity.setId(2L);
        charity.setName("Charity Test");

        need = new Need();
        need.setId(3L);
        need.setQuantity(100);

        donation = new Donation();
        donation.setId(10L);
        donation.setDonor(donor);
        donation.setCharity(charity);
        donation.setStatus(DonationStatus.PENDING);
    }

    @Test
    void addDonation_shouldCreateNewDonation_whenDataIsValid() {
        CreateDonationDTO dto = new CreateDonationDTO();
        dto.setCharityId(charity.getId());
        dto.setNeedId(need.getId());
        dto.setDescription("Food donation");
        dto.setType(DonationType.FOOD);
        dto.setQuantity(5);

        PickupDTO pickupDTO = new PickupDTO();
        pickupDTO.setLocation("Cairo");
        pickupDTO.setScheduledDate(LocalDateTime.now().plusDays(1));
        dto.setPickup(pickupDTO);

        when(donorRepository.findById(donor.getId())).thenReturn(Optional.of(donor));
        when(charityRepository.findById(charity.getId())).thenReturn(Optional.of(charity));
        when(needRepository.findById(need.getId())).thenReturn(Optional.of(need));
        when(pickupRepository.save(any(Pickup.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(donationRepository.save(any(Donation.class))).thenAnswer(invocation -> {
            Donation saved = invocation.getArgument(0);
            saved.setId(99L);
            return saved;
        });

        var result = donationService.addDonation(donor.getId(), dto);

        assertNotNull(result);
        assertEquals("Food donation", result.getDescription());
    }

    @Test
    void pickupScheduled_shouldUpdateStatusToScheduled_whenDonationIsPending() {
        donation.setStatus(DonationStatus.PENDING);

        Pickup pickup = new Pickup();
        pickup.setId(1L);
        pickup.setLocation("Test Street");
        pickup.setScheduledDate(LocalDateTime.now().plusDays(1));
        donation.setPickup(pickup);

        when(donationRepository.findById(donation.getId())).thenReturn(Optional.of(donation));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        var result = donationService.pickupScheduled(donation.getId());

        assertEquals(DonationStatus.SCHEDULED, result.getStatus());
    }

    @Test
    void cancelDonation_shouldSetStatusToCanceled_whenDonationIsPending() {
        donation.setStatus(DonationStatus.PENDING);

        Pickup pickup = new Pickup();
        pickup.setId(2L);
        pickup.setLocation("Test Cancel St.");
        pickup.setScheduledDate(LocalDateTime.now().plusDays(2));
        donation.setPickup(pickup);

        when(donationRepository.findById(donation.getId())).thenReturn(Optional.of(donation));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        var result = donationService.cancelDonation(donation.getId());

        assertEquals(DonationStatus.CANCELED, result.getStatus());
    }

    @Test
    void cancelDonation_shouldThrowException_whenStatusIsCompleted() {
        donation.setStatus(DonationStatus.DELIVERED);

        when(donationRepository.findById(donation.getId())).thenReturn(Optional.of(donation));

        assertThrows(IllegalArgumentException.class, () -> donationService.cancelDonation(donation.getId()));
    }
}
