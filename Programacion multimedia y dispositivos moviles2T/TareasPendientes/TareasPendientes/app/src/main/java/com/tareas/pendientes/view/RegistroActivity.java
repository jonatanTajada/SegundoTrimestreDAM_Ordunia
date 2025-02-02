package com.tareas.pendientes.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;
import com.tareas.pendientes.utils.Encriptador;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etPassword;
    private Button btnRegistrar, btnVolverLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dbHelper = new DatabaseHelper(this);
        inicializarUI();

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        btnVolverLogin.setOnClickListener(v -> irALogin());
    }

    private void inicializarUI() {
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);
    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            mostrarMensaje(getString(R.string.error_campos_obligatorios));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarMensaje(getString(R.string.error_email_invalido));
            return;
        }

        if (usuarioExiste(email)) {
            mostrarMensaje(getString(R.string.error_email_existente));
            return;
        }

        String passwordEncriptada = Encriptador.encriptar(password);
        long resultado = dbHelper.insertarUsuario(nombre, email, passwordEncriptada);

        if (resultado != -1) {
            mostrarMensaje(getString(R.string.registro_exitoso));
            irALogin();
        } else {
            mostrarMensaje(getString(R.string.error_registro));
        }
    }

    private boolean usuarioExiste(String email) {
        Cursor cursor = dbHelper.obtenerUsuarioPorEmail(email);
        boolean existe = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return existe;
    }

    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
