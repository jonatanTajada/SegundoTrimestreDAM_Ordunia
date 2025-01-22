package com.gestorventasapp.controller;

import com.gestorventasapp.model.Producto;
import com.gestorventasapp.service.ProductoService;
import com.gestorventasapp.service.ProductoServiceImp;
import java.util.List;

public class ProductoController {

	private final ProductoService productoService;

	public ProductoController() {
		this.productoService = new ProductoServiceImp();
	}

	public List<Producto> getAllProducts() {
		return productoService.getAllProductos();
	}

	public String addProduct(String nombre, double precio, int stock) {
		try {
			Producto producto = new Producto();
			producto.setNombre(nombre);
			producto.setPrecio(precio);
			producto.setStock(stock);

			productoService.saveProducto(producto);
			return "Producto agregado correctamente.";
		} catch (IllegalArgumentException e) {
			return "Error al agregar producto: " + e.getMessage();
		} catch (Exception e) {
			return "Error inesperado: " + e.getMessage();
		}
	}

	public String updateProduct(int id, String nombre, double precio, int stock) {
		try {
			Producto producto = new Producto();
			producto.setIdProducto(id);
			producto.setNombre(nombre);
			producto.setPrecio(precio);
			producto.setStock(stock);

			productoService.updateProducto(producto);
			return "Producto actualizado correctamente.";
		} catch (IllegalArgumentException e) {
			return "Error al actualizar producto: " + e.getMessage();
		} catch (Exception e) {
			return "Error inesperado: " + e.getMessage();
		}
	}

	public String deleteProduct(int id) {
		try {
			productoService.deleteProducto(id);
			return "Producto eliminado correctamente.";
		} catch (Exception e) {
			return "Error al eliminar producto: " + e.getMessage();
		}
	}
}
