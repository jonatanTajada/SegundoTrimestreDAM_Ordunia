package com.ejemplo.configuracion.cliente;

import javax.swing.*;
import java.awt.*;

public class VentanaConfiguracion extends JFrame {

	private static final long serialVersionUID = 5972322377118135758L;

	private JTextField campoIP;
	private JTextField campoPuerto;
	private JTextField campoNombre;

	private boolean aceptado = false;

	// Constructor
	public VentanaConfiguracion() {
		super("Configuraciones de la comunicación");
		inicializarComponentes();
	}

	// Getters
	public boolean isAceptado() {
		return aceptado;
	}

	public String getIP() {
		return campoIP.getText();
	}

	public String getPuerto() {
		return campoPuerto.getText();
	}

	public String getNombre() {
		return campoNombre.getText();
	}

	// Método inicializar componentes
	private void inicializarComponentes() {

		// Configuración general de la ventana
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// Panel central para los campos
		JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 filas, 2 columnas
		panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

		// Etiquetas y campos
		panelCampos.add(new JLabel("IP del Servidor:", SwingConstants.RIGHT));
		campoIP = new JTextField("127.0.0.1");
		panelCampos.add(campoIP);

		panelCampos.add(new JLabel("Puerto de la conexión:", SwingConstants.RIGHT));
		campoPuerto = new JTextField("10103");
		panelCampos.add(campoPuerto);

		panelCampos.add(new JLabel("Escriba su nombre:", SwingConstants.RIGHT));
		campoNombre = new JTextField();
		panelCampos.add(campoNombre);

		// Añadir el panel de campos al centro
		add(panelCampos, BorderLayout.CENTER);

		// Panel inferior para los botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnOK = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");

		// Acción del botón OK
		btnOK.addActionListener(e -> {

			if (campoNombre.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "El campo 'Nombre' es obligatorio.", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			} else {
				aceptado = true; // Configuración aceptada
				JOptionPane.showMessageDialog(this,
						"Configuración aceptada:\n" + "IP: " + campoIP.getText() + "\n" + "Puerto: "
								+ campoPuerto.getText() + "\n" + "Nombre: " + campoNombre.getText(),
						"Configuración", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});

		// Acción del botón Cancel
		btnCancel.addActionListener(e -> {
			aceptado = false;
			JOptionPane.showMessageDialog(this, "Configuración cancelada.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		});

		panelBotones.add(btnOK);
		panelBotones.add(btnCancel);

		// Añadir el panel de botones al sur
		add(panelBotones, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {

		// Crear la ventana de configuración
		VentanaConfiguracion ventana = new VentanaConfiguracion();

		// Mostrar la ventana
		ventana.setVisible(true);
	}
}
