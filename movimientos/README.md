# Microservicio de Movimientos - Gestión de Inventario y Kárdex

Este microservicio es responsable de la gestión y el control detallado de los movimientos de stock en la tienda de zapatillas. Funciona como un sistema de Kárdex, registrando cada entrada y salida de mercadería para garantizar la trazabilidad total del inventario.

## Funcionalidades Implementadas

* **Registro de Kárdex:** Control de entradas y salidas de stock con detalle de cantidad, tipo y motivo.
* **Validación de Integridad Referencial:** Integración mediante **OpenFeign** con el microservicio de Productos (Puerto `8082`) para asegurar que todo movimiento corresponda a un producto existente.
* **Registro Automatizado de Tiempos:** Uso de `@PrePersist` para capturar la fecha y hora exacta de cada transacción de inventario.
* **Persistencia Independiente:** Gestión de datos en una instancia dedicada de MySQL mediante **Spring Data JPA**.
* **Migraciones Automatizadas:** Implementación de **Flyway** para la creación de la estructura de tablas (`movimientos`) y carga de datos maestros iniciales.
* **Manejo de Errores Estandarizado:** Captura centralizada de excepciones para validar la existencia de productos y parámetros de entrada obligatorios.

---

## Requisitos para Ejecución

1.  **MySQL:** Instancia activa en el puerto `3306`.
2.  **Base de Datos:** `db_movimientos` (Flyway gestiona la estructura automáticamente).
3.  **Dependencias Críticas:**
    * **Microservicio Productos:** Puerto `8082` (Debe estar activo para validar los IDs de productos).
4.  **Puerto de Operación:** El microservicio se ejecuta en el puerto `8085`.

---

## Guía de Pruebas en Postman (Puerto 8085)

### Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/movimientos` | Lista el historial completo de movimientos (Kárdex). |
| **POST** | `/api/movimientos` | Registra una nueva entrada o salida (Valida existencia del Producto). |

---

### Escenarios de Prueba Sugeridos

#### 1. Registro de Entrada de Stock (Reposición)
* **Método:** `POST`
* **Endpoint:** `http://localhost:8085/api/movimientos`
* **Body (JSON):**
    ```json
    {
        "productoId": 1,
        "cantidad": 50,
        "tipo": "ENTRADA",
        "motivo": "REPOSICION",
        "descripcion": "Ingreso de stock Nike Zoom por nueva importación"
    }
    ```
* **Resultado:** Status `201 Created`. El sistema devuelve el movimiento con su `id` y `fechaMovimiento` generados.

#### 2. Error: Producto Inexistente (Validación Feign)
* **Método:** `POST`
* **Body:** Enviar un `productoId` que no exista en el catálogo (ej: 9999).
* **Resultado:** Status `404 Not Found`. Mensaje: *"Error: El producto ID 9999 no existe."*

#### 3. Registro de Salida de Stock (Venta/Ajuste)
* **Método:** `POST`
* **Body (JSON):**
    ```json
    {
        "productoId": 2,
        "cantidad": 5,
        "tipo": "SALIDA",
        "motivo": "VENTA",
        "descripcion": "Descuento de stock por venta directa"
    }
    ```

#### 4. Consulta de Historial Completo
* **Método:** `GET`
* **Endpoint:** `http://localhost:8085/api/movimientos`
* **Resultado:** Status `200 OK`. Lista de todas las transacciones, incluyendo las migraciones iniciales de Flyway.

---

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.6**
* **Spring Data JPA**
* **OpenFeign** (Comunicación con Productos en puerto 8082)
* **MySQL & Flyway**
* **Lombok**
* **Jakarta Validation**