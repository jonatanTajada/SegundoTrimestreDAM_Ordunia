package com.gestorventasapp.dao;

import com.gestorventasapp.model.DetalleVenta;

import java.util.List;

public interface DetalleVentaDAO {
	
	void save(DetalleVenta detalleVenta); 

	void update(DetalleVenta detalleVenta); 

	void delete(int idDetalle); 

	DetalleVenta findById(int idDetalle); 

	List<DetalleVenta> findAll(); 

}