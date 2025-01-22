package com.gestorventasapp.view;

import com.gestorventasapp.controller.ClienteController;
import com.gestorventasapp.controller.VentaController;
import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.List;

public class VentaView extends ModuloBaseView {

	private VentaController ventaController;
	private ClienteController clienteController;

	public VentaView() {
	    super("Gestión de Ventas", new String[] { "ID Venta", "Cliente", "Fecha", "Total" });
	    ventaController = new VentaController();
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
		JButton btnAgregar = new JButton("Agregar Venta");
		JButton btnEliminar = new JButton("Eliminar Venta");
		JButton btnActualizar = new JButton("Actualizar Tabla");
		JButton btnVerDetalles = new JButton("Ver Detalles"); // Nuevo botón para abrir detalles

		// Aplicar estilos a los botones
		EstiloUI.aplicarEstiloBoton(btnAgregar);
		EstiloUI.aplicarEstiloBoton(btnEliminar);
		EstiloUI.aplicarEstiloBoton(btnActualizar);
		EstiloUI.aplicarEstiloBoton(btnVerDetalles);

		// Agregar acciones
		btnAgregar.addActionListener(e -> accionAgregarVenta());
		btnEliminar.addActionListener(e -> accionEliminarVenta());
		btnActualizar.addActionListener(e -> cargarDatosTabla());
		btnVerDetalles.addActionListener(e -> abrirDetalleVenta()); // Acción para ver detalles

		// Agregar los botones al panel
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
		panelBotones.add(btnVerDetalles);
	}

	private void cargarDatosTabla() {
		List<Venta> ventas = ventaController.getAllVentas();
		Object[][] datos = ventas.stream().map(
			    v -> new Object[] { 
			        v.getIdVenta(), 
			        v.getCliente().getNombre(), 
			        v.getFecha(), 
			        calcularTotalVenta(v) // Usar el total formateado
			    })
			    .toArray(Object[][]::new);
		actualizarTabla(datos);
	}

	private void accionAgregarVenta() {
		VentaFormulario formulario = new VentaFormulario(ventana, "Agregar Venta", ventaController, clienteController,
				null);
		formulario.setVisible(true);
		cargarDatosTabla();
	}

	private void accionEliminarVenta() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione una venta para eliminar.");
			return;
		}

		int idVenta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		String mensaje = ventaController.deleteVenta(idVenta);
		JOptionPane.showMessageDialog(ventana, mensaje);
		cargarDatosTabla();
	}

	private String calcularTotalVenta(Venta venta) {
	    double total = venta.getDetallesVenta().stream()
	            .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
	            .sum();
	    return String.format("%.2f", total); // Formatear con dos decimales
	}


	// Nuevo método para abrir la vista de detalles
	private void abrirDetalleVenta() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione una venta para ver los detalles.");
			return;
		}

		// Obtener la venta seleccionada de la tabla
		int idVenta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		Venta ventaSeleccionada = ventaController.getVentaById(idVenta);

		// Abrir la ventana de detalles
		DetalleVentaView detalleVentaView = new DetalleVentaView();
		detalleVentaView.setVentaSeleccionada(ventaSeleccionada); // Asignar la venta seleccionada
		detalleVentaView.mostrar();

	}
	
}
