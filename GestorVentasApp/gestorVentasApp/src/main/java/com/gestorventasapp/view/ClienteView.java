package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteView extends ModuloBaseView {

	private ClienteController clienteController;

	public ClienteView() {
	    super("Gestión de Clientes", new String[] { "ID", "Nombre", "Email", "Teléfono" });
	    clienteController = new ClienteController();

	    // Crear indicador de carga
	    JLabel lblCargando = new JLabel("Cargando datos...");
	    lblCargando.setHorizontalAlignment(SwingConstants.CENTER);
	    ventana.add(lblCargando, BorderLayout.NORTH); // Agregar al norte de la ventana

	    // Ejecutar SwingWorker para cargar datos
	    new SwingWorker<Void, Void>() {
	        @Override
	        protected Void doInBackground() throws Exception {
	            cargarDatosTabla(); // Cargar datos en segundo plano
	            return null;
	        }

	        @Override
	        protected void done() {
	            ventana.remove(lblCargando); // Eliminar el indicador de carga
	            ventana.revalidate(); // Actualizar la ventana
	            ventana.repaint(); // Refrescar la interfaz gráfica
	        }
	    }.execute();
	}



	@Override
	protected void inicializarBotones() {
	    JButton btnAgregar = new JButton("Agregar Cliente");
	    JButton btnEliminar = new JButton("Eliminar Cliente");
	    JButton btnActualizar = new JButton("Actualizar Tabla");

	    // Aplicar estilos a los botones
	    EstiloUI.aplicarEstiloBoton(btnAgregar);
	    EstiloUI.aplicarEstiloBoton(btnEliminar);
	    EstiloUI.aplicarEstiloBoton(btnActualizar);

	    // Agregar acciones
	    btnAgregar.addActionListener(e -> accionAgregarCliente());
	    btnEliminar.addActionListener(e -> accionEliminarCliente());
	    btnActualizar.addActionListener(e -> cargarDatosTabla());

	    // Agregar los botones al panel
	    panelBotones.add(btnAgregar);
	    panelBotones.add(btnEliminar);
	    panelBotones.add(btnActualizar);
	}


	private void cargarDatosTabla() {
	    List<Cliente> clientes = clienteController.getAllClientes();
	    Object[][] datos = clientes.stream()
	            .map(c -> new Object[] { c.getIdCliente(), c.getNombre(), c.getEmail(), c.getTelefono() })
	            .toArray(Object[][]::new);
	    actualizarTabla(datos);
	}


	private void accionAgregarCliente() {
	    ClienteFormularioAgregar formulario = new ClienteFormularioAgregar(
	        ventana,
	        "Agregar Cliente",
	        clienteController,
	        this::cargarDatosTabla // Callback para actualizar la tabla
	    );
	    formulario.setVisible(true);
	}


	private void accionEliminarCliente() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione un cliente para eliminar.");
			return;
		}

		int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		String mensaje = clienteController.deleteCliente(idCliente);
		JOptionPane.showMessageDialog(ventana, mensaje);
		cargarDatosTabla();
	}
}
