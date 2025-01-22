package com.gestorventasapp.controller;

import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.model.Producto;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.service.DetalleVentaService;
import com.gestorventasapp.service.DetalleVentaServiceImpl;

import java.util.List;

public class DetalleVentaController {

	private final DetalleVentaService detalleVentaService;

	// Constructor para inicializar el servicio
	public DetalleVentaController() {
		this.detalleVentaService = new DetalleVentaServiceImpl();
	}

	// Metodo para agregar un detalle de venta
	public String addDetalleVenta(Venta venta, Producto producto, int cantidad) {
		try {
			// Validar que los datos no sean nulos o invalidos
			if (venta == null) {
				return "Error: El detalle debe estar asociado a una venta.";
			}
			if (producto == null) {
				return "Error: El detalle debe estar asociado a un producto.";
			}
			if (cantidad <= 0) {
				return "Error: La cantidad debe ser mayor que 0.";
			}

			// Crear y guardar el detalle de venta
			DetalleVenta detalleVenta = new DetalleVenta();
			detalleVenta.setVenta(venta);
			detalleVenta.setProducto(producto);
			detalleVenta.setCantidad(cantidad);

			// Calcular el subtotal antes de guardar
			detalleVenta.calcularSubtotal();

			// Guardar el detalle de venta
			detalleVentaService.saveDetalleVenta(detalleVenta);
			return "Detalle de venta agregado correctamente.";
		} catch (Exception e) {
			return "Error al agregar el detalle de venta: " + e.getMessage();
		}
	}

	// Metodo para actualizar un detalle de venta
	public String updateDetalleVenta(int idDetalle, Venta venta, Producto producto, int cantidad) {
		try {
			// Validar que los datos no sean nulos o invalidos
			if (venta == null) {
				return "Error: El detalle debe estar asociado a una venta.";
			}
			if (producto == null) {
				return "Error: El detalle debe estar asociado a un producto.";
			}
			if (cantidad <= 0) {
				return "Error: La cantidad debe ser mayor que 0.";
			}

			// Obtener el detalle de venta existente por ID
			DetalleVenta detalleExistente = detalleVentaService.getDetalleVentaById(idDetalle);
			if (detalleExistente == null) {
				return "Error: El detalle de venta no existe.";
			}

			// Actualizar los datos del detalle de venta
			detalleExistente.setVenta(venta);
			detalleExistente.setProducto(producto);
			detalleExistente.setCantidad(cantidad);

			// Calcular el subtotal antes de actualizar
			detalleExistente.calcularSubtotal();

			// Guardar la actualizacion del detalle de venta
			detalleVentaService.updateDetalleVenta(detalleExistente);
			return "Detalle de venta actualizado correctamente.";
		} catch (Exception e) {
			return "Error al actualizar el detalle de venta: " + e.getMessage();
		}
	}

	// Metodo para eliminar un detalle de venta
	public String deleteDetalleVenta(int idDetalle) {
		try {
			// Eliminar el detalle de venta por ID
			detalleVentaService.deleteDetalleVenta(idDetalle);
			return "Detalle de venta eliminado correctamente.";
		} catch (Exception e) {
			return "Error al eliminar el detalle de venta: " + e.getMessage();
		}
	}

	// Metodo para obtener un detalle de venta por su ID
	public DetalleVenta getDetalleVentaById(int idDetalle) {
		return detalleVentaService.getDetalleVentaById(idDetalle);
	}

	// Metodo para obtener todos los detalles de venta
	public List<DetalleVenta> getAllDetalleVentas() {
		return detalleVentaService.getAllDetalleVentas();
	}
}
