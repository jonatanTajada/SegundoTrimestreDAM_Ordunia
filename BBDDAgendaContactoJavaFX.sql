create database AgendaDB;
USE AgendaDB;


CREATE TABLE contactos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(9) NOT NULL UNIQUE,
    imagen VARCHAR(255),
    sitioWeb VARCHAR(255)
);
