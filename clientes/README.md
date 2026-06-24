# Microservicio de Clientes - Gestión de Datos Maestros

Este microservicio es el encargado de administrar la información centralizada de los clientes del sistema. Actúa como el proveedor de identidad principal para los procesos de validación en los microservicios de Ventas y Envíos.

## Funcionalidades Implementadas

* **CRUD Completo:** Gestión total del ciclo de vida de los clientes (Crear, Listar, Buscar y Eliminar).
* **Persistencia de Datos:** Integración con MySQL mediante **Spring Data JPA** para asegurar la consistencia de la información.
* **Control de Versiones de Esquema:** Uso de **Flyway** para la creación automática de tablas y manejo de migraciones de base de datos.
* **Validaciones de Integridad:** Aplicación de **Jakarta Validation** para garantizar formatos de email correctos, campos obligatorios y coherencia en fechas de nacimiento.
* **Manejo Global de Excepciones:** Centralización de errores mediante `@ControllerAdvice`, entregando respuestas estandarizadas en formato JSON.
* **Trazabilidad de Operaciones:** Registro de actividad mediante SLF4J para el monitoreo de peticiones, eventos de persistencia y errores de sistema.
* **Configuración de Acceso:** Ajustes en la capa de seguridad para permitir la comunicación síncrona desde otros microservicios de la arquitectura.

---

## Requisitos para Ejecución

1.  **MySQL:** Instancia activa en el puerto `3306`.
2.  **Base de Datos:** `db_clientes` (el sistema la crea automáticamente al iniciar).
3.  **Credenciales:** `root` / `system` (Configurables en `application.properties`).
4.  **Puerto de Operación:** El microservicio se ejecuta en el puerto `8081`.

---

## Guía de Pruebas en Postman (Puerto 8081)

### Endpoints Disponibles

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/clientes` | Obtiene el listado de todos los clientes registrados. |
| **GET** | `/api/clientes/{id}` | Obtiene el detalle de un cliente específico por su ID. |
| **POST** | `/api/clientes` | Registra un nuevo cliente en el sistema. |
| **DELETE** | `/api/clientes/{id}` | Elimina de forma definitiva el registro de un cliente. |

---

### Escenarios de Prueba Sugeridos

#### 1. Registro de Cliente (Exitoso)
* **Método:** `POST`
* **Endpoint:** `http://localhost:8081/api/clientes`
* **Body (JSON):**
    ```json
    {
        "nombre": "Juan",
        "apellido": "Perez",
        "email": "juan.perez@dominio.cl",
        "fechaNacimiento": "1990-05-15"
    }
    ```
* **Resultado:** Status `201 Created` con el objeto persistido y su ID asignado.

#### 2. Error de Validación (Formato de Email)
* **Método:** `POST`
* **Body:** Enviar un email con formato inválido (ej: "juan.perez@error").
* **Resultado:** Status `400 Bad Request` indicando la falla en la validación del campo.

#### 3. Búsqueda por Identificador
* **Método:** `GET`
* **Endpoint:** `http://localhost:8081/api/clientes/1`
* **Resultado:** Status `200 OK` con la información del cliente solicitado.

#### 4. Recurso no Encontrado
* **Método:** `GET`
* **Endpoint:** `http://localhost:8081/api/clientes/999`
* **Resultado:** Status `404 Not Found` gestionado por el manejador de excepciones global.

---

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.6**
* **Spring Data JPA**
* **MySQL Connector/J**
* **Flyway DB**
* **Lombok**
* **Jakarta Validation**