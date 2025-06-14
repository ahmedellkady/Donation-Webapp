package com.example.donation_app.Unit.Service;

import com.example.donation_app.DTO.DonorDTO;
import com.example.donation_app.DTO.LoginDTO;
import com.example.donation_app.DTO.RegisterDonorDTO;
import com.example.donation_app.Exception.EmailAlreadyUsedException;
import com.example.donation_app.Exception.InvalidCredentialsException;
import com.example.donation_app.Exception.ResourceNotFoundException;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Service.DonorService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DonorServiceTest {

    @Mock
    private DonorRepository donorRepository;

    @InjectMocks
    private DonorService donorService;

    @Test
    void shouldRegisterDonorSuccessfully_whenEmailIsUnique() {
        RegisterDonorDTO dto = new RegisterDonorDTO();
        dto.setName("Test User");
        dto.setEmail("unique@example.com");
        dto.setPassword("pass123");
        dto.setPhone("01000000000");
        dto.setCity("Cairo");

        when(donorRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        System.out.println("Donor Registered Successfully");
        assertDoesNotThrow(() -> donorService.registerDonor(dto));
        verify(donorRepository).save(any(Donor.class));
    }

    @Test
    void shouldThrowException_whenEmailAlreadyExists() {
        RegisterDonorDTO dto = new RegisterDonorDTO();
        dto.setEmail("test@gmail.com");

        when(donorRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        System.out.println("Email Already Exists");
        assertThrows(EmailAlreadyUsedException.class, () -> donorService.registerDonor(dto));
        verify(donorRepository, never()).save(any());
    }

    @Test
    void loginDonor_shouldSucceed_withCorrectCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("testuser@example.com");
        loginDTO.setPassword("correct123");

        Donor donor = new Donor();
        donor.setId(1L);
        donor.setEmail("testuser@example.com");
        donor.setPassword("correct123");
        donor.setName("Test Donor");

        when(donorRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(donor));

        DonorDTO result = donorService.loginDonor(loginDTO);

        assertEquals("Test Donor", result.getName());
        assertEquals("testuser@example.com", result.getEmail());
    }

    @Test
    void loginDonor_shouldThrowInvalidCredentials_withWrongPassword() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("testuser@example.com");
        loginDTO.setPassword("wrongpass");

        Donor donor = new Donor();
        donor.setId(1L);
        donor.setEmail("testuser@example.com");
        donor.setPassword("correct123");

        when(donorRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(donor));

        assertThrows(InvalidCredentialsException.class, () -> donorService.loginDonor(loginDTO));
    }

    @Test
    void loginDonor_shouldThrowResourceNotFound_whenEmailNotExists() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("notfound@example.com");
        loginDTO.setPassword("anything");

        when(donorRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> donorService.loginDonor(loginDTO));
    }
}