package com.example.donation_app.DTO;

import java.time.LocalDateTime;

import com.example.donation_app.Enum.ActionType;
import com.example.donation_app.Enum.DonationType;
import com.example.donation_app.Enum.NeedUrgency;

public class DonorActivityDTO {
    private Long id;
    private Long donorId;
    private Long charityId;
    private ActionType actionType;
    private LocalDateTime timestamp;
    private Long sessionId;
    private DonationType needType;
    private NeedUrgency needUrgency;
    private Integer quantityDonated;
    private LocalDateTime needCreatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public Long getCharityId() {
        return charityId;
    }

    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public DonationType getNeedType() {
        return needType;
    }

    public void setNeedType(DonationType needType) {
        this.needType = needType;
    }

    public NeedUrgency getNeedUrgency() {
        return needUrgency;
    }

    public void setNeedUrgency(NeedUrgency needUrgency) {
        this.needUrgency = needUrgency;
    }

    public Integer getQuantityDonated() {
        return quantityDonated;
    }

    public void setQuantityDonated(Integer quantityDonated) {
        this.quantityDonated = quantityDonated;
    }

    public LocalDateTime getNeedCreatedAt() {
        return needCreatedAt;
    }

    public void setNeedCreatedAt(LocalDateTime needCreatedAt) {
        this.needCreatedAt = needCreatedAt;
    }

    

}
