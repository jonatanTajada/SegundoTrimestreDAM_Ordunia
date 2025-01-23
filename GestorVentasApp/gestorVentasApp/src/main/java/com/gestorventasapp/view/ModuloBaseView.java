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
	protected JMenuBar barraMenu; // Barra de menú

	public ModuloBaseView(String titulo, String[] columnas) {
		// Configurar ventana principal del módulo
		ventana = new JFrame(titulo);
		ventana.setSize(800, 600);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Usar GridBagLayout para permitir ajuste dinámico
		JPanel contenedor = new JPanel(new GridBagLayout());
		ventana.setContentPane(contenedor);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes

		// Agregar barra de menú
		barraMenu = EstiloUI.crearBarraMenu(ventana);
		ventana.setJMenuBar(barraMenu);

		// Encabezado
		JPanel encabezado = EstiloUI.crearEncabezado(titulo);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.05; // Ocupa un 5% del espacio vertical
		gbc.fill = GridBagConstraints.BOTH;
		contenedor.add(encabezado, gbc);

		// Buscador dinámico
		campoBuscador = new JTextField();
		JPanel panelBuscador = EstiloUI.crearPanelBuscadorDinamico("Buscar:", campoBuscador);
		gbc.gridy = 1;
		gbc.weighty = 0.05; // Ocupa un 5% del espacio vertical
		contenedor.add(panelBuscador, gbc);

		// Tabla
		modeloTabla = new DefaultTableModel(columnas, 0);
		tabla = new JTable(modeloTabla);
		EstiloUI.configurarEstiloTabla(tabla);
		JScrollPane scrollPane = new JScrollPane(tabla);
		gbc.gridy = 3; // Cambiar posición para dejar espacio al indicador de carga
		gbc.weighty = 0.7; // Ocupa un 70% del espacio vertical
		contenedor.add(scrollPane, gbc);

		// Panel de botones
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		EstiloUI.aplicarEstiloPanelBotones(panelBotones);
		inicializarBotones();
		gbc.gridy = 4;
		gbc.weighty = 0.15; // Ocupa un 15% del espacio vertical
		contenedor.add(panelBotones, gbc);

		// Configurar la ventana para que los componentes se ajusten al redimensionar
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

	/**
	 * Ejecuta una tarea en segundo plano utilizando SwingWorker.
	 * 
	 * @param tareaEnSegundoPlano  Tarea que se ejecutará en segundo plano.
	 * @param tareaDespuesDeCargar Tarea que se ejecutará después de completar la
	 *                             carga.
	 */
	protected void ejecutarSwingWorker(Runnable tareaEnSegundoPlano, Runnable tareaDespuesDeCargar) {
		// Crear un JPanel como contenedor para el indicador de carga y barra de
		// progreso
		JPanel panelCargando = new JPanel();
		panelCargando.setLayout(new BoxLayout(panelCargando, BoxLayout.Y_AXIS));
		panelCargando.setOpaque(false);

		// Crear un JLabel para el texto "Cargando datos..."
		JLabel lblCargando = new JLabel("Cargando datos...");
		lblCargando.setFont(new Font("Poppins", Font.PLAIN, 14));
		lblCargando.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Crear una barra de progreso indeterminada
		JProgressBar barraProgreso = new JProgressBar();
		barraProgreso.setIndeterminate(true);
		barraProgreso.setPreferredSize(new Dimension(200, 15));
		barraProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Añadir el texto y la barra de progreso al panel
		panelCargando.add(lblCargando);
		panelCargando.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado entre componentes
		panelCargando.add(barraProgreso);

		// Configurar constraints para el indicador de carga
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2; // Justo entre el buscador y la tabla
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0; // No ocupar espacio vertical extra
		gbc.anchor = GridBagConstraints.CENTER;

		// Añadir el panel de carga al contenedor
		Container contenedor = ventana.getContentPane();
		contenedor.add(panelCargando, gbc);
		ventana.revalidate();
		ventana.repaint();

		// Ejecutar la tarea en segundo plano
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				tareaEnSegundoPlano.run();
				return null;
			}

			@Override
			protected void done() {
				// Eliminar el panel de carga
				contenedor.remove(panelCargando);
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
