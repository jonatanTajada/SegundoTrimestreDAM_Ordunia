package com.gestorventasapp.service;

import com.gestorventasapp.model.Venta;

import java.util.List;

public interface VentaService {
	
	void saveVenta(Venta venta); 

	void updateVenta(Venta venta); 

	void deleteVenta(int idVenta); 

	Venta getVentaById(int idVenta); 

	List<Venta> getAllVentas(); 
}
