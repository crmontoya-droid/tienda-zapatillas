CREATE TABLE envios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id BIGINT NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    estado VARCHAR(50) DEFAULT 'PREPARACION',
    numero_seguimiento VARCHAR(100),
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO envios (venta_id, direccion, estado, numero_seguimiento)
VALUES (1, 'Av. España 440, Santiago Centro', 'PREPARACION', 'TRK-ALPHA-001');

INSERT INTO envios (venta_id, direccion, estado, numero_seguimiento)
VALUES (2, 'Pajaritos 1230, Maipú', 'EN_CAMINO', 'TRK-BETA-002');

INSERT INTO envios (venta_id, direccion, estado, numero_seguimiento)
VALUES (3, 'Concha y Toro 550, Puente Alto', 'PREPARACION', 'TRK-GAMMA-003');