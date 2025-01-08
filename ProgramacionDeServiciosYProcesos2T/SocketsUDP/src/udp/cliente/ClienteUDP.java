package udp.cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            // Crear el socket del cliente
            DatagramSocket clienteSocket = new DatagramSocket();

            // Dirección IP del servidor y puerto
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 9876;

            // Mensaje a enviar al servidor
            String mensaje = "Hola mundo desde el cliente";
            byte[] bufferEnviar = mensaje.getBytes();

            // Crear paquete para enviar al servidor
            DatagramPacket paqueteEnviar = new DatagramPacket(bufferEnviar, bufferEnviar.length, direccionServidor, puertoServidor);
            clienteSocket.send(paqueteEnviar);
            System.out.println("Mensaje enviado al servidor: " + mensaje);

            // Buffer para recibir la respuesta del servidor
            byte[] bufferRecibir = new byte[1024];

            // Crear paquete para recibir la respuesta
            DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);
            clienteSocket.receive(paqueteRecibido);

            // Mostrar la respuesta del servidor
            String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            System.out.println("Respuesta recibida del servidor: " + respuesta);

            // Cerrar el socket
            clienteSocket.close();
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
