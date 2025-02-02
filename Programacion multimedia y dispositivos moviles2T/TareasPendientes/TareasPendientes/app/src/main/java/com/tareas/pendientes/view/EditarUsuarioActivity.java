package com.tareas.pendientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tareas.pendientes.MainActivity;
import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText etNombre, etEmail;
    private Button btnGuardarCambios, btnCancelar;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        inicializarUI();

        btnGuardarCambios.setOnClickListener(v -> guardarCambios());
        btnCancelar.setOnClickListener(v -> finish()); // Cierra la actividad sin guardar
    }

    private void inicializarUI() {
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnCancelar = findViewById(R.id.btnCancelar);
        dbHelper = new DatabaseHelper(this);

        // Obtener usuario actual de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            cargarDatosUsuario();
        }
    }

    private void cargarDatosUsuario() {
        // Aquí iría la lógica para cargar los datos del usuario desde la BD
        // Por ahora se deja en blanco hasta definir su implementación
    }

    private void guardarCambios() {
        String nuevoNombre = etNombre.getText().toString().trim();
        String nuevoEmail = etEmail.getText().toString().trim();

        if (nuevoNombre.isEmpty() || nuevoEmail.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_obligatorios), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizado = dbHelper.actualizarUsuario(userId, nuevoNombre, nuevoEmail);

        if (actualizado) {
            // ✅ Actualizamos el correo en la sesión
            SharedPreferences sharedPreferences = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userEmail", nuevoEmail);
            editor.apply();

            Toast.makeText(this, getString(R.string.usuario_actualizado), Toast.LENGTH_SHORT).show();

            // 🔄 Redirigir a MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_actualizar), Toast.LENGTH_SHORT).show();
        }
    }

}
