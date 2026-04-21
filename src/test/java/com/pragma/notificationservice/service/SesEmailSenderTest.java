package com.pragma.notificationservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;
import software.amazon.awssdk.services.ses.model.SesException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SesEmailSenderTest {

    @Mock
    private SesClient sesClient;

    @Test
    void sendEmailShouldBuildRequestAndCallSes() {
        when(sesClient.sendEmail(any(SendEmailRequest.class))).thenReturn(SendEmailResponse.builder().build());
        SesEmailSender emailSender = new SesEmailSender(sesClient, "sender@test.com");

        emailSender.sendEmail("destino@test.com", "Asunto", "Cuerpo");

        ArgumentCaptor<SendEmailRequest> captor = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(sesClient).sendEmail(captor.capture());

        SendEmailRequest sentRequest = captor.getValue();
        assertEquals("sender@test.com", sentRequest.source());
        assertEquals("destino@test.com", sentRequest.destination().toAddresses().getFirst());
        assertEquals("Asunto", sentRequest.message().subject().data());
        assertEquals("Cuerpo", sentRequest.message().body().text().data());
    }

    @Test
    void sendEmailShouldThrowIllegalStateExceptionWhenSesFails() {
        SesEmailSender emailSender = new SesEmailSender(sesClient, "sender@test.com");
        when(sesClient.sendEmail(any(SendEmailRequest.class)))
                .thenThrow(SesException.builder().message("SES error").build());

        assertThrows(IllegalStateException.class,
                () -> emailSender.sendEmail("destino@test.com", "Asunto", "Cuerpo"));
    }
}

