package com.chat.servidor;

import java.io.*;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
	
	private Socket clienteSocket;
	private BufferedReader lector;
	private PrintWriter escritor;
	private Servidor servidor; // Referencia al servidor principal

	public ManejadorCliente(Socket clienteSocket, Servidor servidor) {
		this.clienteSocket = clienteSocket;
		this.servidor = servidor; // Guardar referencia al servidor principal
		
		try {
			
			// Inicializar streams de entrada y salida
			lector = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
			escritor = new PrintWriter(clienteSocket.getOutputStream(), true);
		} catch (IOException e) {
			
			servidor.agregarLog("Error al inicializar streams: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		
		try {
			
			// Enviar mensaje de bienvenida al cliente
			escritor.println("Bienvenido al chat del servidor.");
			servidor.agregarLog("Nuevo cliente conectado desde: " + clienteSocket.getInetAddress());

			String mensaje;
			
			while ((mensaje = lector.readLine()) != null) {
			
				servidor.agregarLog("Mensaje recibido del cliente: " + mensaje);
				escritor.println("Servidor recibió: " + mensaje);
			}
		} catch (IOException e) {
			
			servidor.agregarLog("Error en la conexión con el cliente: " + e.getMessage());
		} finally {
			
			cerrarConexion();
		}
	}

	private void cerrarConexion() {
		
	    try {
	    	
	        if (lector != null) {
	        	
	            lector.close();
	        }
	        
	        if (escritor != null) {
	        	
	            escritor.close();
	        }
	        
	        if (clienteSocket != null && !clienteSocket.isClosed()) {
	        	
	            clienteSocket.close();
	        }
	        
	        servidor.agregarLog("Cliente desconectado.");
	    } catch (IOException e) {
	    	
	        servidor.agregarLog("Error al cerrar la conexión con el cliente: " + e.getMessage());
	    }
	}

}
