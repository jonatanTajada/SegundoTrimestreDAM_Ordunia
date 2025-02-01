package com.example.gestordetareas.vista;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gestordetareas.R;
import com.example.gestordetareas.dao.TareaDAO;
import com.example.gestordetareas.modelo.Tarea;

import java.util.Calendar;

public class AgregarTareaActivity extends AppCompatActivity {

    private EditText etTarea, etDescripcion, etFecha;
    private ImageView ivImagen;
    private Uri imageUri;
    private int tareaId = -1;

    private TareaDAO tareaDAO;

    // Lanzador para abrir la galería y seleccionar una imagen
    ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    ivImagen.setImageURI(imageUri);
                }
            });

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

        // Obtener instancia de Room Database
        tareaDAO = AppDatabase.getInstance(this).tareaDAO();

        // Referencias a vistas
        etTarea = findViewById(R.id.et_tarea);
        etDescripcion = findViewById(R.id.et_descripcion);
        etFecha = findViewById(R.id.et_fecha);
        ivImagen = findViewById(R.id.iv_imagen);
        Button btnGuardar = findViewById(R.id.btn_guardar_tarea);
        Button btnElegirImagen = findViewById(R.id.btn_elegir_imagen);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Cargar datos si estamos editando una tarea
        Intent intent = getIntent();
        if (intent.hasExtra("TAREA_ID")) {
            tareaId = intent.getIntExtra("TAREA_ID", -1);
            etTarea.setText(intent.getStringExtra("TITULO"));
            etDescripcion.setText(intent.getStringExtra("DESCRIPCION"));
            etFecha.setText(intent.getStringExtra("FECHA"));
            String imagenUriString = intent.getStringExtra("IMAGEN_URI");
            if (imagenUriString != null && !imagenUriString.isEmpty()) {
                imageUri = Uri.parse(imagenUriString);
                ivImagen.setImageURI(imageUri);
            }
        }

        // Configurar eventos de botones
        btnElegirImagen.setOnClickListener(v -> elegirImagen());
        btnGuardar.setOnClickListener(v -> {
            if (tareaId != -1) {
                actualizarTarea();
            } else {
                guardarNuevaTarea();
            }
            finish();
        });

        btnVolver.setOnClickListener(v -> finish());
        etFecha.setOnClickListener(v -> mostrarDatePickerDialog());
    }

    /**
     * Método para manejar la acción del botón de retroceso en la Toolbar.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 🔥 Guarda una nueva tarea en la base de datos.
     */
    private void guardarNuevaTarea() {
        String titulo = etTarea.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "⚠️ Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea tarea = new Tarea(titulo, descripcion, fecha, imageUri != null ? imageUri.toString() : "");

        AsyncTask.execute(() -> tareaDAO.insertarTarea(tarea));

        Toast.makeText(this, "✅ Tarea guardada", Toast.LENGTH_SHORT).show();
    }

    /**
     * ✏️ Actualiza una tarea existente en la base de datos.
     */
    private void actualizarTarea() {
        String nuevoTitulo = etTarea.getText().toString().trim();
        String nuevaDescripcion = etDescripcion.getText().toString().trim();
        String nuevaFecha = etFecha.getText().toString().trim();

        if (nuevoTitulo.isEmpty() || nuevaDescripcion.isEmpty()) {
            Toast.makeText(this, "⚠️ Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea tareaActualizada = new Tarea(nuevoTitulo, nuevaDescripcion, nuevaFecha,
                imageUri != null ? imageUri.toString() : "");
        tareaActualizada.setId(tareaId); // Mantener el mismo ID

        AsyncTask.execute(() -> tareaDAO.actualizarTarea(tareaActualizada));

        Toast.makeText(this, "✅ Tarea actualizada", Toast.LENGTH_SHORT).show();
    }

    /**
     * 📅 Muestra un `DatePickerDialog` para seleccionar la fecha.
     */
    private void mostrarDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, year1, monthOfYear, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            etFecha.setText(fecha);
        }, year, month, day);

        datePickerDialog.show();
    }

    /**
     * 🖼 Abre la galería para elegir una imagen.
     */
    private void elegirImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(galleryIntent);
    }
}
