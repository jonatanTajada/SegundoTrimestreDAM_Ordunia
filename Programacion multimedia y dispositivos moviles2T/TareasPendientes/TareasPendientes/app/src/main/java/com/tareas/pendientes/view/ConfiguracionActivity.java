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

        // Inicializar elementos de la interfaz
        inicializarUI();

        // Configurar eventos de los botones
        btnAcercaDe.setOnClickListener(v -> startActivity(new Intent(this, AcercaDeActivity.class)));
        btnCambiarTema.setOnClickListener(v -> cambiarTema());

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

        // Actualizar el texto del botón según el tema actual
        actualizarTextoBotonTema();
    }

    // Método para inicializar los elementos de la interfaz
    private void inicializarUI() {
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnCambiarTema = findViewById(R.id.btnCambiarTema);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnVolver = findViewById(R.id.btnVolver);
        sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
    }

    // Método para cambiar entre modo claro y oscuro
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

    // Método para actualizar el texto del botón de cambio de tema
    private void actualizarTextoBotonTema() {
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        btnCambiarTema.setText(modoOscuro ? getString(R.string.modo_claro) : getString(R.string.modo_oscuro));
    }

    // Método para cerrar sesión y redirigir al login
    private void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        // Redirigir al login y limpiar la pila de actividades
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
