package com.example.gestordetareas.vista;

import android.content.Context;
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
import com.example.gestordetareas.modelo.Tarea;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaTareasActivity extends AppCompatActivity {

    private List<Tarea> listaTareas;
    private static final String PREFS_NAME = "MisTareas";
    private static final String KEY_TAREAS = "lista_tareas";
    private static final String TAG = "DepuracionTareas";

    private TareasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tareas);

        // ✅ Configurar Toolbar con botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // ✅ Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Cargar y mostrar las tareas
        listaTareas = cargarTareas();
        Log.d(TAG, "Número de tareas cargadas: " + listaTareas.size());

        if (listaTareas.isEmpty()) {
            Log.e(TAG, "La lista de tareas está VACÍA.");
        } else {
            for (Tarea tarea : listaTareas) {
                Log.d(TAG, "Tarea cargada: " + tarea.getTitulo());
            }
        }

        // 🔥 Adaptador local para la lista de tareas
        adapter = new TareasAdapter(this, listaTareas);
        recyclerView.setAdapter(adapter);


        // ✅ Botón para eliminar todas las tareas
        Button btnEliminarTodas = findViewById(R.id.btn_eliminar_todas);
        btnEliminarTodas.setOnClickListener(view -> eliminarTodasLasTareas(adapter));

        // 🔹 **Solución para el botón "Volver"**
        Button btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish()); // 🔥 Ahora el botón cerrará la actividad correctamente
    }

    // 🔥 Método para cargar tareas desde SharedPreferences
    private List<Tarea> cargarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TAREAS, "");

        Log.d(TAG, "JSON cargado desde SharedPreferences: " + json);

        if (!json.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<Tarea>>() {}.getType();
                List<Tarea> tareas = new Gson().fromJson(json, type);
                Log.d(TAG, "Número de tareas cargadas: " + tareas.size());
                return tareas;
            } catch (Exception e) {
                Log.e(TAG, "Error al cargar tareas", e);
            }
        }
        return new ArrayList<>();
    }

    // 🔥 Método para eliminar todas las tareas y actualizar la UI
    private void eliminarTodasLasTareas(TareasAdapter adapter) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        listaTareas.clear();
        adapter.notifyItemRangeRemoved(0, listaTareas.size()); // 🔥 Optimización
        Toast.makeText(this, "Todas las tareas han sido eliminadas", Toast.LENGTH_SHORT).show();
    }

    public void eliminarTarea(int position) {
        if (position >= 0 && position < listaTareas.size()) {
            listaTareas.remove(position);
            adapter.notifyItemRemoved(position); // 🔹 Ahora 'adapter' está declarado correctamente

            // 🔥 Guardar la lista actualizada en SharedPreferences
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String json = new Gson().toJson(listaTareas);
            editor.putString(KEY_TAREAS, json);
            editor.apply();

            Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
