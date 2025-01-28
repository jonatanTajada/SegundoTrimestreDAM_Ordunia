// Servidor actualizado con estilos
package com.chat.servidor;

import com.chat.utilities.Constantes;
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
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private boolean servidorActivo = false;

    private final Map<String, PrintWriter> usuariosConectados = new ConcurrentHashMap<>();

    public Servidor() {
        super(Constantes.TITULO_SERVIDOR);
        inicializarVentana();
    }

    private void inicializarVentana() {
        // Configurar la ventana principal
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Aplicar estilo general
        EstiloUI.aplicarEstiloVentana(this, EstiloUI.COLOR_FONDO_SERVIDOR);

        // Título
        JLabel lblTitulo = new JLabel(Constantes.TITULO_SERVIDOR, SwingConstants.CENTER);
        lblTitulo.setFont(EstiloUI.FUENTE_TITULO);
        lblTitulo.setForeground(EstiloUI.COLOR_TITULO_SERVIDOR);
        add(lblTitulo, BorderLayout.NORTH);

        // Área de logs
        logArea = EstiloUI.crearAreaTexto();
        JScrollPane scrollPane = EstiloUI.crearScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }

    public void iniciarServidor() {
        new Thread(() -> {
            try {
                servidorActivo = true;
                serverSocket = new ServerSocket(Constantes.SERVIDOR_PUERTO);
                agregarLog(Constantes.MENSAJE_SERVIDOR_INICIADO + Constantes.SERVIDOR_PUERTO);

                while (servidorActivo) {
                    agregarLog(Constantes.MENSAJE_ESPERANDO_CLIENTES);
                    Socket clienteSocket = serverSocket.accept();
                    new Thread(new ManejadorCliente(clienteSocket, this)).start();
                }
            } catch (IOException e) {
                agregarLog(Constantes.MENSAJE_ERROR_SERVIDOR + e.getMessage());
            } finally {
                detenerServidor();
            }
        }).start();
    }

    public synchronized void agregarUsuario(String nombre, PrintWriter escritor, Socket clienteSocket) {
        if (usuariosConectados.containsKey(nombre)) {
            // Si el nombre ya existe, notificar al cliente y cerrar la conexión
            try {
                escritor.println(Constantes.ERROR_NOMBRE_DUPLICADO);
                clienteSocket.close();
                agregarLog("Intento de conexión con nombre duplicado: " + nombre);
            } catch (IOException e) {
                agregarLog("Error al cerrar la conexión de usuario duplicado: " + e.getMessage());
            }
        } else {
            // Agregar usuario al mapa y notificar
            usuariosConectados.put(nombre, escritor);
            agregarLog("Usuario conectado: " + nombre);
            notificarUsuarios();
        }
    }


    public synchronized void enviarMensajeGlobal(String mensaje, String remitente) {
        usuariosConectados.forEach((nombre, escritor) -> {
            escritor.println(remitente + Constantes.PREFIJO_ENVIO_GLOBAL + mensaje);
        });
        agregarLog(remitente + " envió un mensaje global: " + mensaje);
    }

    public synchronized void enviarMensajePrivado(String mensaje, String remitente, String destinatario) {
        PrintWriter escritorDestinatario = usuariosConectados.get(destinatario);
        if (escritorDestinatario != null) {
            // Enviar el mensaje al destinatario
            escritorDestinatario.println(remitente + Constantes.PREFIJO_PRIVADO + mensaje);
            agregarLog("Mensaje privado enviado de " + remitente + " a " + destinatario + ": " + mensaje);
        } else {
            // Notificar al remitente si el destinatario no está conectado
            PrintWriter escritorRemitente = usuariosConectados.get(remitente);
            if (escritorRemitente != null) {
                escritorRemitente.println("Servidor: " + destinatario + " no está conectado.");
            }
        }
    }

    public synchronized void eliminarUsuario(String nombre) {
        usuariosConectados.remove(nombre);
        agregarLog(nombre + Constantes.MENSAJE_USUARIO_DESCONECTADO);
        notificarUsuarios();
    }

    private void notificarUsuarios() {
        String usuarios = String.join(",", usuariosConectados.keySet());
        usuariosConectados.forEach((nombre, escritor) -> escritor.println(Constantes.PREFIJO_USUARIOS_CONECTADOS + usuarios));
    }


    public void agregarLog(String mensaje) {
        SwingUtilities.invokeLater(() -> logArea.append(mensaje + "\n"));
    }

    private void detenerServidor() {
        try {
            servidorActivo = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                agregarLog(Constantes.MENSAJE_SERVIDOR_DETENIDO);
            }
        } catch (IOException e) {
            agregarLog(Constantes.MENSAJE_ERROR_SERVIDOR_DETENER + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Servidor().iniciarServidor();
    }
}
