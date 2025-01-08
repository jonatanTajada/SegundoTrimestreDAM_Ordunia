package udp.servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
    	
        try {
        	
            // Crear el socket del servidor en el puerto 9876
            DatagramSocket servidorSocket = new DatagramSocket(9876);
            System.out.println("Servidor UDP escuchando en el puerto 9876...");

            // Crear buffer para recibir datos
            byte[] bufferRecibir = new byte[1024];
            byte[] bufferEnviar;

            while (true) {
                // Recibir datagrama del cliente
                DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);
                servidorSocket.receive(paqueteRecibido);

                // Obtener datos y dirección del cliente
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Mensaje recibido del cliente: " + mensaje);

                // Preparar la respuesta
                String respuesta = "Hola mundo desde el servidor";
                bufferEnviar = respuesta.getBytes();

                // Enviar respuesta al cliente
                DatagramPacket paqueteEnviar = new DatagramPacket(
                    bufferEnviar,
                    bufferEnviar.length,
                    paqueteRecibido.getAddress(),
                    paqueteRecibido.getPort()
                );
                servidorSocket.send(paqueteEnviar);

                System.out.println("Respuesta enviada al cliente.");
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
