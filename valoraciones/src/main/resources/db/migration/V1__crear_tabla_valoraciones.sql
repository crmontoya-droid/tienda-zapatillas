-- 1. Crear la tabla
CREATE TABLE valoraciones (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
cliente_id BIGINT NOT NULL,
producto_id BIGINT NOT NULL,
puntuacion INT NOT NULL CHECK (puntuacion >= 1 AND puntuacion <= 5),
comentario VARCHAR(500),
fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 2. Insertar datos de prueba
INSERT INTO valoraciones (cliente_id, producto_id, puntuacion, comentario, fecha_creacion)
VALUES (1, 501, 5, 'Excelentes zapatillas, muy cómodas para correr.', NOW());

INSERT INTO valoraciones (cliente_id, producto_id, puntuacion, comentario, fecha_creacion)
VALUES (2, 502, 3, 'El diseño es bonito, pero la talla es un poco ajustada.', NOW());

INSERT INTO valoraciones (cliente_id, producto_id, puntuacion, comentario, fecha_creacion)
VALUES (3, 503, 1, 'Mala calidad, se despegaron al mes de uso.', NOW());