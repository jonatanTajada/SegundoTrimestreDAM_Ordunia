package com.example.gestordetareas.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordetareas.R;
import com.example.gestordetareas.dao.UsuarioDAO;
import com.example.gestordetareas.modelo.Usuario;
import com.example.gestordetareas.utils.EncriptacionUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etPassword;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        etCorreo = findViewById(R.id.et_correo);
        etPassword = findViewById(R.id.et_password);
        Button btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        TextView tvRegistro = findViewById(R.id.tv_registro);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Inicializar el DAO para manejar la base de datos SQLite
        usuarioDAO = new UsuarioDAO(this);

        // Botón para iniciar sesión
        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());

        // Botón para ir a la pantalla de registro
        tvRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Botón para volver atrás
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Encriptar la contraseña antes de comparar
        String passwordEncriptada = EncriptacionUtils.encriptar(password);

        //  Verificar el usuario con la contraseña encriptada
        boolean usuarioValido = usuarioDAO.verificarLogin(correo, passwordEncriptada);

        if (usuarioValido) {
            // Recuperar el usuario completo de la base de datos
            Usuario usuario = usuarioDAO.obtenerUsuario(correo);

            if (usuario != null) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                // Guardar sesión iniciada
                SharedPreferences prefs = getSharedPreferences("SesionUsuario", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("sesion_iniciada", true);
                editor.putString("usuario_nombre", usuario.getNombre());
                editor.putString("usuario_correo", usuario.getCorreo());
                editor.apply();

                // Ir al Dashboard
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("usuario_nombre", usuario.getNombre());
                intent.putExtra("usuario_correo", usuario.getCorreo());
                startActivity(intent);
                finish(); // Cierra la pantalla de login
            } else {
                Toast.makeText(this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
