package com.ejemplo.servidor;

import java.io.*;
import java.net.*;

//ESTE EJERCICIO ES MONOHILO: Solo puede atender a un cliente a la vez

public class ServidorBasico {

	// Puerto donde el servidor estara escuchando
	static final int PUERTO = 12345; 
	
	public static void main(String[] args) {

		System.out.println("Iniciando servidor en el puerto " + PUERTO + "...");

		try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
			
			System.out.println("Servidor esperando conexiones...");

			// Aceptar la conexión del cliente
			Socket socketCliente = serverSocket.accept();
			System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress());

			// Obtener los streams de entrada y salida
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

			// Leer datos del cliente, procesar y enviar respuestas
			String mensajeCliente;
			while ((mensajeCliente = entrada.readLine()) != null) {
				System.out.println("Cliente dice: " + mensajeCliente);

				// Enviar respuesta al cliente
				salida.println("Servidor: Recibido -> " + mensajeCliente);

				// Salir si el cliente envía "salir"
				if (mensajeCliente.equalsIgnoreCase("salir")) {
					System.out.println("El cliente ha cerrado la conexión.");
					break;
				}
			}

			// Cerrar los streams
			entrada.close();
			salida.close();

			// Cerrar el socket del cliente
			socketCliente.close();

			System.out.println("Conexión cerrada con el cliente.");

		} catch (IOException e) {
			System.out.println("No se ha podido realizar la conexion: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
