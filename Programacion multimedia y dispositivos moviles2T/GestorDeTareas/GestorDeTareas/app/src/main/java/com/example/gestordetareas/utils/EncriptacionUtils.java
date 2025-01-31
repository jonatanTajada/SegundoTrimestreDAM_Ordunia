package com.example.gestordetareas.utils;

import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptacionUtils {

    private static final String TAG = "EncriptacionUtils";

   // Método para encriptar una contraseña
    public static String encriptar(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error al encriptar la contraseña: ", e);
            return null;
        }
    }
}
