CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Carlos', 'Mendez', 'c.mendez@cliente.cl', '1995-04-12');

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Sofia', 'Castro', 's.castro@cliente.cl', '1998-08-25');

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Matias', 'Villanueva', 'm.villanueva@cliente.cl', '2001-12-05');

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Valentina', 'Rojas', 'v.rojas@cliente.cl', '1992-02-28');

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Felipe', 'Espinoza', 'f.espinoza@cliente.cl', '2004-10-18');

INSERT INTO clientes (nombre, apellido, email, fecha_nacimiento)
VALUES ('Erwin', 'Aguilera', 'e.aguilera@cliente.cl', '1987-09-18');