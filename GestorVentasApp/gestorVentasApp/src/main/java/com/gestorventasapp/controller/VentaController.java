package com.gestorventasapp.controller;

import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.service.VentaService;
import com.gestorventasapp.service.VentaServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class VentaController {

	private final VentaService ventaService;

	// Constructor para inicializar el servicio
	public VentaController() {
		this.ventaService = new VentaServiceImpl();
	}

	// Metodo para agregar una venta
	public String addVenta(Cliente cliente, LocalDateTime fecha) {
		try {
			// Validar que los datos no sean nulos
			if (cliente == null) {
				return "Error: La venta debe estar asociada a un cliente.";
			}
			if (fecha == null) {
				return "Error: La fecha de la venta no puede ser nula.";
			}

			// Crear el objeto Venta y guardarlo
			Venta venta = new Venta();
			venta.setCliente(cliente);
			venta.setFecha(fecha);
			ventaService.saveVenta(venta);

			return "Venta agregada correctamente.";
		} catch (Exception e) {
			return "Error al agregar la venta: " + e.getMessage();
		}
	}

	// Método para actualizar una venta
	public String updateVenta(int idVenta, Cliente nuevoCliente, LocalDateTime fecha) {
		try {
			// Obtener la venta actual desde la base de datos
			Venta ventaActual = ventaService.getVentaById(idVenta);

			if (ventaActual == null) {
				return "Error: La venta no existe.";
			}

			// Validar que el cliente no cambie
			if (ventaActual.getCliente().getIdCliente() != nuevoCliente.getIdCliente()) {
				return "Error: No se puede cambiar el cliente de una venta existente.";
			}

			// Validar que la fecha no sea nula
			if (fecha == null) {
				return "Error: La fecha de la venta no puede ser nula.";
			}

			// Actualizar solo la fecha
			ventaActual.setFecha(fecha);

			// Llamar al servicio para guardar la actualización
			ventaService.updateVenta(ventaActual);

			return "Venta actualizada correctamente.";
		} catch (Exception e) {
			return "Error al actualizar la venta: " + e.getMessage();
		}
	}

	// Método para eliminar una venta
	public String deleteVenta(int idVenta) {
		try {
			ventaService.deleteVenta(idVenta);
			return "Venta eliminada correctamente.";
		} catch (Exception e) {
			return "Error al eliminar la venta: " + e.getMessage();
		}
	}

	// Método para obtener una venta por su ID
	public Venta getVentaById(int idVenta) {
		return ventaService.getVentaById(idVenta);
	}

	// Método para obtener todas las ventas
	public List<Venta> getAllVentas() {
		return ventaService.getAllVentas();
	}

	// Método para obtener todas las ventas filtradas por cliente o fecha (dinámico)
	public List<Venta> getVentasFiltradas(String filtro) {
		try {
			return ventaService.getAllVentas().stream()
					.filter(venta -> venta.getCliente().getNombre().toLowerCase().contains(filtro.toLowerCase())
							|| venta.getFecha().toString().contains(filtro))
					.toList();
		} catch (Exception e) {
			throw new RuntimeException("Error al filtrar las ventas: " + e.getMessage());
		}
	}
	
	

}
