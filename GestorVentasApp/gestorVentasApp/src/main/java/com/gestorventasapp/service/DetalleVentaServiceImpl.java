package com.gestorventasapp.service;

import com.gestorventasapp.dao.DetalleVentaDAO;
import com.gestorventasapp.dao.DetalleVentaDAOImpl;
import com.gestorventasapp.model.DetalleVenta;

import java.util.List;

public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();

    @Override
    public void saveDetalleVenta(DetalleVenta detalleVenta) {
        // Validar que la venta no sea nula
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("El detalle debe estar asociado a una venta.");
        }

        // Validar que el producto no sea nulo
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El detalle debe estar asociado a un producto.");
        }

        // Validar que la cantidad sea mayor que 0
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0.");
        }

        // Calcular el subtotal si es necesario
        detalleVenta.calcularSubtotal();

        // Guardar el detalle
        detalleVentaDAO.save(detalleVenta);
    }

    @Override
    public void updateDetalleVenta(DetalleVenta detalleVenta) {
        // Validar que la venta no sea nula
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("El detalle debe estar asociado a una venta.");
        }

        // Validar que el producto no sea nulo
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El detalle debe estar asociado a un producto.");
        }

        // Validar que la cantidad sea mayor que 0
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0.");
        }

        detalleVenta.calcularSubtotal();

        detalleVentaDAO.update(detalleVenta);
    }

    
    @Override
    public void deleteDetalleVenta(int idDetalle) {
        detalleVentaDAO.delete(idDetalle);
    }

    
    @Override
    public DetalleVenta getDetalleVentaById(int idDetalle) {
        return detalleVentaDAO.findById(idDetalle);
    }

    
    @Override
    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleVentaDAO.findAll();
    }
}
