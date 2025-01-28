package com.ejemplo.servidor;

import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {

		int puerto = 12345; 

		try (ServerSocket servidor = new ServerSocket(puerto)) {
			System.out.println("Servidor iniciado en el puerto " + puerto);

			while (true) {
				System.out.println("Esperando conexiones...");

				// Espera a que un cliente se conecte
				try (Socket socketCliente = servidor.accept();
					 BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
					 PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true)) {

					System.out.println("Cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());

					// Leer el numero que envia el cliente
					String numeroRecibido = entrada.readLine();
					int numero = Integer.parseInt(numeroRecibido);
					System.out.println("Numero recibido: " + numero);

					// Calcular el cuadrado del numero
					int cuadrado = numero * numero;
					System.out.println("Enviando cuadrado al cliente: " + cuadrado);

					// Enviar el resultado al cliente
					salida.println(cuadrado);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
}
