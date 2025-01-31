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

    /**
     * Habilitar botón de retroceso en la Toolbar.
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
     * Método para cambiar entre modo claro y modo oscuro.
     * Guarda la preferencia del usuario y recrea la actividad.
     */
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

        recreate(); // Recargar actividad para aplicar cambios
    }

    /**
     * Guarda la preferencia del tema en `SharedPreferences`.
     * @param isDarkMode Indica si el usuario ha activado el modo oscuro.
     */
    private void guardarTema(boolean isDarkMode) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("temaOscuro", isDarkMode);
        editor.apply();
    }

    /**
     * Muestra un cuadro de diálogo para confirmar el cierre de sesión.
     * Si el usuario confirma, se ejecuta `cerrarSesion()`.
     */
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> cerrarSesion())
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Método para cerrar sesión.
     * Elimina los datos de sesión y redirige al usuario al `LoginActivity`.
     * 🔥 Muestra un cuadro de diálogo antes de cerrar sesión y salir de la aplicación.
     */
    private void cerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión y salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // 🔹 Borrar datos de sesión guardados
                    SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();

                    // 🔥 Cerrar la aplicación completamente
                    finishAffinity(); // Cierra todas las actividades
                    System.exit(0); // Asegura que el proceso se cierre completamente
                })
                .setNegativeButton("No", null)
                .show();
    }



    /**
     * Método para eliminar todas las tareas guardadas en `SharedPreferences`.
     * Muestra un mensaje de confirmación cuando la acción se completa.
     */
    private void eliminarTodasLasTareas() {
        SharedPreferences prefs = getSharedPreferences("MisTareas", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Todas las tareas han sido eliminadas", Toast.LENGTH_SHORT).show();
    }
}
