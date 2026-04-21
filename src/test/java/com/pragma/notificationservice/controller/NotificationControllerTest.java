package com.pragma.notificationservice.controller;

import com.pragma.notificationservice.model.NotificationRequest;
import com.pragma.notificationservice.model.NotificationResponse;
import com.pragma.notificationservice.service.NotificationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    private NotificationController notificationController;
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        notificationController = new NotificationController(notificationService);
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    void tearDown() {
        validatorFactory.close();
    }

    @Test
    void createShouldDelegateToServiceAndReturnResponse() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("cliente@correo.com");
        request.setMessage("Tu pedido va en camino");

        NotificationResponse expectedResponse = new NotificationResponse(
                10L,
                "cliente@correo.com",
                "Tu pedido va en camino",
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        when(notificationService.createNotification(request)).thenReturn(expectedResponse);

        NotificationResponse response = notificationController.create(request);

        verify(notificationService).createNotification(request);
        assertEquals(10L, response.getId());
        assertEquals("cliente@correo.com", response.getRecipient());
    }

    @Test
    void notificationRequestShouldFailValidationWhenEmailIsInvalid() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("correo-invalido");
        request.setMessage("Mensaje de prueba");

        Set<ConstraintViolation<NotificationRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
    }
}
