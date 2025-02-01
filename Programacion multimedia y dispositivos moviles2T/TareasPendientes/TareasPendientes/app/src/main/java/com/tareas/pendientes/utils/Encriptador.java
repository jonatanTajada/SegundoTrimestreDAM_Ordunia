package com.tareas.pendientes.utils;

import android.util.Base64;

public class Encriptador {

    public static String encriptar(String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
    }

    public static String desencriptar(String textoEncriptado) {
        return new String(Base64.decode(textoEncriptado, Base64.DEFAULT));
    }

    public static boolean verificarPassword(String passwordIngresada, String passwordAlmacenada) {
        return encriptar(passwordIngresada).equals(passwordAlmacenada);
    }
}
