package com.gestorventasapp.controller;

import java.util.List;

import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.service.ClienteService;
import com.gestorventasapp.service.ClienteServiceImpl;

/**
 * Controlador para manejar las operaciones relacionadas con clientes. Actua
 * como intermediario entre la vista y la capa de servicio.
 */

public class ClienteController {

	private final ClienteService clienteService;

	// Constructor que inicializa el controlador con una implementacion de ClienteService.
	public ClienteController() {
		this.clienteService = new ClienteServiceImpl();
	}

	// Agrega un nuevo cliente.
	public String addCliente(Cliente cliente) {
		try {
			clienteService.saveCliente(cliente);
			return "Cliente ingresado correctamente.";
		} catch (IllegalArgumentException e) {
			return "Error al ingresar cliente: " + e.getMessage();
		} catch (Exception e) {
			return "Error inesperado: " + e.getMessage();
		}
	}

	// Actualiza los datos de un cliente existente.
	public String updateCliente(Cliente cliente) {
		try {
			clienteService.updateCliente(cliente);
			return "Cliente actualizado correctamente.";
		} catch (IllegalArgumentException e) {
			return "Error al actualizar cliente: " + e.getMessage();
		} catch (Exception e) {
			return "Error inesperado: " + e.getMessage();
		}
	}

	// Elimina un cliente por su ID.
	public String deleteCliente(int idCliente) {
		try {
			clienteService.deleteCliente(idCliente);
			return "Cliente eliminado correctamente.";
		} catch (Exception e) {
			return "Error al eliminar cliente: " + e.getMessage();
		}
	}

	// Obtiene un cliente por su ID.
	public Cliente getClienteById(int idCliente) {
		return clienteService.getClienteById(idCliente);
	}

	// Obtiene una lista de todos los clientes.
	public List<Cliente> getAllClientes() {
		return clienteService.getAllClientes();
	}
}
