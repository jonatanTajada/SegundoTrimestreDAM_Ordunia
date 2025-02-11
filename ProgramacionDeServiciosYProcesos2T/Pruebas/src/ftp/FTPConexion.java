package ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class FTPConexion {
    public static void main(String[] args) {
        // Datos del servidor FTP (modifica según tu configuración)
        String servidor = "127.0.0.1"; 
        int puerto = 21;
        String usuario = "test";  
        String contraseña = "1234";  

        // Crear un cliente FTP
        FTPClient clienteFTP = new FTPClient();

        try {
            // Conectar al servidor FTP
            clienteFTP.connect(servidor, puerto);
            System.out.println("✅ Conectado a " + servidor);

            // Iniciar sesión
            boolean login = clienteFTP.login(usuario, contraseña);
            if (login) {
                System.out.println("✅ Login exitoso!");

                // Listar archivos del directorio FTP
                String[] archivos = clienteFTP.listNames();
                if (archivos != null && archivos.length > 0) {
                    System.out.println("📂 Archivos en el servidor FTP:");
                    for (String archivo : archivos) {
                        System.out.println(" - " + archivo);
                    }
                } else {
                    System.out.println("⚠️ No hay archivos en el servidor.");
                }

                // Cerrar sesión
                clienteFTP.logout();
                System.out.println("🚪 Sesión cerrada correctamente.");
            } else {
                System.out.println("❌ Error en el login.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clienteFTP.isConnected()) {
                    clienteFTP.disconnect();
                    System.out.println("🔌 Desconectado del servidor.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
