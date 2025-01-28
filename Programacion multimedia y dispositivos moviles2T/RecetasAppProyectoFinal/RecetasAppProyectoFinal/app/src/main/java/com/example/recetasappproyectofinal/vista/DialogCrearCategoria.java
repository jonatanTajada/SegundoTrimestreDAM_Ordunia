package com.example.recetasappproyectofinal.vista;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;

public class DialogCrearCategoria extends DialogFragment {

    private BaseDatos dbHelper;
    private EditText etNombreCategoria;
    private OnCategoriaCreadaListener listener;

    public interface OnCategoriaCreadaListener {
        void onCategoriaCreada();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriaCreadaListener) {
            listener = (OnCategoriaCreadaListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_crear_categoria, null);
        dialog.setContentView(view);

        dbHelper = new BaseDatos(requireContext());
        etNombreCategoria = view.findViewById(R.id.et_nombre_categoria);
        Button btnGuardar = view.findViewById(R.id.btn_guardar_categoria);

        btnGuardar.setOnClickListener(v -> guardarCategoria());

        return dialog;
    }

    private void guardarCategoria() {
        String nombreCategoria = etNombreCategoria.getText().toString().trim();

        if (TextUtils.isEmpty(nombreCategoria)) {
            Toast.makeText(requireContext(), "Introduce un nombre para la categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el ID del usuario desde SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        int usuarioId = dbHelper.obtenerIdUsuarioPorCorreo(prefs.getString("correoUsuario", null));

        if (usuarioId == -1) {
            Toast.makeText(requireContext(), "Error: Usuario no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = dbHelper.insertarCategoria(nombreCategoria, usuarioId);
        if (id != -1) {
            Toast.makeText(requireContext(), "Categoría guardada con éxito", Toast.LENGTH_SHORT).show();
            cerrarTeclado();
            dismiss(); // Cierra el diálogo

            if (listener != null) listener.onCategoriaCreada();
        } else {
            Toast.makeText(requireContext(), "Error al guardar la categoría", Toast.LENGTH_SHORT).show();
        }
    }

    private void cerrarTeclado() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
