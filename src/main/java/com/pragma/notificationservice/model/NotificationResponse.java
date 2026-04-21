package com.pragma.notificationservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private final Long id;
    private final String recipient;
    private final String message;
    private final LocalDateTime createdAt;

    public NotificationResponse(Long id, String recipient, String message, LocalDateTime createdAt) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.createdAt = createdAt;
    }

}

