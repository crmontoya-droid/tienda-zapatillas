CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    venta_id BIGINT NOT NULL,
    monto DOUBLE NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    estado VARCHAR(50) DEFAULT 'PENDIENTE' NOT NULL,
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO pagos (cliente_id, venta_id, monto, metodo_pago, estado, fecha_pago)
VALUES (1, 1, 300000, 'TARJETA_CREDITO', 'APROBADO', NOW());

INSERT INTO pagos (cliente_id, venta_id, monto, metodo_pago, estado, fecha_pago)
VALUES (2, 2, 120000, 'DEBITO', 'APROBADO', NOW());

INSERT INTO pagos (cliente_id, venta_id, monto, metodo_pago, estado, fecha_pago)
VALUES (3, 3, 95000, 'TRANSFERENCIA', 'PENDIENTE', NOW());