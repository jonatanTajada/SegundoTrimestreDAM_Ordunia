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

        // Verificar si el usuario está autenticado
        if (!usuarioAutenticado()) {
            redirigirAlLogin();
            return;
        }

        setContentView(R.layout.activity_main);

        // Inicializar la interfaz de usuario
        inicializarUI();

        // Configurar eventos de los botones
        configurarEventos();
    }

    // Método para inicializar los botones de la pantalla principal
    private void inicializarUI() {
        btnVerTareas = findViewById(R.id.btnVerTareas);
        btnCrearTarea = findViewById(R.id.btnCrearTarea);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnCerrarApp = findViewById(R.id.btnCerrarApp);
    }

    // Método para asignar las acciones a los botones
    private void configurarEventos() {
        btnVerTareas.setOnClickListener(v -> abrirActividad(TareasActivity.class));
        btnCrearTarea.setOnClickListener(v -> abrirActividad(CrearTareaActivity.class));
        btnConfiguracion.setOnClickListener(v -> abrirActividad(ConfiguracionActivity.class));

        btnCerrarApp.setOnClickListener(v -> cerrarAplicacion());
    }

    // Método para abrir una nueva actividad
    private void abrirActividad(Class<?> actividad) {
        startActivity(new Intent(this, actividad));
    }

    // Método para verificar si el usuario tiene sesión iniciada
    private boolean usuarioAutenticado() {
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        return prefs.contains("userId");
    }

    // Método para redirigir al login si no hay sesión iniciada
    private void redirigirAlLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Método para cerrar completamente la aplicación
    private void cerrarAplicacion() {
        //  Borrar sesión antes de salir
        SharedPreferences prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

        finishAffinity();
        System.exit(0);
    }
}
