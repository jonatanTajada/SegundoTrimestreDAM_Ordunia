package com.example.recetasappproyectofinal.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Usuario;

public class PerfilFragment extends Fragment {

    private BaseDatos dbHelper;
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnActualizarPerfil;
    private Usuario usuarioActual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        dbHelper = new BaseDatos(requireContext());

        // Inicializar vistas
        etNombre = view.findViewById(R.id.et_nombre_perfil);
        etCorreo = view.findViewById(R.id.et_correo_perfil);
        etContrasena = view.findViewById(R.id.et_contrasena_perfil);
        btnActualizarPerfil = view.findViewById(R.id.btn_actualizar_perfil);

        // Cargar datos del usuario
        cargarDatosUsuario();

        // Configurar botón de actualización
        btnActualizarPerfil.setOnClickListener(v -> actualizarPerfil());

        return view;
    }

    private void cargarDatosUsuario() {
        // Obtener el correo del argumento
        String correoUsuario = getArguments() != null ? getArguments().getString("correoUsuario") : null;

        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            mostrarMensaje("No se encontró información del usuario");
            return;
        }

        // Buscar el usuario en la base de datos
        usuarioActual = dbHelper.obtenerUsuarioPorCorreo(correoUsuario);

        if (usuarioActual != null) {
            etNombre.setText(usuarioActual.getNombre());
            etCorreo.setText(usuarioActual.getCorreo());
            etCorreo.setEnabled(false); // Deshabilitar edición del correo
            etCorreo.setFocusable(false);
            etCorreo.setClickable(false);
            etContrasena.setText(usuarioActual.getContrasena());
        } else {
            mostrarMensaje("Error al cargar los datos del usuario");
        }
    }

    private void actualizarPerfil() {
        if (usuarioActual == null) {
            mostrarMensaje("No se puede actualizar. El usuario no está cargado.");
            return;
        }

        String nuevoNombre = etNombre.getText().toString().trim();
        String nuevaContrasena = etContrasena.getText().toString().trim();

        if (!validarCampos(nuevoNombre, nuevaContrasena)) {
            return;
        }

        // Actualizar los datos en la base de datos
        int filasActualizadas = dbHelper.actualizarUsuario(usuarioActual.getCorreo(), nuevoNombre, nuevaContrasena);

        if (filasActualizadas > 0) {
            usuarioActual.setNombre(nuevoNombre);
            usuarioActual.setContrasena(nuevaContrasena);
            mostrarMensaje("Perfil actualizado con éxito");
        } else {
            mostrarMensaje("Error al actualizar el perfil");
        }
    }

    private boolean validarCampos(String nombre, String contrasena) {
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Los campos no pueden estar vacíos");
            return false;
        }

        if (nombre.length() < 3) {
            mostrarMensaje("El nombre debe tener al menos 3 caracteres");
            return false;
        }

        if (contrasena.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
