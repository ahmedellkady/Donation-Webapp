package com.example.donation_app.Exception;

public class NoPendingCharitiesException extends RuntimeException {
    public NoPendingCharitiesException(String message) {
        super(message);
    }
}
