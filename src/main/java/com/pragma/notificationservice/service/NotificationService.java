package com.pragma.notificationservice.service;

import com.pragma.notificationservice.model.NotificationRequest;
import com.pragma.notificationservice.model.NotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    private static final String DEFAULT_SUBJECT = "Notificacion";

    private final EmailSender emailSender;

    public NotificationService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public NotificationResponse createNotification(NotificationRequest request) {
        log.debug("Initiating notification creation for recipient: {}", request.getRecipient());

        emailSender.sendEmail(request.getRecipient(), DEFAULT_SUBJECT, request.getMessage());

        log.debug("Email sent successfully to recipient: {}", request.getRecipient());
        return new NotificationResponse(
                Math.abs(UUID.randomUUID().getMostSignificantBits()),
                request.getRecipient(),
                request.getMessage(),
                LocalDateTime.now()
        );
    }
}
