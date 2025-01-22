package com.gestorventasapp.service;

import java.util.List;

import com.gestorventasapp.model.Producto;

public interface ProductoService {
	
	void saveProducto(Producto producto); // Guardar un nuevo producto
	
    void updateProducto(Producto producto); // Actualizar un producto existente
    
    void deleteProducto(int idProducto); // Eliminar un producto por ID
    
    Producto getProductoById(int idProducto); // Obtener un producto por su ID
    
    List<Producto> getAllProductos(); // Obtener todos los productos
	
	
}
