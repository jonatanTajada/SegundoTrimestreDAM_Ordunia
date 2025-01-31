package com.example.gestordetareas.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.gestordetareas.R;

public class DashboardActivity extends AppCompatActivity {

    private Button btnVerTareas, btnAgregarTarea, btnAjustes, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ✅ Configuración de la Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 🔹 Referencias a los botones del Dashboard
        btnVerTareas = findViewById(R.id.btn_ver_tareas);
        btnAgregarTarea = findViewById(R.id.btn_agregar_tarea);
        btnAjustes = findViewById(R.id.btn_ajustes);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_dashboard);

        // 📌 Navegación entre actividades
        btnVerTareas.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, ListaTareasActivity.class))
        );

        btnAgregarTarea.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AgregarTareaActivity.class))
        );

        btnAjustes.setOnClickListener(view ->
                startActivity(new Intent(DashboardActivity.this, AjustesActivity.class))
        );

        btnCerrarSesion.setOnClickListener(view -> mostrarDialogoCerrarSesion()); // Muestra el diálogo antes de cerrar la app
    }

    /**
     * 🔙 Habilita el botón de retroceso en la Toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Cierra la actividad y vuelve atrás
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 🔥 Muestra un cuadro de diálogo antes de cerrar sesión y salir de la aplicación.
     */
    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión y salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    //  Borrar datos de sesión guardados
                    SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();

                    // Cerrar la aplicación completamente
                    finishAffinity(); // Cierra todas las actividades
                    System.exit(0); // Asegura que el proceso se cierre completamente
                })
                .setNegativeButton("No", null)
                .show();
    }

}
