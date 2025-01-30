package com.example.gestordetareas.vista;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.gestordetareas.R;

public class AjustesActivity extends AppCompatActivity {

    private Button btnCambiarTema, btnEliminarTareas, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a los botones
        btnCambiarTema = findViewById(R.id.btn_cambiar_tema);
        btnEliminarTareas = findViewById(R.id.btn_eliminar_tareas);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_ajustes);

        // Eventos de los botones
        btnCambiarTema.setOnClickListener(view -> cambiarTema());
        btnEliminarTareas.setOnClickListener(view -> eliminarTodasLasTareas());
        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion());
    }

    // Habilitar botón de retroceso en la Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cambiarTema() {
        boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            guardarTema(false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            guardarTema(true);
        }

        recreate();
    }

    private void guardarTema(boolean isDarkMode) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("temaOscuro", isDarkMode);
        editor.apply();
    }

    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Aplicación")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarTodasLasTareas() {
        SharedPreferences prefs = getSharedPreferences("MisTareas", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Todas las tareas han sido eliminadas", Toast.LENGTH_SHORT).show();
    }
}
