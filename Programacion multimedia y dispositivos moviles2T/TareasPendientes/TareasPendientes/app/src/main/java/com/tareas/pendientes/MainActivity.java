package com.tareas.pendientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.tareas.pendientes.view.ConfiguracionActivity;
import com.tareas.pendientes.view.CrearTareaActivity;
import com.tareas.pendientes.view.LoginActivity;
import com.tareas.pendientes.view.TareasActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVerTareas, btnCrearTarea, btnConfiguracion, btnCerrarApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Verificar sesión antes de continuar
        if (!usuarioAutenticado()) {
            redirigirAlLogin();
            return;
        }

        setContentView(R.layout.activity_main);
        inicializarUI();
        configurarEventos();
    }

    private void inicializarUI() {
        btnVerTareas = findViewById(R.id.btnVerTareas);
        btnCrearTarea = findViewById(R.id.btnCrearTarea);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnCerrarApp = findViewById(R.id.btnCerrarApp);
    }

    private void configurarEventos() {
        btnVerTareas.setOnClickListener(v -> abrirActividad(TareasActivity.class));
        btnCrearTarea.setOnClickListener(v -> abrirActividad(CrearTareaActivity.class));
        btnConfiguracion.setOnClickListener(v -> abrirActividad(ConfiguracionActivity.class));

        // 🔴 Nuevo botón para CERRAR la APP
        btnCerrarApp.setOnClickListener(v -> cerrarAplicacion());
    }

    private void abrirActividad(Class<?> actividad) {
        startActivity(new Intent(this, actividad));
    }

    private boolean usuarioAutenticado() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        return prefs.contains("userId");
    }

    private void redirigirAlLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void cerrarAplicacion() {
        // 🔐 Borrar sesión antes de salir
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

        // ❌ Cerrar completamente la aplicación
        finishAffinity(); // 🔥 Cierra todas las actividades
        System.exit(0); // 🔴 Mata el proceso de la app (opcional)
    }
}
