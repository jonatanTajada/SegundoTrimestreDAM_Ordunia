package com.gestorventasapp.view;

import com.gestorventasapp.controller.ProductoController;
import com.gestorventasapp.model.Producto;
import com.gestorventasapp.util.EstiloUI;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class ProductoView extends ModuloBaseView {

	private ProductoController productoController;
	private List<Producto> productosOriginales; // Lista para guardar los datos originales

	public ProductoView() {
		super("Gestión de Productos", new String[] { "ID", "Nombre", "Precio", "Stock" });
		productoController = new ProductoController();

		// Agregar la barra de menú
		ventana.setJMenuBar(EstiloUI.crearBarraMenu(ventana));

		// Configurar SwingWorker para cargar datos en segundo plano
		ejecutarSwingWorker(() -> {
			productosOriginales = productoController.getAllProducts(); // Cargar datos originales
			cargarDatosTabla(); // Actualizar tabla con los datos originales
		}, null);
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

	@Override
	protected void cargarDatosOriginales() {
		// Cargar datos originales a la tabla desde la lista productosOriginales
		Object[][] datos = productosOriginales.stream()
				.map(p -> new Object[] { p.getIdProducto(), p.getNombre(), String.format("%.2f", p.getPrecio()), // Formatear
																													// precio
																													// a
																													// dos
																													// decimales
						p.getStock() })
				.toArray(Object[][]::new);
		actualizarTabla(datos);
	}

	@Override
	protected Object[][] obtenerDatosFiltrados(String textoFiltro) {
		// Filtrar los productos según el texto ingresado en el buscador
		List<Producto> productosFiltrados = productosOriginales.stream()
				.filter(p -> p.getNombre().toLowerCase().contains(textoFiltro)
						|| String.valueOf(p.getIdProducto()).contains(textoFiltro))
				.collect(Collectors.toList());

		// Convertir la lista filtrada en una matriz para la tabla
		return productosFiltrados.stream()
				.map(p -> new Object[] { p.getIdProducto(), p.getNombre(), String.format("%.2f", p.getPrecio()), // Formatear
																													// precio
																													// a
																													// dos
																													// decimales
						p.getStock() })
				.toArray(Object[][]::new);
	}

	private void cargarDatosTabla() {
		cargarDatosOriginales(); // Llamar directamente al método para cargar los datos originales
	}

	private void accionAgregarProducto() {
		ProductoFormulario formulario = new ProductoFormulario(ventana, "Agregar Producto", productoController, () -> {
			productosOriginales = productoController.getAllProducts(); // Actualizar lista original
			cargarDatosTabla();
		});
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

		// Actualizar la tabla después de eliminar un producto
		productosOriginales = productoController.getAllProducts(); // Actualizar lista original
		cargarDatosTabla();
	}
}
