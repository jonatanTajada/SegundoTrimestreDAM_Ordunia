package com.ejemplo.cliente;

import com.ejemplo.configuracion.cliente.VentanaConfiguracion;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Cliente {

	public static void main(String[] args) {
		// Mostrar la ventana de configuración
		VentanaConfiguracion configuracion = new VentanaConfiguracion();
		configuracion.setVisible(true);

		// Verificar si el usuario aceptó la configuración
		if (!configuracion.isAceptado()) {
			JOptionPane.showMessageDialog(null, "Configuración cancelada.", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// Obtener los valores ingresados por el usuario
		String servidorIP = configuracion.getIP();
		int puerto = Integer.parseInt(configuracion.getPuerto());
		String nombre = configuracion.getNombre();

		try (Socket socket = new Socket(servidorIP, puerto)) {
			// Mensaje de conexión exitosa
			JOptionPane.showMessageDialog(null, "Conectado al servidor en " + servidorIP + ":" + puerto,
					"Conexión Exitosa", JOptionPane.INFORMATION_MESSAGE);

			// Streams de entrada y salida
			PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Enviar el nombre del cliente al servidor
			salida.println(nombre);

			// Solicitar un número al usuario
			String numeroStr = solicitarNumero();
			if (numeroStr == null) {
				// El usuario canceló o no ingresó un número válido
				JOptionPane.showMessageDialog(null, "Operación cancelada. No se introdujo un número válido.",
						"Cancelado", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Convertir el número a entero y enviarlo al servidor
			int numero = Integer.parseInt(numeroStr.trim());
			salida.println(numero);

			// Leer la respuesta del servidor
			String respuesta = entrada.readLine();

			// Mostrar el resultado en una ventana
			JOptionPane.showMessageDialog(null, "El cuadrado de " + numero + " es: " + respuesta, "Resultado",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {
			// Mostrar error si no se puede conectar o enviar datos
			JOptionPane.showMessageDialog(null, "Error en el cliente: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Solicita un número al usuario mediante un cuadro de diálogo.
	 * 
	 * @return El número ingresado como cadena o null si el usuario cancela.
	 */
	private static String solicitarNumero() {
		while (true) {
			String numeroStr = JOptionPane.showInputDialog(null, "Introduce un número:", "Entrada de Datos",
					JOptionPane.QUESTION_MESSAGE);

			if (numeroStr == null || numeroStr.trim().isEmpty()) {
				// Si se cancela o no se introduce nada
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la operación?",
						"Confirmar Cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmacion == JOptionPane.YES_OPTION) {
					return null; // Usuario decidió cancelar
				}
			} else {
				try {
					// Validar si el número es válido
					Integer.parseInt(numeroStr.trim());
					return numeroStr;
				} catch (NumberFormatException e) {
					// Mostrar mensaje de error si no es un número válido
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
