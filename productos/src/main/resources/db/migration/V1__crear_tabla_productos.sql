CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL
);

INSERT INTO productos (nombre, marca, precio, stock) VALUES ('Air Jordan 1', 'Nike', 150000, 10);
INSERT INTO productos (nombre, marca, precio, stock) VALUES ('Ultraboost', 'Adidas', 130000, 15);
INSERT INTO productos (nombre, marca, precio, stock) VALUES ('Classic Leather', 'Reebok', 75000, 25);
INSERT INTO productos (nombre, marca, precio, stock) VALUES ('550', 'New Balance', 125000, 8);
INSERT INTO productos (nombre, marca, precio, stock) VALUES ('Suede Classic', 'Puma', 65000, 18);