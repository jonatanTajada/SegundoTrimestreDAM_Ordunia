package com.gestorventasapp.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.gestorventasapp.util.EstiloUI;

public abstract class ModuloBaseView {
	protected JFrame ventana;
	protected JTable tabla;
	protected DefaultTableModel modeloTabla;
	protected JPanel panelBotones;
	protected JTextField campoBuscador; // Campo de texto para el buscador dinámico

	public ModuloBaseView(String titulo, String[] columnas) {
		// Configurar ventana principal del módulo
		ventana = new JFrame(titulo);
		ventana.setSize(800, 600);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setLayout(new BorderLayout());

		// Crear panel superior para incluir encabezado y buscador
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

		// Agregar encabezado
		JPanel encabezado = EstiloUI.crearEncabezado(titulo);
		panelSuperior.add(encabezado);

		// Agregar buscador dinámico
		campoBuscador = new JTextField();
		JPanel panelBuscador = EstiloUI.crearPanelBuscadorDinamico("Buscar:", campoBuscador);
		panelSuperior.add(panelBuscador);

		// Añadir el panel superior al norte de la ventana
		ventana.add(panelSuperior, BorderLayout.NORTH);

		// Configurar la tabla y su contenedor
		modeloTabla = new DefaultTableModel(columnas, 0);
		tabla = new JTable(modeloTabla);
		EstiloUI.configurarEstiloTabla(tabla); // Aplicar estilo personalizado a la tabla
		JScrollPane scrollPane = new JScrollPane(tabla);
		ventana.add(scrollPane, BorderLayout.CENTER);

		// Configurar panel de botones
		panelBotones = new JPanel(new FlowLayout());
		EstiloUI.aplicarEstiloPanelBotones(panelBotones); // Estilo uniforme para botones
		inicializarBotones();
		ventana.add(panelBotones, BorderLayout.SOUTH);

		// Mostrar la ventana por defecto
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);

		// Configurar funcionalidad del buscador dinámico
		configurarBuscadorDinamico();
	}

	/**
	 * Método abstracto para inicializar los botones. Debe ser implementado por las
	 * clases hijas.
	 */
	protected abstract void inicializarBotones();

	/**
	 * Actualiza la tabla con nuevos datos.
	 *
	 * @param datos Matriz de objetos con los datos para la tabla.
	 */
	protected void actualizarTabla(Object[][] datos) {
		modeloTabla.setRowCount(0);
		for (Object[] fila : datos) {
			modeloTabla.addRow(fila);
		}
	}

	/**
	 * Métodos para controlar la visibilidad de la ventana.
	 */
	public void mostrar() {
		ventana.setVisible(true);
	}

	public void ocultar() {
		ventana.setVisible(false);
	}

	/**
	 * Configura el comportamiento del buscador dinámico. Este método debe ser
	 * sobrescrito por las clases hijas si quieren personalizar el filtro.
	 */
	protected void configurarBuscadorDinamico() {
		campoBuscador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				filtrarTabla();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				filtrarTabla();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				filtrarTabla();
			}
		});
	}

	/**
	 * Filtra los datos de la tabla basado en el texto ingresado en el buscador.
	 * Este método es genérico y puede ser sobrescrito por las clases hijas para
	 * personalizar el comportamiento.
	 */
	protected void filtrarTabla() {
		String textoFiltro = campoBuscador.getText().toLowerCase();
		if (textoFiltro.isEmpty()) {
			// Restablecer la tabla si no hay texto en el buscador
			cargarDatosOriginales();
		} else {
			// Implementar lógica para filtrar los datos
			Object[][] datosFiltrados = obtenerDatosFiltrados(textoFiltro);
			actualizarTabla(datosFiltrados);
		}
	}

	/**
	 * Método para cargar los datos originales en la tabla. Las clases hijas deben
	 * implementarlo para restaurar los datos sin filtrar.
	 */
	protected abstract void cargarDatosOriginales();

	/**
	 * Método para obtener los datos filtrados. Las clases hijas deben implementarlo
	 * para definir la lógica del filtro.
	 *
	 * @param textoFiltro Texto ingresado en el buscador.
	 * @return Matriz de objetos con los datos filtrados.
	 */
	protected abstract Object[][] obtenerDatosFiltrados(String textoFiltro);
	
	protected void ejecutarSwingWorker(Runnable tareaEnSegundoPlano, Runnable tareaDespuesDeCargar) {
	    // Mostrar un indicador de carga en la parte superior de la ventana
	    JLabel lblCargando = new JLabel("Cargando datos...");
	    lblCargando.setHorizontalAlignment(SwingConstants.CENTER);
	    ventana.add(lblCargando, BorderLayout.CENTER);
	    ventana.revalidate();
	    ventana.repaint();

	    // Ejecutar la tarea en segundo plano con SwingWorker
	    new SwingWorker<Void, Void>() {
	        @Override
	        protected Void doInBackground() throws Exception {
	            tareaEnSegundoPlano.run();
	            return null;
	        }

	        @Override
	        protected void done() {
	            // Eliminar el indicador de carga
	            ventana.remove(lblCargando);
	            ventana.revalidate();
	            ventana.repaint();

	            // Ejecutar cualquier tarea después de cargar (si se proporciona)
	            if (tareaDespuesDeCargar != null) {
	                tareaDespuesDeCargar.run();
	            }
	        }
	    }.execute();
	}

}
