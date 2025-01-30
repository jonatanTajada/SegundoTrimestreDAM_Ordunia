package com.example.gestordetareas.vista;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
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
import com.example.gestordetareas.modelo.Tarea;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AgregarTareaActivity extends AppCompatActivity {

    private EditText etTarea, etDescripcion, etFecha;
    private ImageView ivImagen;
    private Uri imageUri;
    private int tareaId;

    private static final String PREFS_NAME = "MisTareas";
    private static final String KEY_TAREAS = "lista_tareas";

    // Lanzador para abrir la galería
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

        // Obtener los datos pasados por el Intent (crear o actualizar)
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("TITULO");
        String descripcion = intent.getStringExtra("DESCRIPCION");
        String fecha = intent.getStringExtra("FECHA");
        String imagenUriString = intent.getStringExtra("IMAGEN_URI");
        tareaId = intent.getIntExtra("TAREA_ID", -1);

        // Referencias a vistas
        etTarea = findViewById(R.id.et_tarea);
        etDescripcion = findViewById(R.id.et_descripcion);
        etFecha = findViewById(R.id.et_fecha);
        ivImagen = findViewById(R.id.iv_imagen);
        Button btnGuardar = findViewById(R.id.btn_guardar_tarea);
        Button btnElegirImagen = findViewById(R.id.btn_elegir_imagen);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Si estamos editando una tarea, prellenamos los datos
        if (tareaId != -1) {
            etTarea.setText(titulo);
            etDescripcion.setText(descripcion);
            etFecha.setText(fecha);
            if (imagenUriString != null && !imagenUriString.isEmpty()) {
                imageUri = Uri.parse(imagenUriString);
                ivImagen.setImageURI(imageUri);
            }
        }

        btnElegirImagen.setOnClickListener(v -> elegirImagen());
        btnGuardar.setOnClickListener(v -> {
            if (tareaId != -1) {
                actualizarTarea(tareaId);
                setResult(RESULT_OK); // Notificar que hubo cambios
            } else {
                guardarTarea();
            }
            finish(); // Cerrar actividad después de guardar
        });



        btnVolver.setOnClickListener(v -> finish()); // Botón volver
        etFecha.setOnClickListener(v -> mostrarDatePickerDialog());
    }

    // Habilitar botón de atrás en la Toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actualizarTarea(int tareaId) {
        String nuevoTitulo = etTarea.getText().toString().trim();
        String nuevaDescripcion = etDescripcion.getText().toString().trim();
        String nuevaFecha = etFecha.getText().toString().trim();

        if (nuevoTitulo.isEmpty() || nuevaDescripcion.isEmpty()) {
            Toast.makeText(this, getString(R.string.tarea_vacia_error), Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea tarea = new Tarea(nuevoTitulo, nuevaDescripcion, nuevaFecha, imageUri, tareaId);

        List<Tarea> tareas = cargarTareas();
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == tareaId) {
                tareas.set(i, tarea);
                break;
            }
        }

        guardarListaTareas(tareas);
        Toast.makeText(this, getString(R.string.tarea_actualizada), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void guardarTarea() {
        String nuevoTitulo = etTarea.getText().toString().trim();
        String nuevaDescripcion = etDescripcion.getText().toString().trim();
        String fechaSeleccionada = etFecha.getText().toString().trim();

        if (nuevoTitulo.isEmpty() || nuevaDescripcion.isEmpty()) {
            Toast.makeText(this, getString(R.string.tarea_vacia_error), Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea tarea = new Tarea(nuevoTitulo, nuevaDescripcion, fechaSeleccionada, imageUri);

        List<Tarea> tareas = cargarTareas();
        tareas.add(tarea);

        guardarListaTareas(tareas);
        Toast.makeText(this, getString(R.string.tarea_guardada), Toast.LENGTH_SHORT).show();
        finish();
    }

    private List<Tarea> cargarTareas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TAREAS, "");

        if (!json.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<Tarea>>() {}.getType();
                return new Gson().fromJson(json, type);
            } catch (Exception e) {
                android.util.Log.e("CargarTareas", "Error al cargar tareas", e);
            }
        }
        return new ArrayList<>();
    }

    private void guardarListaTareas(List<Tarea> tareas) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(tareas);
        editor.putString(KEY_TAREAS, json);
        editor.apply();
    }

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

    private void elegirImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(galleryIntent);
    }
}
