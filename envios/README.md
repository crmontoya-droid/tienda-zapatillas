# Microservicio de Envíos - Gestión de Despachos y Logística

Este microservicio se encarga de la gestión y seguimiento de los despachos de productos. Su función principal es vincular una venta existente con una orden de envío, generando automáticamente la información de seguimiento y gestionando los estados del proceso logístico.

## Funcionalidades Implementadas

* **Programación de Despachos:** Registro de órdenes de envío asociadas a transacciones de venta confirmadas.
* **Validación de Integridad Referencial:** Integración mediante **OpenFeign** con el microservicio de Ventas (Puerto `8083`) para asegurar que el despacho corresponda a una transacción real.
* **Generación de Tracking:** Creación automática de códigos de seguimiento únicos (UUID) para cada despacho programado.
* **Gestión de Estados:** Control del ciclo de vida del envío, iniciando por defecto en estado de preparación.
* **Persistencia Independiente:** Administración de datos en una instancia propia de MySQL mediante **Spring Data JPA**.
* **Migraciones Automatizadas:** Uso de **Flyway** para la creación y mantenimiento de la estructura de tablas de logística.
* **Manejo de Errores Estandarizado:** Captura centralizada de excepciones para garantizar respuestas coherentes en formato JSON.

---

## Requisitos para Ejecución

1.  **MySQL:** Instancia activa en el puerto `3306`.
2.  **Base de Datos:** `db_envios` (se crea automáticamente al iniciar el servicio).
3.  **Dependencias Críticas:**
    * **Microservicio Ventas:** Puerto `8083` (Debe estar en ejecución para validar las órdenes).
4.  **Puerto de Operación:** El microservicio se ejecuta en el puerto `8084`.

---

## Guía de Pruebas en Postman (Puerto 8084)

### Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/envios` | Lista todos los despachos registrados en el sistema. |
| **GET** | `/api/envios/{id}` | Detalle de un envío específico por su ID. |
| **POST** | `/api/envios` | Registra un nuevo despacho (Valida VentaId y genera tracking). |
| **DELETE** | `/api/envios/{id}` | Elimina un registro de despacho del sistema. |

---

### Escenarios de Prueba Sugeridos

#### 1. Programación de Envío Exitoso
* **Método:** `POST`
* **Endpoint:** `http://localhost:8084/api/envios`
* **Body (JSON):**
    ```json
    {
        "ventaId": 1,
        "direccion": "Avenida España 440, Santiago"
    }
    ```
* **Resultado:** Status `201 Created`. El sistema devuelve el objeto con un `numeroSeguimiento` generado (ej: TRK-A1B2C3D4) y el estado "PREPARACION".

#### 2. Error: Venta Inexistente (Validación Feign)
* **Método:** `POST`
* **Body:** Enviar un `ventaId` que no exista en el microservicio de Ventas (ej: 999).
* **Resultado:** Status `404 Not Found`. Mensaje: *"No se puede crear el envío: La venta especificada no existe."*

#### 3. Error: Validación de Dirección
* **Método:** `POST`
* **Body:** Enviar el campo `"direccion"` vacío o nulo.
* **Resultado:** Status `400 Bad Request` indicando que la dirección es obligatoria.

#### 4. Consulta de Historial de Despachos
* **Método:** `GET`
* **Endpoint:** `http://localhost:8084/api/envios`
* **Resultado:** Status `200 OK`. Lista de todos los envíos, incluyendo los cargados por el script SQL inicial.

---

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.6**
* **Spring Data JPA**
* **OpenFeign** (Comunicación Síncrona con Ventas)
* **MySQL & Flyway**
* **Lombok**
* **Jakarta Validation**