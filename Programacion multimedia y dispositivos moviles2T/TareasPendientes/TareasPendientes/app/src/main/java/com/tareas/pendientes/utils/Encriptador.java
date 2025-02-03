package com.tareas.pendientes.utils;

import android.util.Base64;

public class Encriptador {


    // Método para encriptar un texto utilizando Base64.
    public static String encriptar(String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
    }


     // Método para desencriptar un texto codificado en Base64.
    public static String desencriptar(String textoEncriptado) {
        return new String(Base64.decode(textoEncriptado, Base64.DEFAULT));
    }


     //Método para verificar si la contraseña ingresada coincide con la almacenada.
    public static boolean verificarPassword(String passwordIngresada, String passwordAlmacenada) {
        return encriptar(passwordIngresada).equals(passwordAlmacenada);
    }
}
