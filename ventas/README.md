# Microservicio de Ventas - Gestión de Transacciones de Zapatillas

Este microservicio se encarga de orquestar el proceso de venta, comunicándose con los microservicios de Clientes y Productos para asegurar la integridad de cada transacción y calcular los montos finales de forma automática.

## 🚀 Funcionalidades Implementadas

* **Procesamiento de Ventas:** Registro centralizado de transacciones vinculando clientes y productos.
* **Cálculo Automático de Totales:** Integración mediante **OpenFeign** con el microservicio de Productos (Puerto `8082`) para obtener precios actualizados.
* **Validación Cruzada:** Verificación remota de la existencia del Cliente (Puerto `8081`) antes de procesar la orden.
* **Persistencia y Migraciones:** Gestión de base de datos MySQL con **Flyway**, garantizando un historial consistente.
* **Logs y Trazabilidad:** Registro detallado de cada paso del proceso (validación, comunicación externa y guardado) mediante SLF4J.
* **Estandarización de Respuestas:** Uso de un objeto `ApiResponse` único para mantener la coherencia en todo el sistema.

---

## Requisitos para Ejecución

1.  **MySQL:** Activo (Laragon/XAMPP) en el puerto `3306`.
2.  **Base de Datos:** `db_ventas` (se crea automáticamente al iniciar).
3.  **Dependencias Externas:**
    * **Microservicio Clientes:** Puerto `8081` (En ejecución).
    * **Microservicio Productos:** Puerto `8082` (En ejecución).

---

## Guía de Pruebas en Postman (Puerto 8083)

### Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/ventas` | Obtiene el historial completo de ventas. |
| **GET** | `/api/ventas/{id}` | Busca el detalle de una venta específica. |
| **POST** | `/api/ventas` | Procesa una nueva venta (Valida IDs y calcula total). |
| **DELETE** | `/api/ventas/{id}` | Elimina un registro del historial. |

---

### Escenarios de Prueba Sugeridos

#### 1. Venta Exitosa (Cálculo Automático)
* **Método:** `POST`
* **Endpoint:** `http://localhost:8083/api/ventas`
* **Body (JSON):**
    ```json
    {
        "clienteId": 1,
        "productoId": 1,
        "cantidad": 2
    }
    ```
* **Resultado:** Status `201 Created`. El sistema debe devolver el `total` calculado basándose en el precio traído de Productos.

#### 2. Error: Cliente Inexistente (Validación Feign)
* **Método:** `POST`
* **Body:** Usar un `clienteId` que no exista en `db_clientes`.
* **Resultado:** Status `404 Not Found`. Mensaje: *"No se puede vender: El cliente no existe."*

#### 3. Error: Producto Inexistente
* **Método:** `POST`
* **Body:** Usar un `productoId` que no esté en el catálogo de productos.
* **Resultado:** Status `404 Not Found`. Mensaje: *"Error: El producto no existe en el catálogo."*

#### 4. Error: Validación de Datos (Bean Validation)
* **Método:** `POST`
* **Body:** Enviar `"cantidad": 0` o un valor negativo.
* **Resultado:** Status `400 Bad Request`. Indica que la cantidad mínima debe ser 1.

---

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.6**
* **Spring Data JPA**
* **OpenFeign** (Comunicación Síncrona)
* **MySQL & Flyway**
* **Lombok**