package com.example.donation_app.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.donation_app.DTO.DonorSessionDTO;
import com.example.donation_app.Model.Donor;
import com.example.donation_app.Model.DonorSession;
import com.example.donation_app.Repository.DonorRepository;
import com.example.donation_app.Repository.DonorSessionRepository;

@Service
public class DonorSessionService {
    private final DonorSessionRepository donorSessionRepository;
    private final DonorRepository donorRepository;

    public DonorSessionService(DonorSessionRepository donorSessionRepository, DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
        this.donorSessionRepository = donorSessionRepository;
    }

    private DonorSessionDTO map(DonorSession session) {
        DonorSessionDTO dto = new DonorSessionDTO();
        dto.setId(session.getId());
        dto.setDonorId(session.getDonor().getId());
        dto.setStartTime(session.getSessionStart());
        dto.setEndTime(session.getSessionEnd());
        return dto;
    }

    public DonorSessionDTO startSession(Long donorId) {
        DonorSession session = new DonorSession();
        Donor donor = donorRepository.findById(donorId).get();

        session.setDonor(donor);
        session.setSessionStart(LocalDateTime.now());
        donorSessionRepository.save(session);

        return map(session);
    }

    public void endSession(Long sessionId) {
        DonorSession session = donorSessionRepository.findById(sessionId).get();
        session.setSessionEnd(LocalDateTime.now());
        donorSessionRepository.save(session);
    }

    public void maintainsSessionLimit(Long donorId, int maxSessions) {
        List<DonorSession> sessions = donorSessionRepository.findSessionsForDonor(donorId);
        if (sessions.size() > maxSessions) {
            List<DonorSession> sessionsToDelete = sessions.subList(maxSessions, sessions.size());
            donorSessionRepository.deleteAll(sessionsToDelete);
        }
    }
}
