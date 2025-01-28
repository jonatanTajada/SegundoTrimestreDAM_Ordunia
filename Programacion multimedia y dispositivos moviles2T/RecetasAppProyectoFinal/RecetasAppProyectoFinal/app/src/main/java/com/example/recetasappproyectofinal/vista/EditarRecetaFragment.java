package com.example.recetasappproyectofinal.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Receta;

public class EditarRecetaFragment extends Fragment {

    private static final int SELECT_IMAGE_REQUEST = 1;

    private BaseDatos dbHelper;
    private int recetaId;
    private Uri imagenUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_receta, container, false);

        dbHelper = new BaseDatos(requireContext());

        EditText etTituloReceta = view.findViewById(R.id.et_titulo_receta);
        EditText etIngredientes = view.findViewById(R.id.et_ingredientes);
        EditText etInstrucciones = view.findViewById(R.id.et_instrucciones);
        ImageView ivImagenReceta = view.findViewById(R.id.iv_imagen_receta);
        Button btnSeleccionarImagen = view.findViewById(R.id.btn_seleccionar_imagen);
        Button btnGuardarCambios = view.findViewById(R.id.btn_guardar_cambios);

        if (getArguments() != null) {
            recetaId = getArguments().getInt("recetaId", -1);
            Receta receta = dbHelper.obtenerRecetaPorId(recetaId);

            if (receta != null) {
                etTituloReceta.setText(receta.getTitulo());
                etIngredientes.setText(receta.getIngredientes());
                etInstrucciones.setText(receta.getInstrucciones());

                if (receta.getImagenUri() != null) {
                    imagenUri = Uri.parse(receta.getImagenUri());
                    ivImagenReceta.setImageURI(imagenUri);
                }
            }
        }

        btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_IMAGE_REQUEST);
        });

        btnGuardarCambios.setOnClickListener(v -> {
            String titulo = etTituloReceta.getText().toString();
            String ingredientes = etIngredientes.getText().toString();
            String instrucciones = etInstrucciones.getText().toString();
            String imagenPath = imagenUri != null ? imagenUri.toString() : null;

            if (titulo.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            int filasActualizadas = dbHelper.actualizarReceta(recetaId, titulo, ingredientes, instrucciones, imagenPath);
            if (filasActualizadas > 0) {
                Toast.makeText(requireContext(), "Receta actualizada con éxito", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "Error al actualizar la receta", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            imagenUri = data.getData();
            ImageView ivImagenReceta = getView().findViewById(R.id.iv_imagen_receta);
            ivImagenReceta.setImageURI(imagenUri);
        }
    }
}
