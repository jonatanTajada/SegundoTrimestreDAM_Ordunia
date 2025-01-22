package com.gestorventasapp.service;

import com.gestorventasapp.model.DetalleVenta;

import java.util.List;

public interface DetalleVentaService {
	
    void saveDetalleVenta(DetalleVenta detalleVenta); 
    
    void updateDetalleVenta(DetalleVenta detalleVenta); 
    
    void deleteDetalleVenta(int idDetalle); 
    
    DetalleVenta getDetalleVentaById(int idDetalle); 
    
    List<DetalleVenta> getAllDetalleVentas(); 
    
}
