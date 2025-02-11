package lanzador;

import cliente.Cliente;

public class Lanzador {
    private static final int NUMERO_CLIENTES = 100;
    private static volatile boolean hayGanador = false; // Variable compartida para detener clientes si hay un ganador

    public static void main(String[] args) {
        System.out.println("Iniciando " + NUMERO_CLIENTES + " clientes...");

        Thread[] clientes = new Thread[NUMERO_CLIENTES]; // Array para almacenar los hilos de los clientes

        for (int i = 0; i < NUMERO_CLIENTES; i++) {
            if (hayGanador) break; // No crear más clientes si ya hay un ganador

            clientes[i] = new Thread(new Cliente()); // Crear un nuevo cliente en un hilo separado
            clientes[i].start(); // Iniciar el cliente

            try {
                Thread.sleep(10); // Pequeña pausa para evitar sobrecarga del servidor
            } catch (InterruptedException e) {
                System.err.println("Error al iniciar cliente: " + e.getMessage());
            }
        }

        // Esperamos a que todos los clientes terminen
        for (Thread cliente : clientes) {
            if (cliente != null) {
                try {
                    cliente.join(); // Esperar a que el hilo del cliente termine
                } catch (InterruptedException e) {
                    System.err.println("Error esperando finalización de cliente: " + e.getMessage());
                }
            }
        }

        System.out.println("Todos los clientes han finalizado.");
    }
}
