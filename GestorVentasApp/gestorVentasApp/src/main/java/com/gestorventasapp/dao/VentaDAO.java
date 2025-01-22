package com.gestorventasapp.dao;

import java.util.List;

import com.gestorventasapp.model.Venta;

public interface VentaDAO {

	void save(Venta venta);
	
	void update(Venta venta);
	
	void delete(int idVenta);
	
	Venta findById(int idVenta);
	
	List<Venta> findAll();
	
	
	
	
	
	
	
}
