# pp-notification-service

Servicio de notificaciones con arquitectura por capas regular.

## Estructura

- `controller`: expone endpoints REST.
- `service`: contiene logica de negocio e integracion con AWS SES.
- `model`: modelos de request/response.
- `config`: configuracion de clientes externos y seguridad.

## Seguridad

- `POST /api/v1/notifications` requiere autenticacion con `Authorization: Bearer <token>`.
- Variables JWT:
  - `JWT_SECRET`
  - `JWT_EXPIRATION` (milisegundos)

## Variables AWS SES

- `AWS_REGION`
- `AWS_SES_FROM_EMAIL`

## Paquete base

`com.pragma.notificationservice`

## Ejecutar pruebas

```bash
./gradlew test
```
