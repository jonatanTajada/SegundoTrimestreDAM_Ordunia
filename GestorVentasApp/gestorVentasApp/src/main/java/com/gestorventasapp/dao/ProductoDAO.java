package com.gestorventasapp.dao;

import java.util.List;

import com.gestorventasapp.model.Producto;

public interface ProductoDAO {
	
	void save(Producto producto); // Guardar un producto
	
    void update(Producto producto); // Actualizar un producto
    
    void delete(int idProducto); // Eliminar un producto por su ID
    
    Producto findById(int idProducto); // Buscar un producto por su ID
    
    List<Producto> findAll(); // Obtener todos los productos
	
}
