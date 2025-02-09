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
	            } else if (servidor.getClientes().containsKey(nombreUsuario)) {
	                escritor.println("Nombre en uso. Elija otro:");
	            } else {
	                synchronized (servidor) {
	                    servidor.registrarCliente(nombreUsuario, escritor);
	                    servidor.getGui().actualizarLog("Nuevo cliente conectado: " + nombreUsuario); // Solo muestra el nombre
	                }
	                break;
	            }
	        }

	        escritor.println("Bienvenido al chat, " + nombreUsuario + "! Puedes escribir mensajes.");
	        
	        // Escuchar mensajes del cliente
	        String mensaje;
	        while ((mensaje = lector.readLine()) != null) {
	            mensaje = mensaje.trim();
	            if (mensaje.isEmpty()) continue;

	            if (mensaje.equalsIgnoreCase("salir")) {
	                break;
	            }

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
	        servidor.getGui().actualizarLog("Cliente desconectado: " + nombreUsuario);
	    } finally {
	        try {
	            if (nombreUsuario != null) {
	                servidor.eliminarCliente(nombreUsuario);
	            }
	            if (socket != null && !socket.isClosed()) {
	                socket.close();
	            }
	        } catch (IOException e) {
	            servidor.getGui().actualizarLog("Error cerrando conexión con " + nombreUsuario + ": " + e.getMessage());
	        }
	    }
	}



}
