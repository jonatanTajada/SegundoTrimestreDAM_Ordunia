package com.example.gestordetareas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaTareasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TareasAdapter adapter;
    private List<String> tareas;
    private static final String PREFS_NAME = "MisTareas";
    private static final String KEY_TAREAS = "lista_tareas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tareas);

        // Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recycler_view_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar tareas almacenadas
        tareas = cargarTareas();
        if (tareas == null) {
            tareas = new ArrayList<>(); // Evita null en caso de error
        }

        // Configurar Adapter
        adapter = new TareasAdapter(tareas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Regresar a la actividad anterior
        return true;
    }

    // Método para cargar las tareas desde SharedPreferences
    private List<String> cargarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TAREAS, "");
        if (!json.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                return new Gson().fromJson(json, type);
            } catch (Exception e) {
                e.printStackTrace(); // Evita crashes si hay datos corruptos
            }
        }
        return new ArrayList<>(); // Si no hay datos o hay error, devolver lista vacía
    }

    // Método para guardar tareas en SharedPreferences
    private void guardarTareas() {
        if (tareas != null && !tareas.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String json = new Gson().toJson(tareas);
            editor.putString(KEY_TAREAS, json);
            editor.apply();
            Toast.makeText(this, "Tareas guardadas", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para agregar una nueva tarea
    public void agregarTarea(String nuevaTarea) {
        if (nuevaTarea != null && !nuevaTarea.trim().isEmpty()) {
            tareas.add(nuevaTarea);
            adapter.notifyItemInserted(tareas.size() - 1); // Optimizado para mejor rendimiento
            guardarTareas();
        }
    }
}
