package com.example.gestordetareas.vista;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestordetareas.R;
import com.example.gestordetareas.dao.TareaDAO;
import com.example.gestordetareas.modelo.Tarea;

import java.util.List;

public class ListaTareasActivity extends AppCompatActivity {

    private TareasAdapter adapter;
    private TareaDAO tareaDAO;
    private List<Tarea> listaTareas;

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

        // ✅ Obtener instancia de la base de datos
        tareaDAO = AppDatabase.getInstance(this).tareaDAO();

        // ✅ Cargar y mostrar tareas desde la base de datos
        cargarTareas();

        // ✅ Configurar botones
        Button btnEliminarTodas = findViewById(R.id.btn_eliminar_todas);
        btnEliminarTodas.setOnClickListener(v -> eliminarTodasLasTareas());

        Button btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish());
    }

    // 🔥 Método para cargar tareas desde la base de datos
    private void cargarTareas() {
        AsyncTask.execute(() -> {
            listaTareas = tareaDAO.obtenerTodasLasTareas();
            runOnUiThread(() -> {
                adapter = new TareasAdapter(this, listaTareas);
                RecyclerView recyclerView = findViewById(R.id.recycler_view_tareas);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    // 🔥 Método para eliminar todas las tareas
    private void eliminarTodasLasTareas() {
        AsyncTask.execute(() -> {
            for (Tarea tarea : listaTareas) {
                tareaDAO.eliminarTarea(tarea);
            }
            runOnUiThread(() -> {
                listaTareas.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Todas las tareas eliminadas", Toast.LENGTH_SHORT).show();
            });
        });
    }

    // 🔥 Método para eliminar una tarea específica
    public void eliminarTarea(int position) {
        if (position >= 0 && position < listaTareas.size()) {
            Tarea tareaAEliminar = listaTareas.get(position);
            AsyncTask.execute(() -> {
                tareaDAO.eliminarTarea(tareaAEliminar);
                runOnUiThread(() -> {
                    listaTareas.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, listaTareas.size());
                    Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                });
            });
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
