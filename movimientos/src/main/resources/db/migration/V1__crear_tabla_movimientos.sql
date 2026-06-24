CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    motivo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Carga de datos iniciales para la tienda
INSERT INTO movimientos (producto_id, cantidad, tipo, motivo, descripcion)
VALUES (1, 100, 'ENTRADA', 'REPOSICION', 'Carga inicial de stock Nike Zoom - Bodega Central');

-- Entrada inicial de Jordan Retro 1 (Asumiendo que es el producto ID 2)
INSERT INTO movimientos (producto_id, cantidad, tipo, motivo, descripcion)
VALUES (2, 50, 'ENTRADA', 'REPOSICION', 'Ingreso por recepción de proveedor - Jordan Retro 1');

-- Salida por venta del producto 1
INSERT INTO movimientos (producto_id, cantidad, tipo, motivo, descripcion)
VALUES (1, 2, 'SALIDA', 'VENTA', 'Venta registrada en tienda física');