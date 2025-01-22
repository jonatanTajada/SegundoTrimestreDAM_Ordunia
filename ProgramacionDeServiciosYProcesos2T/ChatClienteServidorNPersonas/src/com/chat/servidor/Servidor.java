// Clase Servidor para gestionar la comunicacion entre multiples clientes en un chat
package com.chat.servidor;

import com.chat.utilities.EstiloUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int PUERTO = 10101; 

    private JTextArea logArea; 
    private ServerSocket serverSocket; 
    private boolean servidorActivo = false; 

    private final Map<String, PrintWriter> usuariosConectados = new ConcurrentHashMap<>(); // Lista de usuarios conectados

    // Constructor que inicializa la ventana del servidor
    public Servidor() {
        super("Servidor de Chat");
        inicializarVentana();
    }

    // Configura la ventana principal del servidor
    private void inicializarVentana() {
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Aplica el estilo personalizado a la ventana
        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_SERVIDOR);

        JLabel lblTitulo = new JLabel("Log del Servidor", SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FUENTE_TITULO);
        lblTitulo.setForeground(Color.DARK_GRAY);
        add(lblTitulo, BorderLayout.NORTH);

        // Area de texto para mostrar logs
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(EstiloUI.FUENTE_GENERAL);
        logArea.setForeground(Color.DARK_GRAY);
        logArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = EstiloUI.crearScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Inicia el servidor y acepta conexiones de clientes
    public void iniciarServidor() {
        try {
            servidorActivo = true;
            serverSocket = new ServerSocket(PUERTO);
            agregarLog("Servidor iniciado en el puerto: " + PUERTO);

            while (servidorActivo) {
                agregarLog("Esperando clientes...");
                Socket clienteSocket = serverSocket.accept();

                agregarLog("Cliente conectado desde: " + clienteSocket.getInetAddress());

                // Inicia un hilo para gestionar al cliente conectado
                Thread manejadorCliente = new Thread(new ManejadorCliente(clienteSocket, this));
                manejadorCliente.start();
            }
        } catch (IOException e) {
            agregarLog("Error en el servidor: " + e.getMessage());
        } finally {
            detenerServidor();
        }
    }

    // Agrega un usuario al servidor y notifica a los clientes
    public synchronized void agregarUsuario(String nombre, PrintWriter escritor) {
        usuariosConectados.put(nombre, escritor);
        agregarLog(nombre + " se ha conectado.");
        notificarUsuarios();
    }

    // Elimina un usuario del servidor y notifica a los clientes
    public synchronized void eliminarUsuario(String nombre) {
        usuariosConectados.remove(nombre);
        agregarLog(nombre + " se ha desconectado.");
        notificarUsuarios();
    }

    // Envia un mensaje al cliente o a todos los conectados
    public synchronized void enviarMensaje(String mensaje, String remitente, String destinatario) {
        if (destinatario == null || "Global".equals(destinatario)) {
            // Mensaje global
            usuariosConectados.forEach((nombre, escritor) -> {
                if (!nombre.equals(remitente)) {
                    escritor.println("Global:" + remitente + ": " + mensaje);
                }
            });
            agregarLog(remitente + " envio un mensaje global: " + mensaje);
        } else {
            // Mensaje privado
            PrintWriter escritor = usuariosConectados.get(destinatario);
            if (escritor != null) {
                escritor.println(remitente + "|" + mensaje);
                agregarLog(remitente + " envio un mensaje privado a " + destinatario + ": " + mensaje);
            } else {
                agregarLog("Usuario " + destinatario + " no encontrado.");
            }
        }
    }

    // Notifica a los clientes la lista actualizada de usuarios conectados
    private void notificarUsuarios() {
        String usuarios = String.join(",", usuariosConectados.keySet());
        usuariosConectados.forEach((nombre, escritor) -> escritor.println("Usuarios conectados:" + usuarios));
        agregarLog("Usuarios conectados actualizados: " + usuarios);
    }

    // Agrega un mensaje al log del servidor
    public void agregarLog(String mensaje) {
        SwingUtilities.invokeLater(() -> logArea.append(mensaje + "\n"));
    }

    // Detiene el servidor y cierra el socket
    private void detenerServidor() {
        try {
            servidorActivo = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                agregarLog("Servidor detenido.");
            }
        } catch (IOException e) {
            agregarLog("Error al detener el servidor: " + e.getMessage());
        }
    }

    // Metodo principal para ejecutar el servidor
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}
