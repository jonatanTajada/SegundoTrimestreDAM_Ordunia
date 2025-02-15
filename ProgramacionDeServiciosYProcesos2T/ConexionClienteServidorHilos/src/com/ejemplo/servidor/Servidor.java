package com.ejemplo.servidor;

import java.io.*;
import java.net.*;

public class Servidor {

	private static final int PUERTO = 10102;

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
			System.out.println("Servidor iniciado. Escuchando en el puerto " + PUERTO);

			while (true) {
				
				System.out.println("Esperando conexion...");
				Socket clienteSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress());

				// Crear  hilo para manejar al cliente
				Thread clienteHilo = new Thread(new ManejadorCliente(clienteSocket));
				clienteHilo.start();
			}
		} catch (IOException e) {
			System.err.println("Error en el servidor: " + e.getMessage());
		}
	}
}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------------------------

// Clase para manejar cada cliente en un hilo
class ManejadorCliente implements Runnable {

	private Socket clienteSocket;

	public ManejadorCliente(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}

	@Override
	public void run() {
		try (Socket cliente = clienteSocket; 
			 InputStream entrada = cliente.getInputStream();
			 BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
			 OutputStream salida = cliente.getOutputStream();
			 PrintWriter escritor = new PrintWriter(salida, true)) {

			// Leer el nombre del archivo que el cliente quiere enviar
			String nombreArchivo = lector.readLine();
			System.out.println("Recibiendo archivo: " + nombreArchivo);

			// Crear el archivo en el servidor
			File archivo = new File("archivos_recibidos/" + nombreArchivo);
			archivo.getParentFile().mkdirs(); // Crear directorios si no existen

			try (FileOutputStream fos = new FileOutputStream(archivo)) {
				// Recibir el contenido del archivo
				byte[] buffer = new byte[4096];
				int bytesLeidos;
				while ((bytesLeidos = entrada.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesLeidos);
				}
				System.out.println("Archivo recibido: " + nombreArchivo);
			}

			// Confirmar al cliente
			escritor.println("Archivo " + nombreArchivo + " recibido correctamente.");

		} catch (IOException e) {
			System.err.println("Error con el cliente: " + e.getMessage());
		}
	}
}
