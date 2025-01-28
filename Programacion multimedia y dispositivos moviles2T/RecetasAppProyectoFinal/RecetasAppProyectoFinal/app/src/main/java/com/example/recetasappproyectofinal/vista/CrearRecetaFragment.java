package com.example.recetasappproyectofinal.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;

public class CrearRecetaFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private BaseDatos dbHelper;
    private ImageView ivImagen;
    private Uri imagenUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_receta, container, false);

        dbHelper = new BaseDatos(requireContext());

        EditText etTitulo = view.findViewById(R.id.et_titulo_receta);
        EditText etIngredientes = view.findViewById(R.id.et_ingredientes);
        EditText etInstrucciones = view.findViewById(R.id.et_instrucciones);
        Spinner spCategorias = view.findViewById(R.id.sp_categorias);
        ivImagen = view.findViewById(R.id.iv_imagen_receta);
        Button btnSeleccionarImagen = view.findViewById(R.id.btn_seleccionar_imagen);
        Button btnGuardarReceta = view.findViewById(R.id.btn_guardar_receta);

        // Acción para seleccionar una imagen
        btnSeleccionarImagen.setOnClickListener(v -> seleccionarImagen());

        // Acción para guardar la receta
        btnGuardarReceta.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String ingredientes = etIngredientes.getText().toString().trim();
            String instrucciones = etInstrucciones.getText().toString().trim();
            String categoria = spCategorias.getSelectedItem().toString();

            if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(ingredientes) || TextUtils.isEmpty(instrucciones) || imagenUri == null) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos y selecciona una imagen", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener ID de la categoría seleccionada
            int categoriaId = dbHelper.obtenerIdCategoria(categoria);

            // Insertar receta en la base de datos
            long resultado = dbHelper.insertarReceta(titulo, ingredientes, instrucciones, imagenUri.toString(), categoriaId, 1); // Usuario ID temporal

            if (resultado != -1) {
                Toast.makeText(requireContext(), "Receta guardada exitosamente", Toast.LENGTH_SHORT).show();

                // Volver al fragmento de recetas
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "Error al guardar la receta", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == requireActivity().RESULT_OK && data != null) {
            imagenUri = data.getData();
            ivImagen.setImageURI(imagenUri); // Mostrar la imagen seleccionada
        }
    }
}
