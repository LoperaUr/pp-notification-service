package com.pragma.notificationservice.service;

import com.pragma.notificationservice.model.NotificationRequest;
import com.pragma.notificationservice.model.NotificationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void createNotificationShouldSendEmailAndReturnResponse() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("cliente@correo.com");
        request.setMessage("Tu pedido va en camino");

        NotificationResponse response = notificationService.createNotification(request);

        verify(emailSender).sendEmail("cliente@correo.com", "Notificacion", "Tu pedido va en camino");
        assertNotNull(response.getId());
        assertEquals("cliente@correo.com", response.getRecipient());
        assertEquals("Tu pedido va en camino", response.getMessage());
        assertNotNull(response.getCreatedAt());
    }
}
