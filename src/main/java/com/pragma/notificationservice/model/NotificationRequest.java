package com.pragma.notificationservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    @Email
    @Size(max = 120)
    private String recipient;

    @NotBlank
    @Size(max = 4000)
    private String message;

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
