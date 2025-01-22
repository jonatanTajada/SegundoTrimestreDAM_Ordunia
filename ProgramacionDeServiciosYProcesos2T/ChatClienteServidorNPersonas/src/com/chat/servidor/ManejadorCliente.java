// Clase ManejadorCliente para gestionar la comunicacion entre un cliente y el servidor
package com.chat.servidor;

import java.io.*;
import java.net.Socket;

public class ManejadorCliente implements Runnable {

    private Socket clienteSocket; 
    private BufferedReader lector; 
    private PrintWriter escritor; 
    private Servidor servidor; 
    private String nombreUsuario; 

    // Constructor que inicializa la conexion del cliente con el servidor
    public ManejadorCliente(Socket clienteSocket, Servidor servidor) {
        this.clienteSocket = clienteSocket;
        this.servidor = servidor; 

        try {
            // Inicializar streams de entrada y salida
            lector = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            escritor = new PrintWriter(clienteSocket.getOutputStream(), true);
        } catch (IOException e) {
            servidor.agregarLog("Error al inicializar streams: " + e.getMessage());
        }
    }

    // Metodo que se ejecuta cuando el hilo inicia
    @Override
    public void run() {
        try {
            // Leer el nombre del usuario enviado por el cliente
            nombreUsuario = lector.readLine();
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                cerrarConexion();
                return;
            }

            // Agregar el usuario al servidor y notificar a los demas clientes
            servidor.agregarUsuario(nombreUsuario, escritor);

            // Enviar mensaje de bienvenida al cliente
            escritor.println("Bienvenido al chat del servidor.");
            servidor.agregarLog("Nuevo cliente conectado: " + nombreUsuario);

            String mensaje;

            // Escuchar mensajes del cliente
            while ((mensaje = lector.readLine()) != null) {
                if (mensaje.startsWith("Global:")) {
                    // Manejar mensajes globales
                    String contenido = mensaje.replace("Global:", "").trim();
                    servidor.enviarMensaje(contenido, nombreUsuario, "Global");
                } else if (mensaje.startsWith("@")) {
                    // Manejar mensajes privados
                    int separador = mensaje.indexOf("|");
                    if (separador > 0) {
                        String destinatario = mensaje.substring(1, separador).trim();
                        String contenido = mensaje.substring(separador + 1).trim();
                        servidor.enviarMensaje(contenido, nombreUsuario, destinatario);
                    }
                }
            }
        } catch (IOException e) {
            servidor.agregarLog("Error en la conexion con el cliente: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    // Cierra la conexion del cliente con el servidor
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

            // Eliminar al usuario del servidor y notificar a los demas clientes
            if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
                servidor.eliminarUsuario(nombreUsuario);
            }

            servidor.agregarLog("Cliente desconectado: " + nombreUsuario);
        } catch (IOException e) {
            servidor.agregarLog("Error al cerrar la conexion con el cliente: " + e.getMessage());
        }
    }
}
