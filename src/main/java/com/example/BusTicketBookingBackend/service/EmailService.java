package com.example.BusTicketBookingBackend.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String code) throws MessagingException;
    void sendForgotPasswordEmail(String email, String newPassword) throws MessagingException;


    void sendBookingDetailsEmail(Integer maDonDatVe) throws MessagingException;

    void sendSimpleCancelBookingEmail(Integer maDonDatVe) throws MessagingException;
}
