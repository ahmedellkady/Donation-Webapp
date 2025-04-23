package com.example.donation_app.Exception;

public class NoDonationFoundException extends RuntimeException {
    public NoDonationFoundException(String message) {
        super(message);
    }
}
