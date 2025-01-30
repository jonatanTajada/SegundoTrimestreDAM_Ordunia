package com.example.gestordetareas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordetareas.vista.DashboardActivity;
import com.example.gestordetareas.vista.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si el usuario ya ha iniciado sesión previamente
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        boolean sesionIniciada = prefs.getBoolean("sesion_iniciada", false);

        Intent intent;
        if (sesionIniciada) {
            // Si la sesión está iniciada, ir al Dashboard
            intent = new Intent(this, DashboardActivity.class);
        } else {
            // Si no, ir al Login
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish(); // Cierra MainActivity para evitar que quede en segundo plano
    }
}
