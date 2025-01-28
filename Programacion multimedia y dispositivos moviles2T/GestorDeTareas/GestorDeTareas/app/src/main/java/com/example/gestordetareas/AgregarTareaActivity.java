package com.example.gestordetareas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AgregarTareaActivity extends AppCompatActivity {

    private EditText etTarea;
    private static final String PREFS_NAME = "MisTareas";
    private static final String KEY_TAREAS = "lista_tareas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        // Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a vistas
        etTarea = findViewById(R.id.et_tarea);
        Button btnGuardar = findViewById(R.id.btn_guardar_tarea);

        // Evento click para guardar la tarea
        btnGuardar.setOnClickListener(v -> guardarTarea());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Cierra la actividad y vuelve atrás
        return true;
    }

    // Método para guardar la tarea
    private void guardarTarea() {
        String nuevaTarea = etTarea.getText().toString().trim();

        if (nuevaTarea.isEmpty()) {
            Toast.makeText(this, getString(R.string.tarea_vacia_error), Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la lista de tareas actual
        List<String> tareas = cargarTareas();
        tareas.add(nuevaTarea);

        // Guardar la lista actualizada en SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(tareas);
        editor.putString(KEY_TAREAS, json);
        editor.apply();

        // Verificar en los logs si la lista se guarda correctamente
        android.util.Log.d("GuardarTarea", "Tareas guardadas: " + json);

        Toast.makeText(this, getString(R.string.tarea_guardada), Toast.LENGTH_SHORT).show();

        // Redirigir al Dashboard
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }



    // Método para cargar la lista de tareas desde SharedPreferences
    private List<String> cargarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TAREAS, "");
        if (!json.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                List<String> tareas = new Gson().fromJson(json, type);

                // Verificar en los logs si las tareas se cargan correctamente
                android.util.Log.d("CargarTareas", "Tareas cargadas: " + tareas);

                return tareas;
            } catch (Exception e) {
                android.util.Log.e("CargarTareas", "Error al cargar tareas", e);
            }
        }
        return new ArrayList<>(); // Devuelve lista vacía si no hay datos
    }


}
