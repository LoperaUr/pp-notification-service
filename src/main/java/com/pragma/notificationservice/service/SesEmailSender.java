package com.pragma.notificationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

@Component
public class SesEmailSender implements EmailSender {

    private final SesClient sesClient;
    private final String fromEmail;

    public SesEmailSender(SesClient sesClient, @Value("${aws.ses.from-email}") String fromEmail) {
        this.sesClient = sesClient;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendEmail(String recipient, String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .source(fromEmail)
                .destination(destination -> destination.toAddresses(recipient))
                .message(message -> message
                        .subject(subjectContent -> subjectContent.data(subject))
                        .body(messageBody -> messageBody
                                .text(textContent -> textContent.data(body))))
                .build();

        try {
            sesClient.sendEmail(request);
        } catch (SesException ex) {
            throw new IllegalStateException("No fue posible enviar el correo con AWS SES", ex);
        }
    }
}
