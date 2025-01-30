package com.example.gestordetareas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordetareas.R;
import com.example.gestordetareas.dao.UsuarioDAO;
import com.example.gestordetareas.utils.EncriptacionUtils;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etPassword, etTelefono, etCiudad;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        etNombre = findViewById(R.id.et_nombre);
        etCorreo = findViewById(R.id.et_correo);
        etPassword = findViewById(R.id.et_password);
        etTelefono = findViewById(R.id.et_telefono);
        etCiudad = findViewById(R.id.et_ciudad);
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Inicializar DAO
        usuarioDAO = new UsuarioDAO(this);

        // Botón Registrar
        btnRegistrar.setOnClickListener(v -> registrarUsuario());

        // Botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String ciudad = etCiudad.getText().toString().trim();

        // Validación de campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || telefono.isEmpty() || ciudad.isEmpty()) {
            Toast.makeText(this, "⚠️ Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el usuario ya existe
        if (usuarioDAO.verificarLogin(correo, "")) {  // Solo verificamos si existe el usuario
            Toast.makeText(this, "⚠️ El correo ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }


        // Encriptar contraseña
        String passwordEncriptada = EncriptacionUtils.encriptar(password);

        // Registrar usuario
        boolean registroExitoso = usuarioDAO.registrarUsuario(nombre, correo, passwordEncriptada, telefono, ciudad);

        if (registroExitoso) {
            Toast.makeText(this, "✅ Registro exitoso. Inicia sesión.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "❌ Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
