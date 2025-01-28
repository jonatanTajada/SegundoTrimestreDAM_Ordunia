package com.ejemplo.cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.ejemplo.configuracion.cliente.VentanaConfiguracion;

public class Cliente {
	
	public static void main(String[] args) {
		
		// Mostrar la ventana de configuracion
		VentanaConfiguracion configuracion = new VentanaConfiguracion();
		configuracion.setVisible(true);

		// Esperar a que el usuario cierre la ventana de configuracion
		while (configuracion.isVisible()) {
			try {
				Thread.sleep(100); // Espera activa mientras se configura
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String direccionIp = configuracion.getDireccionIP();
		int puerto = configuracion.getPuerto();

		// Validar que el usuario haya introducido valores validos
		if (direccionIp == null || direccionIp.isEmpty() || puerto <= 0) {
			System.out.println("La configuracion no es valida. Terminando el programa.");
			return;
		}

		// Iniciar la conexion con el servidor
		try (Socket socket = new Socket(direccionIp, puerto);
				BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
				Scanner scanner = new Scanner(System.in)) {

			System.out.println("Conectado al servidor en " + direccionIp + ":" + puerto);

			// Enviar numero al servidor
			System.out.print("Introduce un numero para calcular su cuadrado: ");
			int numero = scanner.nextInt();
			salida.println(numero);

			// Recibir resultado del servidor
			String resultado = entrada.readLine();
			System.out.println("El cuadrado del numero es: " + resultado);

		} catch (IOException e) {
			System.out.println("Error al conectar con el servidor: " + e.getMessage());
		}
	}
}
