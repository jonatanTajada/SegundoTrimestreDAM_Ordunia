package com.tareas.pendientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.tareas.pendientes.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnVolver, btnAcercaDe, btnCambiarTema, btnEditarUsuario, btnCerrarSesion;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        inicializarUI();

        btnVolver.setOnClickListener(v -> finish()); // Cierra la actividad actual
        btnAcercaDe.setOnClickListener(v -> startActivity(new Intent(this, AcercaDeActivity.class)));
        btnCambiarTema.setOnClickListener(v -> cambiarTema());
        btnEditarUsuario.setOnClickListener(v -> startActivity(new Intent(this, EditarUsuarioActivity.class)));
        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());

        actualizarTextoBotonTema();
    }

    private void inicializarUI() {
        btnVolver = findViewById(R.id.btnVolver);
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnCambiarTema = findViewById(R.id.btnCambiarTema);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        sharedPreferences = getSharedPreferences("Preferencias", MODE_PRIVATE);
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

        // 🔄 Reiniciar la actividad para aplicar cambios inmediatamente
        recreate();
    }

    private void actualizarTextoBotonTema() {
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        btnCambiarTema.setText(modoOscuro ? getString(R.string.modo_claro) : getString(R.string.modo_oscuro));
    }

    private void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
