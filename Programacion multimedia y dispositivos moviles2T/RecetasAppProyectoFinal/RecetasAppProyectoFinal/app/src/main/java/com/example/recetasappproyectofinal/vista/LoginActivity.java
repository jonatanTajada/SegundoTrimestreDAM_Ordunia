package com.example.recetasappproyectofinal.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;

public class LoginActivity extends AppCompatActivity {

    private BaseDatos dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new BaseDatos(this);

        EditText etCorreo = findViewById(R.id.et_correo);
        EditText etContrasena = findViewById(R.id.et_contrasena);
        Button btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);

        btnIniciarSesion.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.verificarUsuario(correo, contrasena)) {
                // Usuario verificado, guardar sesión y redirigir a CategoriaActivity
                getSharedPreferences("SesionUsuario", MODE_PRIVATE)
                        .edit()
                        .putString("correoUsuario", correo)
                        .apply();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Evitar volver al Login
            } else {
                // Credenciales incorrectas
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
