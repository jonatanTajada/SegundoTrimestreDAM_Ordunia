package com.tareas.pendientes.view;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
    private Button btnEliminarTodas, btnVolver;
    private List<Tarea> listaTareas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        // Inicializar elementos de la UI
        inicializarUI();

        // Cargar la lista de tareas desde la base de datos
        cargarTareas();

        // Configurar el botón para eliminar todas las tareas con confirmación
        btnEliminarTodas.setOnClickListener(v -> mostrarDialogoConfirmacion());

        // Configurar el botón para volver atrás
        btnVolver.setOnClickListener(v -> finish());
    }

    /**
     * Método para inicializar los elementos de la interfaz de usuario
     */
    private void inicializarUI() {
        dbHelper = new DatabaseHelper(this);
        recyclerTareas = findViewById(R.id.recyclerTareas);
        btnEliminarTodas = findViewById(R.id.btnEliminarTodas);
        btnVolver = findViewById(R.id.btnVolver);

        recyclerTareas.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Método para cargar las tareas desde la base de datos y actualizar la lista
     */
    private void cargarTareas() {
        listaTareas = dbHelper.obtenerTareas();
        tareasAdapter = new TareasAdapter(this, listaTareas, this);
        recyclerTareas.setAdapter(tareasAdapter);
    }

    /**
     * Método para mostrar un cuadro de diálogo de confirmación antes de eliminar todas las tareas
     */
    private void mostrarDialogoConfirmacion() {
        if (listaTareas.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_hay_tareas), Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmar_eliminar)
                .setMessage(R.string.mensaje_confirmar_eliminar_todas)
                .setPositiveButton(R.string.si, (dialog, which) -> {
                    dbHelper.eliminarTodasLasTareas(); // Elimina todas las tareas
                    cargarTareas(); // Recargar lista después de eliminar
                    Toast.makeText(this, getString(R.string.todas_tareas_eliminadas), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    /**
     * Método para editar una tarea
     */
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

    /**
     * Método para eliminar una tarea específica
     */
    @Override
    public void onEliminarClick(Tarea tarea) {
        dbHelper.eliminarTarea(tarea.getId());
        cargarTareas();
        Toast.makeText(this, getString(R.string.tarea_eliminada), Toast.LENGTH_SHORT).show();
    }

    /**
     * Método para marcar una tarea como completada o pendiente
     */
    @Override
    public void onMarcarCompletada(Tarea tarea) {
        dbHelper.actualizarEstadoTarea(tarea.getId(), !tarea.isCompletada());
        cargarTareas();
    }
}
