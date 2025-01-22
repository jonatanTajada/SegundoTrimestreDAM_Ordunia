-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS gestorVentasApp;
USE gestorVentasApp;

-- Tabla Producto
CREATE TABLE IF NOT EXISTS Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0), -- Precio no puede ser negativo
    stock INT NOT NULL CHECK (stock >= 0) -- Stock no puede ser negativo
);

-- Tabla Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$'), -- Validación de email
    telefono VARCHAR(9) NOT NULL CHECK (telefono REGEXP '^[0-9]{9}$') -- Teléfono debe tener 9 dígitos numéricos
);

-- Tabla Venta
CREATE TABLE IF NOT EXISTS Venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Fecha por defecto
    id_cliente INT NOT NULL,
    
    CONSTRAINT fkCliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE
);

-- Tabla Detalle_Venta
CREATE TABLE IF NOT EXISTS Detalle_Venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0), -- Cantidad debe ser positiva
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0), -- Subtotal no puede ser negativo
    
    CONSTRAINT fkVenta FOREIGN KEY (id_venta) REFERENCES Venta(id_venta) ON DELETE CASCADE,
    CONSTRAINT fkProducto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE,
    UNIQUE (id_venta, id_producto) -- Garantiza que no se dupliquen combinaciones de venta y producto
);

-- --------------------------------------------------------------------------------------------------------------------------

-- Insertar productos
INSERT INTO Producto (nombre, precio, stock) VALUES
('Manzana', 0.50, 100),
('Pan', 1.20, 50),
('Leche', 0.90, 30),
('Arroz', 1.50, 200),
('Huevos', 2.40, 60);

-- Insertar clientes
INSERT INTO Cliente (nombre, email, telefono) VALUES
('Carlos Pérez', 'carlos.perez@example.com', '600123456'),
('Ana Gómez', 'ana.gomez@example.com', '610654321'),
('Luis Martínez', 'luis.martinez@example.com', '620987654'),
('María López', 'maria.lopez@example.com', '630876543');

-- Insertar ventas
INSERT INTO Venta (fecha, id_cliente) VALUES
('2025-01-18 10:30:00', 1), -- Venta de Carlos Pérez
('2025-01-18 11:00:00', 2), -- Venta de Ana Gómez
('2025-01-18 12:15:00', 3); -- Venta de Luis Martínez

-- Insertar detalles de ventas
INSERT INTO Detalle_Venta (id_venta, id_producto, cantidad, subtotal) VALUES
(1, 1, 5, 2.50),  -- Carlos Pérez compra 5 manzanas
(1, 2, 2, 2.40),  -- Carlos Pérez compra 2 panes
(2, 3, 3, 2.70),  -- Ana Gómez compra 3 leches
(2, 4, 1, 1.50),  -- Ana Gómez compra 1 arroz
(3, 5, 12, 28.80); -- Luis Martínez compra 12 huevos

-- -------------------------------------------------------------------------------------------
SELECT * FROM Producto;
SELECT * FROM Cliente;
SELECT * FROM Venta;
SELECT * FROM Detalle_Venta;


   # ALTER TABLE venta MODIFY COLUMN fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;


-- consulta para ver historial compras por id_cliente
SELECT 
    c.nombre AS Cliente,
    v.fecha AS Fecha_Venta,
    p.nombre AS Producto,
    dv.cantidad AS Cantidad,
    dv.subtotal AS Subtotal
FROM 
    Cliente c
JOIN 
    Venta v ON c.id_cliente = v.id_cliente
JOIN 
    Detalle_Venta dv ON v.id_venta = dv.id_venta
JOIN 
    Producto p ON dv.id_producto = p.id_producto
WHERE 
    c.id_cliente = 1; -- Cambia este ID por el cliente que quieres consultar
    
-- ------------------------------------------------------------------------------------------------------------

-- Índice para buscar ventas por cliente
CREATE INDEX idx_venta_cliente ON Venta (id_cliente);

-- Índice para buscar detalles por venta
CREATE INDEX idx_detalle_venta ON Detalle_Venta (id_venta);

-- Índice para buscar detalles por producto
CREATE INDEX idx_detalle_producto ON Detalle_Venta (id_producto);

-- ---------------------------------------------------------------------------------------------
-- ------# este codigo de abajo no esta ejecutado. Para hacerlo mas simple no lo he hecho.------
-- ---------------------------------------------------------------------------------------------

-- 2. Validaciones adicionales
-- Restricción para evitar ventas vacías:
-- En la tabla Venta, podríamos garantizar que no se permita insertar una venta sin detalles (aunque en Java controlaremos esto).

-- Trigger para evitar ventas sin detalles
DELIMITER $$

CREATE TRIGGER validar_venta_detalle
AFTER INSERT ON Venta
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Detalle_Venta WHERE id_venta = NEW.id_venta) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede insertar una venta sin productos.';
    END IF;
END$$

DELIMITER ;


-- 3.Auditoría (opcional)
-- Para un sistema profesional, podrías agregar una tabla de auditoría para registrar quién y cuándo realizó cambios importantes, como:

-- Inserciones o eliminaciones en Venta o Detalle_Venta.
-- Modificaciones en el stock de productos.

CREATE TABLE Auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    tabla_modificada VARCHAR(50),
    accion VARCHAR(50),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT
);


-- Trigger para registrar cambios:
-- Trigger de auditoría para cambios en Producto
DELIMITER $$
CREATE TRIGGER auditoria_producto
AFTER UPDATE ON Producto
FOR EACH ROW
BEGIN
    INSERT INTO Auditoria (tabla_modificada, accion, descripcion)
    VALUES ('Producto', 'UPDATE', CONCAT('Stock modificado de ', OLD.stock, ' a ', NEW.stock, ' para Producto ID: ', NEW.id_producto));
END$$

DELIMITER ;





















