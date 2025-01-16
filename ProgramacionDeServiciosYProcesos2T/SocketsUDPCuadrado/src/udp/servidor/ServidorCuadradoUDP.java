package udp.servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorCuadradoUDP {
	
    public static void main(String[] args) {
    	
        try {
            // Crear el socket del servidor en el puerto 9877
            DatagramSocket servidorSocket = new DatagramSocket(9877);
            System.out.println("Servidor UDP (Cuadrado) escuchando en el puerto 9877...");

            // Crear buffer para recibir datos
            byte[] bufferRecibir = new byte[1024];
            byte[] bufferEnviar;

            while (true) {
                // Recibir datagrama del cliente
                DatagramPacket paqueteRecibido = new DatagramPacket(bufferRecibir, bufferRecibir.length);
                servidorSocket.receive(paqueteRecibido);

                // Obtener el número enviado por el cliente
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                int numero = Integer.parseInt(mensaje.trim());
                System.out.println("Número recibido del cliente: " + numero);

                // Calcular el cuadrado del número
                int cuadrado = numero * numero;
                System.out.println("Cuadrado calculado: " + cuadrado);

                // Preparar el mensaje de respuesta
                String respuesta = "El cuadrado de " + numero + " es " + cuadrado;
                bufferEnviar = respuesta.getBytes();

                // Enviar la respuesta al cliente
                DatagramPacket paqueteEnviar = new DatagramPacket(
                    bufferEnviar,
                    bufferEnviar.length,
                    paqueteRecibido.getAddress(),
                    paqueteRecibido.getPort()
                );
                servidorSocket.send(paqueteEnviar);

                System.out.println("Resultado enviado al cliente.");
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
