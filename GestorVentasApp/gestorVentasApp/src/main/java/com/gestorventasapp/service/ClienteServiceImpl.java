package com.gestorventasapp.service;

import java.util.List;
import com.gestorventasapp.dao.ClienteDAO;
import com.gestorventasapp.dao.ClienteDAOImpl;
import com.gestorventasapp.model.Cliente;

public class ClienteServiceImpl implements ClienteService {

	private final ClienteDAO clienteDAO = new ClienteDAOImpl();

	@Override
	public void saveCliente(Cliente cliente) {
		
		// Validar que el nombre no sea nulo o vacio
		if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacio");
		}

		// Validar que el email no sea nulo o vacio y tenga un formato valido
		if (cliente.getEmail() == null || !cliente.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("El email no tiene un formato valido");
		}

		// Validar que el telefono no sea nulo y tenga exactamente 9 dígitos
		if (cliente.getTelefono() == null || !cliente.getTelefono().matches("^[0-9]{9}$")) {
			throw new IllegalArgumentException("El telefono debe tener exactamente 9 digitos");
		}

		clienteDAO.save(cliente);
	}

	@Override
	public void updateCliente(Cliente cliente) {
		
		// Validar que el nombre no sea nulo o vacio
		if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacio");
		}

		// Validar que el email no sea nulo o vacio y tenga un formato valido
		if (cliente.getEmail() == null|| !cliente.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("El email no tiene un formato valido");
		}

		// Validar que el telefono no sea nulo y tenga exactamente 9 dígitos
		if (cliente.getTelefono() == null || !cliente.getTelefono().matches("^[0-9]{9}$")) {
			throw new IllegalArgumentException("El telefono debe tener exactamente 9 digitos");
		}

		clienteDAO.update(cliente);
	}

	@Override
	public void deleteCliente(int idCliente) {
		clienteDAO.delete(idCliente);
	}

	@Override
	public Cliente getClienteById(int idCliente) {
		return clienteDAO.findById(idCliente);
	}

	@Override
	public List<Cliente> getAllClientes() {
		return clienteDAO.findAll();
	}
}
