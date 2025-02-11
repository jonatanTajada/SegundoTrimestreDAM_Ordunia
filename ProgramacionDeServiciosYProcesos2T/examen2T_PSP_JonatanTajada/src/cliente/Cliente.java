package cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Cliente implements Runnable {

	private static final String SERVIDOR = "localhost"; // Dirección del servidor
	private static final int PUERTO = 5555; // Puerto de conexión
	private static volatile boolean hayGanador = false; // Variable global compartida para detener clientes si hay un
														// ganador

	@Override
	public void run() {
		if (hayGanador)
			return; // Si hay un ganador, no ejecutar más clientes

		try (Socket socket = new Socket(SERVIDOR, PUERTO);
				Scanner entrada = new Scanner(socket.getInputStream());
				PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

			// Recibir mensaje de bienvenida del servidor
			if (entrada.hasNextLine()) {
				System.out.println("Servidor: " + entrada.nextLine());
			}

			Random random = new Random();
			while (!hayGanador) {
				int numero = random.nextInt(100) + 1; // Generar número aleatorio entre 1 y 100
				System.out.println("Enviando número: " + numero);
				salida.println(numero); // Enviar número al servidor

				if (entrada.hasNextLine()) {
					String respuesta = entrada.nextLine(); // Leer respuesta del servidor
					System.out.println("Servidor: " + respuesta);

					// Si el cliente gana, se notifica y se detienen los demás clientes
					if ("GANADOR".equals(respuesta)) {
						System.out.println("¡He ganado el sorteo!");
						hayGanador = true;
						break;
					}

					// Si el servidor envía "BYE", se cierra la conexión
					if ("BYE".equals(respuesta)) {
						System.out.println("Desconectando del servidor...");
						hayGanador = true;
						break;
					}

					if (hayGanador) {
						break; // Verificar la variable en cada iteración
					}
				}

				// Pequeña pausa para evitar sobrecarga y permitir respuesta rápida
				Thread.sleep(20);
			}
		} catch (IOException | InterruptedException e) {
			if (!hayGanador) {
				System.err.println("Error en el cliente: " + e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Cliente()).start(); // Iniciar un nuevo cliente en un hilo separado
	}
}
