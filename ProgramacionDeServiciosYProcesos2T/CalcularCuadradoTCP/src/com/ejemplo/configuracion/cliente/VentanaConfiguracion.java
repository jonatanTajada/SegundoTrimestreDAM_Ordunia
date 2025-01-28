package com.ejemplo.configuracion.cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConfiguracion extends JFrame {

	private JTextField campoIP;
	private JTextField campoPuerto;
	private JButton botonGuardar;
	private String direccionIP;
	private int puerto;

	public VentanaConfiguracion() {

		// Configuracion de la ventana
		setTitle("Configuracion del Servidor");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 2, 10, 10));

		// Componentes de la interfaz
		JLabel etiquetaIP = new JLabel("Direccion IP:");
		campoIP = new JTextField("127.0.0.1");

		JLabel etiquetaPuerto = new JLabel("Puerto:");
		campoPuerto = new JTextField("12345");

		botonGuardar = new JButton("Guardar");

		// Añadir componentes a la ventana
		add(etiquetaIP);
		add(campoIP);
		add(etiquetaPuerto);
		add(campoPuerto);
		add(new JLabel()); // Espacio vacio
		add(botonGuardar);

		// Accion del boton Guardar
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					direccionIP = campoIP.getText();
					puerto = Integer.parseInt(campoPuerto.getText());
					JOptionPane.showMessageDialog(null, "Configuracion guardada.");
					dispose(); // Cierra la ventana
					
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un puerto valido.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// Metodos para obtener la configuracion
	public String getDireccionIP() {
		return direccionIP;
	}

	public int getPuerto() {
		return puerto;
	}

	
}
