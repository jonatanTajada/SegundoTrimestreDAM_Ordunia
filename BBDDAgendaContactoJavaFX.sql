CREATE DATABASE AgendaDB;

USE AgendaDB;

CREATE TABLE contactos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100),
    telefono VARCHAR(20),
    imagen VARCHAR(255), -- Ruta de la imagen guardada
    sitioWeb VARCHAR(255)
);
