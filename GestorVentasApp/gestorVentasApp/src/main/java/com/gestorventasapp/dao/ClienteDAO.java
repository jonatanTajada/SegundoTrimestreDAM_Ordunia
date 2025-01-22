package com.gestorventasapp.dao;

import java.util.List;

import com.gestorventasapp.model.Cliente;

public interface ClienteDAO {
	
	
	void save(Cliente cliente);
	
	void update(Cliente cliente);
	
	void delete(int idCliente);
	
	Cliente findById(int idCliente);
	
	List<Cliente> findAll();

}
