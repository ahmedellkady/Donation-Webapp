package com.example.donation_app.Service;

import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.DTO.FeedbackDTO;
import com.example.donation_app.DTO.AdminDTO;
import com.example.donation_app.DTO.CharityDTO;
import com.example.donation_app.DTO.DonationDetailsDTO;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.ResourceNotFoundException;
import com.example.donation_app.Model.Admin;
import com.example.donation_app.Model.Charity;
import com.example.donation_app.Model.Donation;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.Feedback;
import com.example.donation_app.Repository.AdminRepository;
import com.example.donation_app.Repository.CharityRepository;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.DonationRepository;
import com.example.donation_app.Repository.FeedbackRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donation_app.Enum.Role;
import com.example.donation_app.Enum.VerificationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final CharityRepository charityRepository;
    private final FeedbackRepository feedbackRepository;
    

    @Autowired
    public AdminService(DonationRepository donationRepository, DonorRepository donorRepository
    , CharityRepository charityRepository, FeedbackRepository feedbackRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.charityRepository = charityRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public void promoteDonorToAdmin(Long donorId) {
        Donor donor = donorRepository.findById(donorId)
            .orElseThrow(() -> new RuntimeException("Donor not found"));
    
        donor.setRole(Role.ADMIN);
        donorRepository.save(donor);
    }
    

    
    public Admin loginAdmin(AdminDTO dto) {
        Donor donor = donorRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!donor.getPassword().equals(dto.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        if (donor.getRole() != Role.ADMIN) {
            throw new RuntimeException("User is not an admin");
        }

        // احوّل الـ Donor إلى Admin object لو لازم
        Admin admin = new Admin();
        admin.setId(donor.getId());
        admin.setEmail(donor.getEmail());
        admin.setPassword(donor.getPassword());
        admin.setName(donor.getName());
        admin.setPhone(donor.getPhone());
        admin.setRole(Role.ADMIN);

        return admin;
    }

    //fetch all donors and convert to DonorDTO
    public List<DonorDTO> getAllDonors() {
        List<Donor> donors = donorRepository.findAll();

        return donors.stream() 
                .map(donor -> {
                    DonorDTO donorDTO = new DonorDTO();
                    donorDTO.setId(donor.getId());
                    donorDTO.setName(donor.getName());
                    donorDTO.setEmail(donor.getEmail());
                    donorDTO.setPhone(donor.getPhone());
                    // donorDTO.setRole(donor.getRole().name());
                    donorDTO.setCity(donor.getCity());
                    donorDTO.setNeighborhood(donor.getNeighborhood());
                    donorDTO.setRegisteredAt(donor.getRegisteredAt());
                    donorDTO.setPreferredTypes(donor.getPreferredTypes());
                    return donorDTO;
                })
                .collect(Collectors.toList());
    }

    // Method to fetch all charities and convert to CharityDTO
    public List<CharityDTO> getAllCharities() {
        List<Charity> charities = charityRepository.findAll();  // Fetch all charities from the database

        return charities.stream()  // Convert each Charity entity to CharityDTO
                .map(charity -> {
                    CharityDTO charityDTO = new CharityDTO();
                    charityDTO.setId(charity.getId());
                    charityDTO.setName(charity.getName());
                    charityDTO.setEmail(charity.getEmail());
                    charityDTO.setPhone(charity.getPhone());
                    // charityDTO.setRole(charity.getRole().name());
                    charityDTO.setCity(charity.getCity());
                    // charityDTO.setNeighborhood(charity.getNeighborhood());
                    charityDTO.setPreferredTypes(charity.getPreferredTypes());
                    charityDTO.setUploadedDocuments(charity.getUploadedDocuments());
                    charityDTO.setStatus(charity.getStatus());
                    return charityDTO;
                })
                .collect(Collectors.toList());  // Collect into a list of CharityDTOs
    }

    public boolean addDonor(DonorDTO dto) {
        if (donorRepository.existsByEmail(dto.getEmail())) {
            return false;
        }
    
        Donor donor = new Donor();
        donor.setName(dto.getName());
        donor.setEmail(dto.getEmail());
        donor.setPhone(dto.getPhone());
        // donor.setPassword(dto.getPassword());
        donor.setRole(Role.DONOR);
        donor.setCity(dto.getCity());
        donor.setNeighborhood(dto.getNeighborhood());
        donor.setPreferredTypes(dto.getPreferredTypes());
        donor.setRegisteredAt(LocalDateTime.now());

        donorRepository.save(donor);
        return true;
    }

    public boolean editDonor(Long donorId, DonorDTO dto) {
        Optional<Donor> optionalDonor = donorRepository.findById(donorId);
        if (optionalDonor.isEmpty()) {
            return false;
        }
    
        Donor donor = optionalDonor.get();
    
        if (!donor.getEmail().equals(dto.getEmail()) &&
            donorRepository.existsByEmail(dto.getEmail())) {
            return false;
        }
    
        donor.setName(dto.getName());
        donor.setPhone(dto.getPhone());
        donor.setCity(dto.getCity());
        donor.setNeighborhood(dto.getNeighborhood());
        donor.setPreferredTypes(dto.getPreferredTypes());
        donor.setEmail(dto.getEmail());
    
        donorRepository.save(donor);
        return true;
    }
    
    
    public boolean deleteDonor(Long donorId) {
        if (!donorRepository.existsById(donorId)) {
            return false;
        }
    
        donorRepository.deleteById(donorId);
        return true;
    }
    
    public boolean addDonation(Long donorId, Long charityId, DonationDetailsDTO dto) {
    Optional<Donor> optionalDonor = donorRepository.findById(donorId);
    Optional<Charity> optionalCharity = charityRepository.findById(charityId);

    if (optionalDonor.isEmpty() || optionalCharity.isEmpty()) {
        return false;
    }

    Donor donor = optionalDonor.get();
    Charity charity = optionalCharity.get();

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setCharity(charity);
        donation.setType(dto.getType());
        donation.setStatus(dto.getStatus());
        donation.setQuantity(dto.getQuantity());
        donation.setExpiryDate(dto.getExpiryDate());
        // donation.setDonationItems(dto.getDonationItems());

        donationRepository.save(donation);
        return true;
    }

    public boolean editDonation(Long donationId, DonationDetailsDTO dto) {
        Optional<Donation> optionalDonation = donationRepository.findById(donationId);
        if (optionalDonation.isEmpty()) {
            return false;
        }
    
        Donation donation = optionalDonation.get();
    
        donation.setType(dto.getType());
        donation.setStatus(dto.getStatus());
        donation.setQuantity(dto.getQuantity());
        donation.setExpiryDate(dto.getExpiryDate());
        // donation.setDonationItems(dto.getDonationItems());
    
        donationRepository.save(donation);
        return true;
    }
    
    public String addCharity(CharityDTO dto) {
        if (charityRepository.existsByEmail(dto.getEmail())) {
            return "Email already used";
        }
    
        Charity charity = new Charity();
        charity.setName(dto.getName());
        charity.setEmail(dto.getEmail());
        charity.setPhone(dto.getPhone());
        // charity.setPassword(dto.getPassword());
        charity.setRole(Role.CHARITY);
        charity.setCity(dto.getCity());
        // charity.setStatus(VerificationStatus.valueOf(dto.getStatus().toUpperCase()));
        // charity.setNeighborhood(dto.getNeighborhood());
        charity.setPreferredTypes(dto.getPreferredTypes());
        charity.setUploadedDocuments(dto.getUploadedDocuments());
    
        charityRepository.save(charity);
        return "Charity registered successfully";
    }
    
    
    public boolean editCharity(Long charityId, CharityDTO dto) {
        Optional<Charity> optionalCharity = charityRepository.findById(charityId);
        if (optionalCharity.isEmpty()) {
            return false;
        }

        Charity charity = optionalCharity.get();

        // تحقق من تكرار الإيميل فقط لو فيه تغيير
        if (dto.getEmail() != null &&
            !dto.getEmail().equals(charity.getEmail()) &&
            charityRepository.existsByEmail(dto.getEmail())) {
            return false;
        }

        // تعديل الحقول فقط لو تم إرسالها
        if (dto.getName() != null) charity.setName(dto.getName());
        if (dto.getPhone() != null) charity.setPhone(dto.getPhone());
        if (dto.getEmail() != null) charity.setEmail(dto.getEmail());
        if (dto.getCity() != null) charity.setCity(dto.getCity());
        // if (dto.getNeighborhood() != null) charity.setNeighborhood(dto.getNeighborhood());
        if (dto.getPreferredTypes() != null) charity.setPreferredTypes(dto.getPreferredTypes());
        if (dto.getUploadedDocuments() != null) charity.setUploadedDocuments(dto.getUploadedDocuments());
        if (dto.getStatus() != null) {
            charity.setStatus(dto.getStatus());
        }

        charityRepository.save(charity);
        return true;
    }

    
        public List<DonationDetailsDTO> getAllDonations() {
        List<Donation> donations = donationRepository.findAll();

        return donations.stream().map(donation -> {
            DonationDetailsDTO dto = new DonationDetailsDTO();
            dto.setId(donation.getId());
            dto.setType(donation.getType());
            dto.setQuantity(donation.getQuantity());
            dto.setExpiryDate(donation.getExpiryDate());
            dto.setStatus(donation.getStatus());
            // dto.setDonationItems(donation.getDonationItems());

            // set donorId and charityId if available
            if (donation.getDonor() != null) {
                dto.setDonorName(donation.getDonor().getName());
            }
            if (donation.getCharity() != null) {
                dto.setCharityName(donation.getCharity().getName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public boolean updateDonationStatus(Long donationId, DonationDetailsDTO dto) {
    Optional<Donation> optionalDonation = donationRepository.findById(donationId);
    if (optionalDonation.isEmpty()) {
        return false;
    }

    Donation donation = optionalDonation.get();
    donation.setStatus(dto.getStatus()); // بس بنعدل الحالة

    donationRepository.save(donation);
    return true;
}

public List<FeedbackDTO> getAllFeedbacks() {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    List<Donation> donations = donationRepository.findAll();

    List<FeedbackDTO> dtos = new ArrayList<>();

    for (Donation donation : donations) {
        if (donation.getDonorFeedback() != null) {
            Feedback f = donation.getDonorFeedback();
            FeedbackDTO dto = new FeedbackDTO();
            dto.setDonationId(donation.getId());
            dto.setRating(f.getRating());
            dto.setComment(f.getComment());
            dto.setIsUrgent(f.getIsUrgent());
            dto.setForRole("DONOR");
            dtos.add(dto);
        }

        if (donation.getCharityFeedback() != null) {
            Feedback f = donation.getCharityFeedback();
            FeedbackDTO dto = new FeedbackDTO();
            dto.setDonationId(donation.getId());
            dto.setRating(f.getRating());
            dto.setComment(f.getComment());
            dto.setIsUrgent(f.getIsUrgent());
            dto.setForRole("CHARITY");
            dtos.add(dto);
        }
    }
    return dtos;
}

}
