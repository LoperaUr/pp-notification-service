# Notification Service Microservicio

Microservicio encargado de gestionar el envio de notificaciones por correo electronico usando AWS SES.

## Documentacion consolidada

- Requisitos compartidos: `..\requirements.md`
- Coleccion Postman unica: `..\postman_collections\plazoleta-pragma.postman_collection.json`

## Estado funcional vs requirements

| HU | Endpoint / Regla | Estado | Evidencia en el repo |
|---|---|---|---|
| #14 | Notificar pedido listo al cliente | Implementada | `POST /api/v1/notifications` en `NotificationController` |
| #5 | Endpoint protegido con JWT | Implementada | `SecurityConfig` exige autenticacion en `POST /api/v1/notifications` |
| Soporte interno | Envio de correo por AWS SES | Implementada | `SesEmailSender` y `AwsSesConfig` |

### Alcance actual

- El micro expone un endpoint para enviar notificaciones por correo.
- La autenticacion se valida por token JWT en `Authorization: Bearer <token>`.
- La integracion de envio se realiza con `SesClient` de AWS SDK.

## Variables de entorno

- `AWS_REGION`
- `AWS_SES_FROM_EMAIL`
- `JWT_SECRET`
- `JWT_EXPIRATION`

## Guia para construir y desplegar la imagen Docker en AWS ECR

### 1. Construir la imagen Docker

```sh
docker build -t nombre-imagen:tag .
```

### 2. Crear un repositorio en ECR (si no existe)

```sh
aws ecr create-repository --repository-name nombre-repositorio
```

### 3. Autenticarse en ECR

```sh
aws ecr get-login-password --region tu-region | docker login --username AWS --password-stdin <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com
```

### 4. Etiquetar la imagen

```sh
docker tag nombre-imagen:tag <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com/nombre-repositorio:tag
```

### 5. Subir la imagen a ECR

```sh
docker push <tu-id-cuenta>.dkr.ecr.<tu-region>.amazonaws.com/nombre-repositorio:tag
```

> Reemplaza `nombre-imagen`, `tag`, `nombre-repositorio`, `tu-region` y `tu-id-cuenta` por los valores correspondientes.
