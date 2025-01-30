package com.example.gestordetareas.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestordetareas.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.gestordetareas.modelo.Tarea;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaTareasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TareasAdapter adapter;
    private List<Tarea> tareas;
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

        // Configurar Adapter
        adapter = new TareasAdapter(this, tareas);
        recyclerView.setAdapter(adapter);

        // Configurar botón de eliminar todas las tareas
        Button btnEliminarTodas = findViewById(R.id.btn_eliminar_todas);
        btnEliminarTodas.setOnClickListener(v -> eliminarTodasLasTareas());

        // Botón Volver
        Button btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish()); // Cierra la actividad
    }

    // Manejo del botón de retroceso en la barra de herramientas
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Cierra esta actividad y vuelve atrás
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para cargar tareas desde SharedPreferences
    private List<Tarea> cargarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(KEY_TAREAS, "[]"); // Evita errores con un JSON vacío
        Log.d("JSON_DEBUG", "Cargando JSON: " + json);

        try {
            Type type = new TypeToken<ArrayList<Tarea>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            Log.e("JSON_DEBUG", "Error al parsear JSON", e);
            return new ArrayList<>(); // Devuelve una lista vacía si falla
        }
    }

    // Método para guardar las tareas en SharedPreferences
    private void guardarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(tareas);
        editor.putString(KEY_TAREAS, json);
        editor.apply();
    }

    // Método para eliminar una tarea
    public void eliminarTarea(int position) {
        if (position >= 0 && position < tareas.size()) {
            tareas.remove(position);
            adapter.notifyItemRemoved(position); // Solo notifica la tarea eliminada
            guardarTareas();
            Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para eliminar todas las tareas
    private void eliminarTodasLasTareas() {
        if (!tareas.isEmpty()) {
            int total = tareas.size();
            tareas.clear();
            adapter.notifyItemRangeRemoved(0, total); // Notifica solo los eliminados
            guardarTareas();
            Toast.makeText(this, "Todas las tareas eliminadas", Toast.LENGTH_SHORT).show();
        }
    }
}
