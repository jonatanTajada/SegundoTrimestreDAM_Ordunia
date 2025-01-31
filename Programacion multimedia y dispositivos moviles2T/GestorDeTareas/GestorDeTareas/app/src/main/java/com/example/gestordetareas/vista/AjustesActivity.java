package com.example.gestordetareas.vista;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // ✅ Configurar Toolbar con botón de retroceso y título "Ajustes"
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.ajustes_title)); // Ahora se ve "Ajustes"
        }

        // 🔹 Referencias a los botones
        Button btnCambiarTema = findViewById(R.id.btn_cambiar_tema);
        Button btnEliminarTareas = findViewById(R.id.btn_eliminar_tareas);
        Button btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_ajustes);
        Button btnSobreApp = findViewById(R.id.btn_sobre_app); // Botón nuevo

        // 📌 Eventos de los botones
        btnCambiarTema.setOnClickListener(view -> cambiarTema());
        btnEliminarTareas.setOnClickListener(view -> eliminarTodasLasTareas());
        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion());
        btnSobreApp.setOnClickListener(view -> {
            Intent intent = new Intent(AjustesActivity.this, SobreAppActivity.class);
            startActivity(intent);
        });

    }

    /**
     * 🔙 Habilita el botón de retroceso en la Toolbar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 🌗 Cambia entre modo claro y oscuro.
     */
    private void cambiarTema() {
        boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;

        AppCompatDelegate.setDefaultNightMode(isDarkMode ?
                AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);

        guardarTema(!isDarkMode);
        recreate();
    }

    /**
     * 💾 Guarda la preferencia del tema en `SharedPreferences`.
     */
    private void guardarTema(boolean isDarkMode) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        prefs.edit().putBoolean("temaOscuro", isDarkMode).apply();
    }

    /**
     * 🔥 Muestra un cuadro de diálogo para confirmar el cierre de sesión.
     */
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.cerrar_sesion_titulo))
                .setMessage(getString(R.string.cerrar_sesion_mensaje))
                .setPositiveButton(getString(R.string.cerrar_sesion_si), (dialog, which) -> {
                    // Borrar datos de sesión
                    SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                    prefs.edit().clear().apply();

                    // Cerrar la aplicación completamente
                    finishAffinity();
                    System.exit(0);
                })
                .setNegativeButton(getString(R.string.cerrar_sesion_no), null)
                .show();
    }

    /**
     * 🗑 Elimina todas las tareas guardadas en `SharedPreferences`.
     */
    private void eliminarTodasLasTareas() {
        SharedPreferences prefs = getSharedPreferences("MisTareas", MODE_PRIVATE);
        prefs.edit().clear().apply();
        Toast.makeText(this, getString(R.string.msg_task_deleted), Toast.LENGTH_SHORT).show();
    }

}
