package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Servidor {
	private static final int PUERTO = 5555;
	private static final int NUMERO_GANADOR;
	private static volatile boolean hayGanador = false;

	static {
		// Se genera un número aleatorio entre 1 y 100 al iniciar el servidor
		Random random = new Random();
		NUMERO_GANADOR = random.nextInt(100) + 1;
		System.out.println("Número ganador del sorteo: " + NUMERO_GANADOR);
	}

	public static void main(String[] args) {
		try (ServerSocket servidor = new ServerSocket(PUERTO)) {
			System.out.println("Servidor iniciado en el puerto " + PUERTO);

			while (!hayGanador) {
				// Espera conexiones de clientes
				Socket socketCliente = servidor.accept();
				System.out.println("Nuevo cliente conectado desde: " + socketCliente.getInetAddress());

				// Inicia un nuevo hilo para manejar al cliente
				new Thread(new ManejadorCliente(socketCliente, servidor)).start();
			}
		} catch (IOException e) {
			// Captura errores si el servidor no se cerró debido a un ganador
			if (!hayGanador) {
				System.err.println("Error en el servidor: " + e.getMessage());
			}
		}
	}

	// Clase interna para manejar la conexión con cada cliente
	private static class ManejadorCliente implements Runnable {
		private final Socket socket;
		private final ServerSocket servidor;

		public ManejadorCliente(Socket socket, ServerSocket servidor) {
			this.socket = socket;
			this.servidor = servidor;
		}

		@Override
		public void run() {
			try (Scanner entrada = new Scanner(socket.getInputStream());
					PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {
				// Enviar mensaje de bienvenida al cliente
				salida.println("Bienvenido al servidor");

				while (!hayGanador) {
					if (entrada.hasNextInt()) {
						int numeroCliente = entrada.nextInt();
						System.out.println("Cliente " + socket.getInetAddress() + " envió: " + numeroCliente);

						// Verifica si el cliente acertó el número
						if (numeroCliente == NUMERO_GANADOR) {
							salida.println("GANADOR");
							salida.println("BYE");
							hayGanador = true;
							System.out.println(
									"¡Tenemos un ganador! Cliente " + socket.getInetAddress() + " acertó el número.");
							servidor.close(); // Cierra el servidor al encontrar un ganador
							break;
						} else {
							salida.println("NO GANADOR");
						}
					} else {
						break;
					}
				}
			} catch (IOException e) {
				// Manejo de errores si no hay ganador aún
				if (!hayGanador) {
					System.err.println("Error con el cliente: " + e.getMessage());
				}
			} finally {
				try {
					socket.close(); // Cierra la conexión del cliente
					System.out.println("Cliente desconectado: " + socket.getInetAddress());
				} catch (IOException e) {
					System.err.println("Error al cerrar socket: " + e.getMessage());
				}
			}
		}
	}
}