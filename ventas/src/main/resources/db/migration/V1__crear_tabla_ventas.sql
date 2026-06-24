CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    total DOUBLE NOT NULL,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO ventas (cliente_id, producto_id, cantidad, total) VALUES (1, 1, 2, 300000);

INSERT INTO ventas (cliente_id, producto_id, cantidad, total) VALUES (2, 2, 1, 120000);

INSERT INTO ventas (cliente_id, producto_id, cantidad, total) VALUES (3, 3, 1, 95000);