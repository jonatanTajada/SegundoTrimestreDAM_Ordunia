package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
	
    private Socket socket;
    private ServidorChat servidor;
    private PrintWriter escritor;
    private BufferedReader lector;
    private String nombreUsuario;



    public ManejadorCliente(Socket socket, ServidorChat servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            escritor = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre de usuario
            escritor.println("Ingrese su nombre de usuario:");
            while (true) {
                nombreUsuario = lector.readLine();
                if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                    escritor.println("Nombre no válido. Intente de nuevo:");
                } else if (servidor.getClientes().containsKey(nombreUsuario)) { // Ahora usamos el getter
                    escritor.println("Nombre en uso. Elija otro:");
                } else {
                    synchronized (servidor) {
                        servidor.registrarCliente(nombreUsuario, escritor);
                    }
                    break;
                }
            }


            escritor.println("Bienvenido al chat, " + nombreUsuario + "! Puedes escribir mensajes.");
            servidor.difundirMensaje("Servidor: " + nombreUsuario + " se ha unido al chat.");

            // Escuchar mensajes del cliente
            String mensaje;
            while ((mensaje = lector.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }

                // Manejar mensaje privado o público
                if (mensaje.startsWith("@")) {
                    String[] partes = mensaje.split(" ", 2);
                    if (partes.length == 2) {
                        String destinatario = partes[0].substring(1);
                        servidor.enviarMensajePrivado(destinatario, nombreUsuario + ": " + partes[1]);
                    } else {
                        escritor.println("Formato incorrecto. Usa: @usuario mensaje");
                    }
                } else {
                    servidor.difundirMensaje(nombreUsuario + ": " + mensaje);
                } 
            }
        } catch (IOException e) {
        	servidor.getGui().actualizarLog("Error cerrando conexión con " + nombreUsuario);

        } finally {
            // Cerrar conexión y eliminar usuario
            try {
                if (nombreUsuario != null) {
                    servidor.eliminarCliente(nombreUsuario);
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            	servidor.getGui().actualizarLog("Error cerrando conexión con " + nombreUsuario);


            }
        }
    }
}
