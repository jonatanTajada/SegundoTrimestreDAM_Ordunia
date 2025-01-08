package udp.cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteCuadradoUDP {
    public static void main(String[] args) {
        try {
            // Crear el socket del cliente
            DatagramSocket clienteSocket = new DatagramSocket();

            // Dirección IP del servidor y puerto
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 9877;

            // Solicitar número al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce un número para calcular su cuadrado: ");
            int numero = scanner.nextInt();
            String mensaje = String.valueOf(numero);
            byte[] bufferEnviar = mensaje.getBytes();

            // Crear paquete para enviar al servidor
            DatagramPacket paqueteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, direccionServidor, puertoServidor);
            clienteSocket.send(paqueteEnviar);
            System.out.println("Número enviado al servidor: " + numero);

            // Buffer para recibir la respuesta del servidor
            byte[] bufferRecibir = new byte[1024];

            // Crear paquete para recibir la respuesta
            DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);
            clienteSocket.receive(paqueteRecibido);

            // Mostrar la respuesta del servidor
            String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            System.out.println("Respuesta del servidor: " + respuesta);

            // Cerrar el socket
            clienteSocket.close();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
