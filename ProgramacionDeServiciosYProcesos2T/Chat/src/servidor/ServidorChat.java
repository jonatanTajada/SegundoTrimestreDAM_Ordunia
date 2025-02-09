package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServidorChat {
    private static final int PUERTO = 5003;
    private Map<String, PrintWriter> clientes = new HashMap<>();
    private ServerSocket serverSocket;
    private ServidorChatGUI gui;
    private boolean servidorActivo = true;

    public ServidorChat(ServidorChatGUI gui) {
        this.gui = gui;
    }

    public void iniciarServidor() {
        try {
            serverSocket = new ServerSocket(PUERTO);
            gui.actualizarLog("Servidor iniciado en el puerto " + PUERTO);

            while (servidorActivo) {
                Socket socket = serverSocket.accept();
            

                ManejadorCliente manejador = new ManejadorCliente(socket, this);
                new Thread(manejador).start();
            }
        } catch (IOException e) {
            if (servidorActivo) {
                gui.actualizarLog("Error en el servidor: " + e.getMessage());
            }
        }
    }

    public synchronized void registrarCliente(String nombre, PrintWriter escritor) {
        if (!clientes.containsKey(nombre)) { // Evita registrar dos veces
            clientes.put(nombre, escritor);
            actualizarListaUsuarios(); // Solo actualizar usuarios, no difundir mensaje aquí
        }
    }




    public synchronized void eliminarCliente(String nombre) {
        if (clientes.containsKey(nombre)) { // Evita eliminar dos veces
            clientes.remove(nombre);
            actualizarListaUsuarios();
            difundirMensaje("Servidor: " + nombre + " ha salido del chat."); // Solo una vez
        }
    }


    public synchronized void difundirMensaje(String mensaje) {
        if (mensaje.startsWith("[Privado]")) {
            return; // No enviar mensajes privados a todos
        }
        for (PrintWriter escritor : clientes.values()) {
            escritor.println(mensaje);
        }
        gui.actualizarLog("Mensaje público: " + mensaje);
    }

    public synchronized void enviarMensajePrivado(String destinatario, String mensaje) {
        PrintWriter escritor = clientes.get(destinatario);
        if (escritor != null) {
            escritor.println("[Privado] " + mensaje);
        } else {
            gui.actualizarLog("Intento de enviar mensaje privado a usuario no conectado: " + destinatario);
        }
    }

    public synchronized void enviarListaUsuarios() {
        StringBuilder lista = new StringBuilder("[Usuarios]");
        for (String usuario : clientes.keySet()) {
            lista.append(usuario).append(",");
        }
        String listaUsuarios = lista.toString();

        for (PrintWriter escritor : clientes.values()) {
            escritor.println(listaUsuarios);
        }
    }

    public void detenerServidor() {
        servidorActivo = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            gui.actualizarLog("Error al detener el servidor: " + e.getMessage());
        }
    }
    
    public synchronized Map<String, PrintWriter> getClientes() {
        return clientes;
    }

    public ServidorChatGUI getGui() {
        return gui;
    }
    
    public synchronized void actualizarListaUsuarios() {
        if (gui != null) { 
            gui.actualizarUsuarios(clientes.keySet()); // Ahora sí actualiza la lista en la GUI del servidor
        }
        enviarListaUsuarios(); // Enviar lista a los clientes
    }


}
