# BASE DE DATOS - PROYECTO: AGENDA_FX

CREATE DATABASE AgendaDB;
USE AgendaDB;

CREATE TABLE contactos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(9) NOT NULL UNIQUE,
    localidad VARCHAR(100) NOT NULL, -- 
    imagen VARCHAR(255),
    sitioWeb VARCHAR(255)
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- insertar datos

INSERT INTO contactos (nombre, correo, telefono, localidad, imagen, sitioWeb) VALUES
('Juan Pérez', 'juan.perez@email.com', '611223344', 'Madrid', NULL, 'https://juanperez.com'),
('María López', 'maria.lopez@email.com', '622334455', 'Barcelona', NULL, 'https://marialopez.com'),
('Carlos Rodríguez', 'carlos.rodri@email.com', '633445566', 'Bilbao', NULL, 'https://carlosrodri.com'),
('Laura Fernández', 'laura.fernandez@email.com', '644556677', 'Sevilla', NULL, 'https://laurafernandez.com'),
('David Gómez', 'david.gomez@email.com', '655667788', 'Valencia', NULL, NULL),
('Ana Martínez', 'ana.martinez@email.com', '666778899', 'Zaragoza', NULL, 'https://anamartinez.com'),
('Pedro Sánchez', 'pedro.sanchez@email.com', '677889900', 'Granada', NULL, NULL),
('Isabel Torres', 'isabel.torres@email.com', '688990011', 'Málaga', NULL, 'https://isabeltorres.com'),
('Fernando Ruiz', 'fernando.ruiz@email.com', '699001122', 'Santander', NULL, 'https://fernandoruiz.com'),
('Elena Navarro', 'elena.navarro@email.com', '700112233', 'Alicante', NULL, NULL),
('Sergio Herrera', 'sergio.herrera@email.com', '711223344', 'Vigo', NULL, 'https://sergioherrera.com'),
('Beatriz Domínguez', 'beatriz.dominguez@email.com', '722334455', 'Oviedo', NULL, NULL),
('Andrés Castro', 'andres.castro@email.com', '733445566', 'Toledo', NULL, 'https://andrescastro.com'),
('Natalia Rivas', 'natalia.rivas@email.com', '744556677', 'Burgos', NULL, NULL),
('Hugo Ramírez', 'hugo.ramirez@email.com', '755667788', 'Murcia', NULL, 'https://hugoramirez.com');


