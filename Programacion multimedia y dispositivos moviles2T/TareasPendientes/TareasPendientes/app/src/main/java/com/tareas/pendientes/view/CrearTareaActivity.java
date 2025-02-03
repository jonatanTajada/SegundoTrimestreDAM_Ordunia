package com.tareas.pendientes.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;

import java.util.Calendar;

public class CrearTareaActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion;
    private TextView tvFecha;
    private ImageView ivImagen;
    private Button btnSeleccionarFecha, btnSeleccionarImagen, btnGuardar;
    private final DatabaseHelper dbHelper = new DatabaseHelper(this);
    private Uri imageUri;

    //  Lanzador para seleccionar una imagen desde la galería
    private final ActivityResultLauncher<Intent> seleccionarImagenLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            ivImagen.setImageURI(imageUri);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        //  Inicializar los elementos de la UI
        inicializarUI();

        //  Configurar eventos de los botones
        btnSeleccionarFecha.setOnClickListener(v -> abrirSelectorFecha());
        btnSeleccionarImagen.setOnClickListener(v -> abrirGaleria());
        btnGuardar.setOnClickListener(v -> guardarTarea());
    }

    //  Método para inicializar los elementos de la UI
    private void inicializarUI() {
        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        tvFecha = findViewById(R.id.tvFecha);
        ivImagen = findViewById(R.id.ivImagen);
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    //  Método para abrir el selector de fecha
    private void abrirSelectorFecha() {
        // Obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Mostrar la fecha actual al abrir la pantalla (solo si está vacío)
        if (tvFecha.getText().toString().isEmpty()) {
            tvFecha.setText(getString(R.string.fecha_placeholder, day, month + 1, year));
        }

        // Abrir el DatePickerDialog para seleccionar fecha
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) ->
                        tvFecha.setText(getString(R.string.fecha_placeholder, selectedDay, selectedMonth + 1, selectedYear)),
                year, month, day);

        dialog.show();
    }

    // Método para abrir la galería y seleccionar una imagen
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        seleccionarImagenLauncher.launch(intent);
    }

    //  Método para guardar una nueva tarea en la base de datos
    private void guardarTarea() {
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fecha = tvFecha.getText().toString().trim();
        String imagen = (imageUri != null) ? imageUri.toString() : null;

        // Validar que los campos requeridos no estén vacíos
        if (titulo.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = 1; //  Obtener usuario real desde sesión
        dbHelper.insertarTarea(userId, titulo, descripcion, fecha, imagen, false);

        Toast.makeText(this, getString(R.string.tarea_actualizada), Toast.LENGTH_SHORT).show();
        finish(); // Cierra la actividad y vuelve atrás
    }
}
