# Microservicio de Productos - Gestión de Inventario de Zapatillas

Este microservicio es el encargado de administrar el catálogo de productos (zapatillas) de la tienda, gestionando información crítica como precios, stock y marcas, además de proveer datos a otros microservicios del ecosistema.

## Funcionalidades Implementadas

* **CRUD Completo:** Gestión total del ciclo de vida de las zapatillas (Crear, Listar, Buscar, Actualizar y Eliminar).
* **Persistencia Robusta:** Integración con MySQL mediante **Spring Data JPA** para un manejo eficiente de los datos.
* **Migraciones Automatizadas:** Uso de **Flyway** para asegurar que la estructura de la base de datos sea consistente en cualquier entorno.
* **Validaciones de Integridad:** Implementación de **Bean Validation** para garantizar que los precios sean positivos y los campos obligatorios no estén vacíos.
* **Manejo Global de Excepciones:** Respuestas estandarizadas en formato JSON ante errores de validación o recursos no encontrados.
* **Trazabilidad con Logs:** Registro detallado de operaciones y errores mediante SLF4J para facilitar el debugging.

---

## Requisitos para Ejecución

1.  **MySQL:** Activo (Laragon/XAMPP) en el puerto `3306`.
2.  **Base de Datos:** `db_productos` (se crea automáticamente si no existe gracias a la configuración de la URL).
3.  **Credenciales:** `root` / `system` (Configurables en `application.properties`).
4.  **Microservicio Externo:** El microservicio de Clientes (Puerto `8081`) debe estar activo para pruebas de integración de validación remota.

---

## Guía de Pruebas en Postman (Puerto 8082)

### Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/productos` | Lista todo el inventario disponible. |
| **GET** | `/api/productos/{id}` | Obtiene el detalle de un producto específico. |
| **POST** | `/api/productos` | Registra un nuevo modelo de zapatilla. |
| **PUT** | `/api/productos/{id}` | Actualiza datos (precio, stock, etc.) de una zapatilla. |
| **DELETE** | `/api/productos/{id}` | Elimina físicamente un producto del sistema. |

---

### Escenarios de Prueba Sugeridos

#### 1. Crear Nuevo Producto (Éxito)
* **Método:** `POST`
* **Endpoint:** `http://localhost:8082/api/productos`
* **Body (JSON):**
    ```json
    {
        "nombre": "Air Max 270",
        "marca": "Nike",
        "precio": 129990.0,
        "stock": 20
    }
    ```
* **Resultado:** Status `201 Created` y el objeto creado con su ID asignado.

#### 2. Actualización de Stock
* **Método:** `PUT`
* **Endpoint:** `http://localhost:8082/api/productos/1`
* **Body:** Similar al POST pero con los valores actualizados.

#### 3. Error de Validación (Precio Negativo)
* **Método:** `POST`
* **Body:** Enviar un `"precio": -500`.
* **Resultado:** Status `400 Bad Request` con el detalle del error de validación.

#### 4. Búsqueda de Producto Inexistente
* **Método:** `GET`
* **Endpoint:** `http://localhost:8082/api/productos/999`
* **Resultado:** Status `404 Not Found` gestionado por el `GlobalExceptionHandler`.

---

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.6**
* **Spring Data JPA**
* **MySQL Connector/J**
* **Flyway DB**
* **Lombok**
* **Jakarta Validation**