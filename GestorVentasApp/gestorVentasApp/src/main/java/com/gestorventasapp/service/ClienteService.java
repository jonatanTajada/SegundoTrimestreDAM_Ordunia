package com.gestorventasapp.service;

import java.util.List;

import com.gestorventasapp.model.Cliente;

public interface ClienteService {

	
	void saveCliente(Cliente cliente);
	
	void updateCliente(Cliente cliente);
	
	void deleteCliente(int idCliente);
	
	Cliente getClienteById(int idCliente);
	
	List<Cliente> getAllClientes();
	
}
