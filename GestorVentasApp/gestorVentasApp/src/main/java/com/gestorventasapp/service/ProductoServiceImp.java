package com.gestorventasapp.service;

import java.util.Iterator;
import java.util.List;

import com.gestorventasapp.dao.ProductoDAO;
import com.gestorventasapp.dao.ProductoDAOImpl;
import com.gestorventasapp.model.Producto;

public class ProductoServiceImp implements ProductoService{
	
	private final ProductoDAO productoDAO = new ProductoDAOImpl();
	

	@Override
	public void saveProducto(Producto producto) {
		
		//Validacion: Verificar si ya existe un producto con el mismo nombre
		List<Producto> productosExistentes = getAllProductos();
		
	    for (Producto p : productosExistentes) {
	        if (p.getNombre().equalsIgnoreCase(producto.getNombre()) && p.getIdProducto() != producto.getIdProducto()) {
	            throw new IllegalArgumentException("Ya existe otro producto con el nombre: " + producto.getNombre());
	        }
	    }
		
		// Validacion: El nombre no puede ser nulo ni vacio
		if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del producto no puede estar vacio");
		}
		
		// Validacion: El precio debe ser mayor que 0
		if (producto.getPrecio() <= 0) {
			throw new IllegalArgumentException("El precio del producto debe ser mayor que 0.");
		}
		
		// Validacion: El stock no puede ser negativo
		if (producto.getStock() < 0) {
			throw new IllegalArgumentException("El stock del producto no puede ser negativo.");
		}
		
		productoDAO.save(producto);	
	}
	 

	@Override
	public void updateProducto(Producto producto) {
		
		//Validacion: Verificar si ya existe un producto con el mismo nombre
		List<Producto> productosExistentes = getAllProductos();
	    for (Producto p : productosExistentes) {
	        if (p.getNombre().equalsIgnoreCase(producto.getNombre()) && p.getIdProducto() != producto.getIdProducto()) {
	            throw new IllegalArgumentException("Ya existe otro producto con el nombre: " + producto.getNombre());
	        }
	    }
		
		// Validacion: El nombre no puede ser nulo ni vacio
		if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del producto no puede estar vacio");
		}
		
		//Validacion: El precio debe ser mayor que 0.
		if (producto.getPrecio() <= 0) {
			throw new IllegalArgumentException("El precio del producto debe ser mayor que 0.");
		}
		
		//Validacion: El stock no puede ser negativo
		if (producto.getStock() < 0 ) {
			throw new IllegalArgumentException("El stock del producto no puede ser negativo");
		}
		
		productoDAO.update(producto);
	}

	
	@Override
	public void deleteProducto(int idProducto) {
		productoDAO.delete(idProducto);
	}

	
	@Override
	public Producto getProductoById(int idProducto) {
		return productoDAO.findById(idProducto);
	}

	@Override
	public List<Producto> getAllProductos() {
		return productoDAO.findAll();
	}

}
