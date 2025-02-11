package cifrado;


import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class CifradoRSA {
	
	
	private KeyPair keyPair;
	

	/**
	 * Constructor: Genera un par de claves RSA (pública y privada)
	 */
	public CifradoRSA() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048); // Tamaño de clave segura
			this.keyPair = keyGen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Método para cifrar un texto con la clave pública
	 */
	public String encriptar(String mensaje) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			byte[] mensajeCifrado = cipher.doFinal(mensaje.getBytes());
			return Base64.getEncoder().encodeToString(mensajeCifrado);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Método para descifrar un texto con la clave privada
	 */
	public String desencriptar(String mensajeCifrado) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			byte[] mensajeDescifrado = cipher.doFinal(Base64.getDecoder().decode(mensajeCifrado));
			return new String(mensajeDescifrado);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Método principal para probar el cifrado y descifrado
	 */
	public static void main(String[] args) {
		CifradoRSA rsa = new CifradoRSA();
		String mensaje = "esto es un mensaje secreto";

		// Cifrar mensaje
		String mensajeCifrado = rsa.encriptar(mensaje);
		System.out.println("Mensaje cifrado: " + mensajeCifrado);

		// Descifrar mensaje
		String mensajeDescifrado = rsa.desencriptar(mensajeCifrado);
		System.out.println("Mensaje descifrado: " + mensajeDescifrado);
	}
	
	
}
