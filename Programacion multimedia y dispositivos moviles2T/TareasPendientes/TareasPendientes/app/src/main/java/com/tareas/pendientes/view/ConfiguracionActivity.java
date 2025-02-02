package com.tareas.pendientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.tareas.pendientes.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnAcercaDe, btnCambiarTema, btnEditarUsuario, btnCerrarSesion, btnVolver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        inicializarUI();

        btnAcercaDe.setOnClickListener(v -> startActivity(new Intent(this, AcercaDeActivity.class)));
        btnCambiarTema.setOnClickListener(v -> cambiarTema());

        // 🛠 Verificar si EditarUsuarioActivity está en el Manifest
        btnEditarUsuario.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, EditarUsuarioActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
        btnVolver.setOnClickListener(v -> finish());

        actualizarTextoBotonTema();
    }

    private void inicializarUI() {
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnCambiarTema = findViewById(R.id.btnCambiarTema);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnVolver = findViewById(R.id.btnVolver);
        sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
    }

    private void cambiarTema() {
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("modoOscuro", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("modoOscuro", true);
        }

        editor.apply();
        actualizarTextoBotonTema();
    }

    private void actualizarTextoBotonTema() {
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        btnCambiarTema.setText(modoOscuro ? getString(R.string.modo_claro) : getString(R.string.modo_oscuro));
    }

    private void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply(); // 🔥 Eliminar TODA la sesión correctamente

        // 🔄 Redirigir al login y limpiar la pila de actividades
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // 🚫 Evita que el usuario pueda volver atrás
    }
}
