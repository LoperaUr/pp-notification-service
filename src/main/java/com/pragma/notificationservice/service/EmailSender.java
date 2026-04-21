package com.pragma.notificationservice.service;

public interface EmailSender {

    void sendEmail(String recipient, String subject, String body);
}

