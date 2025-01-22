package com.gestorventasapp.view;

import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.Producto;
import com.gestorventasapp.util.EstiloUI;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.List;

public class ProductoView extends ModuloBaseView {

	private ProductoController productoController;

	public ProductoView() {
	    super("Gestión de Productos", new String[] { "ID", "Nombre", "Precio", "Stock" });
	    productoController = new ProductoController();

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
	    JButton btnAgregar = new JButton("Agregar Producto");
	    JButton btnEliminar = new JButton("Eliminar Producto");
	    JButton btnActualizar = new JButton("Actualizar Tabla");

	    // Aplicar estilos a los botones
	    EstiloUI.aplicarEstiloBoton(btnAgregar);
	    EstiloUI.aplicarEstiloBoton(btnEliminar);
	    EstiloUI.aplicarEstiloBoton(btnActualizar);

	    // Agregar acciones
	    btnAgregar.addActionListener(e -> accionAgregarProducto());
	    btnEliminar.addActionListener(e -> accionEliminarProducto());
	    btnActualizar.addActionListener(e -> cargarDatosTabla());

	    // Agregar los botones al panel
	    panelBotones.add(btnAgregar);
	    panelBotones.add(btnEliminar);
	    panelBotones.add(btnActualizar);
	}


	private void cargarDatosTabla() {
	    List<Producto> productos = productoController.getAllProducts();
	    Object[][] datos = productos.stream()
	            .map(p -> new Object[] { 
	                p.getIdProducto(), 
	                p.getNombre(), 
	                String.format("%.2f", p.getPrecio()), // Formatear precio a dos decimales
	                p.getStock() 
	            })
	            .toArray(Object[][]::new);
	    actualizarTabla(datos);
	}


	private void accionAgregarProducto() {
		ProductoFormulario formulario = new ProductoFormulario(ventana, "Agregar Producto", productoController,
				this::cargarDatosTabla);
		formulario.setVisible(true);
	}

	private void accionEliminarProducto() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(ventana, "Seleccione un producto para eliminar.");
			return;
		}

		int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
		String mensaje = productoController.deleteProduct(idProducto);
		JOptionPane.showMessageDialog(ventana, mensaje);
		cargarDatosTabla();
	}
}
