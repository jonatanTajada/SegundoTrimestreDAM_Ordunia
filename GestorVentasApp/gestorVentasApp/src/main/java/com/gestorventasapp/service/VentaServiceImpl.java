package com.gestorventasapp.service;

import com.gestorventasapp.dao.VentaDAO;
import com.gestorventasapp.dao.VentaDAOImpl;
import com.gestorventasapp.model.Venta;

import java.util.List;

public class VentaServiceImpl implements VentaService {

	private final VentaDAO ventaDAO = new VentaDAOImpl();

	@Override
	public void saveVenta(Venta venta) {
		// Validar que el cliente no sea nulo
		if (venta.getCliente() == null) {
			throw new IllegalArgumentException("La venta debe estar asociada a un cliente.");
		}

		// Validar que la fecha no sea nula
		if (venta.getFecha() == null) {
			throw new IllegalArgumentException("La fecha de la venta no puede ser nula.");
		}

		ventaDAO.save(venta);
	}

	@Override
	public void updateVenta(Venta venta) {
		// Validar que el cliente no sea nulo
		if (venta.getCliente() == null) {
			throw new IllegalArgumentException("La venta debe estar asociada a un cliente.");
		}

		// Validar que la fecha no sea nula
		if (venta.getFecha() == null) {
			throw new IllegalArgumentException("La fecha de la venta no puede ser nula.");
		}

		ventaDAO.update(venta);
	}

	@Override
	public void deleteVenta(int idVenta) {
		ventaDAO.delete(idVenta);
	}

	@Override
	public Venta getVentaById(int idVenta) {
		return ventaDAO.findById(idVenta);
	}

	@Override
	public List<Venta> getAllVentas() {
		return ventaDAO.findAll();
	}
}
