package com.chat.servidor;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManejadorCliente implements Runnable {

    private Socket clienteSocket;
    private BufferedReader lector;
    private PrintWriter escritor;
    private Servidor servidor;
    private String nombreUsuario;

    public ManejadorCliente(Socket clienteSocket, Servidor servidor) {
        this.clienteSocket = clienteSocket;
        this.servidor = servidor;

        try {
            lector = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            escritor = new PrintWriter(clienteSocket.getOutputStream(), true);
        } catch (IOException e) {
            servidor.agregarLog(formatearLog("Error al inicializar streams: " + e.getMessage()));
        }
    }

    @Override
    public void run() {
        try {
            // Leer el nombre de usuario enviado por el cliente
            nombreUsuario = lector.readLine();
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                cerrarConexion();
                return;
            }

            // Agregar el usuario al servidor
            servidor.agregarUsuario(nombreUsuario, escritor, clienteSocket);

            servidor.agregarLog("Nuevo cliente conectado: " + nombreUsuario);

            // Manejar los mensajes enviados por el cliente
            String mensaje;
            while ((mensaje = lector.readLine()) != null) {
                if (mensaje.startsWith("Global:")) {
                    servidor.enviarMensajeGlobal(mensaje.replace("Global:", "").trim(), nombreUsuario);
                } else if (mensaje.startsWith("@")) {
                    int separador = mensaje.indexOf(":");
                    if (separador > 0) {
                        String destinatario = mensaje.substring(1, separador).trim();
                        String contenido = mensaje.substring(separador + 1).trim();
                        servidor.enviarMensajePrivado(contenido, nombreUsuario, destinatario);
                    }
                }
            }
        } catch (IOException e) {
            servidor.agregarLog("Error en la conexión con el cliente: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    private void cerrarConexion() {
        try {
            if (lector != null)
                lector.close();
            if (escritor != null)
                escritor.close();
            if (clienteSocket != null && !clienteSocket.isClosed())
                clienteSocket.close();

            if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
                servidor.eliminarUsuario(nombreUsuario);
            }

            servidor.agregarLog(formatearLog("Cliente desconectado: " + nombreUsuario));
        } catch (IOException e) {
            servidor.agregarLog(formatearLog("Error al cerrar la conexión con el cliente: " + e.getMessage()));
        }
    }

    // Formatear logs con timestamps
    private String formatearLog(String mensaje) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return "[" + timestamp + "] " + mensaje;
    }
}
