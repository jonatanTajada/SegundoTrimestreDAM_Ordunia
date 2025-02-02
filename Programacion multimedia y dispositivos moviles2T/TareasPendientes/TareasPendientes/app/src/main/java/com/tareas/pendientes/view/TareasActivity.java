package com.tareas.pendientes.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tareas.pendientes.R;
import com.tareas.pendientes.adapter.TareasAdapter;
import com.tareas.pendientes.controller.DatabaseHelper;
import com.tareas.pendientes.model.Tarea;
import java.util.ArrayList;
import java.util.List;

public class TareasActivity extends AppCompatActivity implements TareasAdapter.OnItemClickListener {

    private RecyclerView recyclerTareas;
    private TareasAdapter tareasAdapter;
    private DatabaseHelper dbHelper;
    private Button btnEliminarTodas, btnVolver;  // ✅ Se agregó btnVolver
    private List<Tarea> listaTareas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        inicializarUI(); // ✅ Método de inicialización

        cargarTareas(); // ✅ Cargar tareas desde la BD

        // 🗑 Botón para eliminar todas las tareas
        btnEliminarTodas.setOnClickListener(v -> {
            if (!listaTareas.isEmpty()) {
                dbHelper.eliminarTodasLasTareas();
                cargarTareas();
                Toast.makeText(this, getString(R.string.todas_tareas_eliminadas), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.no_hay_tareas), Toast.LENGTH_SHORT).show();
            }
        });

        // 🔙 Botón para volver atrás
        btnVolver.setOnClickListener(v -> finish());
    }

    private void inicializarUI() {
        dbHelper = new DatabaseHelper(this);
        recyclerTareas = findViewById(R.id.recyclerTareas);
        btnEliminarTodas = findViewById(R.id.btnEliminarTodas);
        btnVolver = findViewById(R.id.btnVolver);  // ✅ Se inicializó correctamente

        recyclerTareas.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarTareas() {
        listaTareas = dbHelper.obtenerTareas();
        tareasAdapter = new TareasAdapter(this, listaTareas, this);
        recyclerTareas.setAdapter(tareasAdapter);
    }

    @Override
    public void onEditarClick(Tarea tarea) {
        Intent intent = new Intent(this, EditarTareaActivity.class);
        intent.putExtra("ID_TAREA", tarea.getId());
        intent.putExtra("TITULO", tarea.getTitulo());
        intent.putExtra("FECHA", tarea.getFecha());
        intent.putExtra("DESCRIPCION", tarea.getDescripcion());
        intent.putExtra("IMAGEN", tarea.getImagen());
        startActivity(intent);
    }


    @Override
    public void onEliminarClick(Tarea tarea) {
        dbHelper.eliminarTarea(tarea.getId());
        cargarTareas();
        Toast.makeText(this, getString(R.string.tarea_eliminada), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarcarCompletada(Tarea tarea) {
        dbHelper.actualizarEstadoTarea(tarea.getId(), !tarea.isCompletada());
        cargarTareas();
    }
}
