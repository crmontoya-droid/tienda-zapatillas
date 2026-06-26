CREATE TABLE reclamos (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    venta_id BIGINT NOT NULL, -- Cambiado de pedido_id a venta_id
    motivo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    estado VARCHAR(50) DEFAULT 'ABIERTO' NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO reclamos (cliente_id, venta_id, motivo, descripcion, estado, fecha_creacion)
VALUES (1, 1, 'PRODUCTO_DEFECTUOSO', 'La zapatilla derecha llegó con una costura suelta.', 'ABIERTO', NOW());

INSERT INTO reclamos (cliente_id, venta_id, motivo, descripcion, estado, fecha_creacion)
VALUES (2, 2, 'TALLA_INCORRECTA', 'Pedí talla 42 y enviaron 40.', 'EN_REVISION', NOW());

INSERT INTO reclamos (cliente_id, venta_id, motivo, descripcion, estado, fecha_creacion)
VALUES (3, 3, 'RETRASO_ENVIO', 'El pedido debió llegar ayer y no hay actualizaciones.', 'CERRADO', NOW());