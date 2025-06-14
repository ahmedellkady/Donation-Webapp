package com.example.donation_app.Integration.Controller;

import com.example.donation_app.DTO.*;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DonorFlowIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void donorCharityDonationFlow() throws Exception {
        // Step 1: Register Donor
        RegisterDonorDTO donorDTO = new RegisterDonorDTO();
        donorDTO.setName("Test Donor");
        donorDTO.setEmail("donor_integration_test@example.com");
        donorDTO.setPhone("01112345678");
        donorDTO.setPassword("password123");
        donorDTO.setCity("Cairo");
        donorDTO.setNeighborhood("Nasr City");
        donorDTO.setPreferredTypes(Set.of(DonationType.FOOD));

        String donorJson = objectMapper.writeValueAsString(donorDTO);

        String donorResponse = mockMvc.perform(post("/api/donors/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(donorJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DonorDTO registeredDonor = objectMapper.readValue(donorResponse, DonorDTO.class);
        Long donorId = registeredDonor.getId();

        // Step 2: Login Donor
        LoginDTO donorLogin = new LoginDTO();
        donorLogin.setEmail("donor_integration_test@example.com");
        donorLogin.setPassword("password123");

        mockMvc.perform(post("/api/donors/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donorLogin)))
                .andExpect(status().isOk());

        // Step 3: Register Charity
        RegisterCharityDTO charityDTO = new RegisterCharityDTO();
        charityDTO.setName("Test Charity");
        charityDTO.setEmail("charity_integration_test@example.com");
        charityDTO.setPhone("01098765432");
        charityDTO.setPassword("charitypass");
        charityDTO.setCity("Giza");
        charityDTO.setPreferredTypes(Set.of(DonationType.FOOD));

        String charityJson = objectMapper.writeValueAsString(charityDTO);

        String charityResponse = mockMvc.perform(post("/api/charity/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(charityJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CharityDTO registeredCharity = objectMapper.readValue(charityResponse, CharityDTO.class);
        Long charityId = registeredCharity.getId();

        // Step 4: Login Charity
        LoginDTO charityLogin = new LoginDTO();
        charityLogin.setEmail("charity_integration_test@example.com");
        charityLogin.setPassword("charitypass");

        mockMvc.perform(post("/api/charity/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(charityLogin)))
                .andExpect(status().isOk());

        // Step 5: Post a Need
        NeedDTO needDTO = new NeedDTO();
        needDTO.setDescription("Need food for families");
        needDTO.setType(DonationType.FOOD);
        needDTO.setQuantity(10);
        needDTO.setUrgency(NeedUrgency.MEDIUM);

        String needResponse = mockMvc.perform(post("/api/needs/post/" + charityId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(needDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        NeedDTO createdNeed = objectMapper.readValue(needResponse, NeedDTO.class);
        Long needId = createdNeed.getId();

        // Step 6: Submit a Donation
        PickupDTO pickupDTO = new PickupDTO();
        pickupDTO.setLocation("Cairo Pickup Point");
        pickupDTO.setScheduledDate(LocalDateTime.now().plusDays(2));

        CreateDonationDTO donationDTO = new CreateDonationDTO();
        donationDTO.setDescription("testttt");
        donationDTO.setNeedId(needId);
        donationDTO.setCharityId(charityId);
        donationDTO.setExpiryDate(LocalDate.now().plusWeeks(1));
        donationDTO.setQuantity(10);
        donationDTO.setType(DonationType.FOOD);
        donationDTO.setPickup(pickupDTO);

        String donationJson = objectMapper.writeValueAsString(donationDTO);

        String donationResponse = mockMvc.perform(post("/api/donations/add/" + donorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(donationJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DonationDetailsDTO donation = objectMapper.readValue(donationResponse, DonationDetailsDTO.class);
        Long donationId = donation.getId();

        // Step 6: Confirm Pickup by Donor
        mockMvc.perform(put("/api/donations/scheduled-pickup/" + donationId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }
}
