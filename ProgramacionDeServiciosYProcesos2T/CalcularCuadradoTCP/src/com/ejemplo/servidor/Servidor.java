package com.ejemplo.servidor;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Servidor {
	private static final int PUERTO = 10103;

	public static void main(String[] args) {
		
		// Mostrar mensaje inicial del servidor
		JOptionPane.showMessageDialog(null, "Servidor iniciado. Escuchando en el puerto " + PUERTO, "Servidor Iniciado",
				JOptionPane.INFORMATION_MESSAGE);

		try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
			while (true) {
				// Mostrar que está esperando conexiones
				JOptionPane.showMessageDialog(null, "Esperando conexión...", "Esperando Cliente",
						JOptionPane.INFORMATION_MESSAGE);

				try (Socket clienteSocket = serverSocket.accept()) {
					// Cliente conectado
					String clienteInfo = "Cliente conectado desde: " + clienteSocket.getInetAddress();
					JOptionPane.showMessageDialog(null, clienteInfo, "Conexión Establecida",
							JOptionPane.INFORMATION_MESSAGE);

					// Streams para la comunicación
					BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
					PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);

					// Leer el número enviado por el cliente
					String mensaje = entrada.readLine(); // Leer como String
					if (mensaje != null) {
						int numero = Integer.parseInt(mensaje); // Convertir a entero

						// Calcular el cuadrado y enviar el resultado al cliente
						int cuadrado = numero * numero;
						String resultado = "Número recibido: " + numero + "\nSu cuadrado es: " + cuadrado;
						JOptionPane.showMessageDialog(null, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
						salida.println(cuadrado); // Enviar el cuadrado al cliente
					} else {
						JOptionPane.showMessageDialog(null, "El cliente no envió ningún dato.", "Error en Datos",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error en la conexión con el cliente: " + e.getMessage(),
							"Error de Conexión", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en el servidor: " + e.getMessage(), "Error del Servidor",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
