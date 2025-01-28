package com.example.recetasappproyectofinal.vista;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Receta;

public class DetallesRecetaFragment extends Fragment {

    private BaseDatos dbHelper;
    private int recetaId;

    private ImageView ivImagenReceta;
    private TextView tvTituloReceta;
    private TextView tvIngredientes;
    private TextView tvInstrucciones;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_receta, container, false);

        // Inicializar BaseDatos y elementos de la vista
        dbHelper = new BaseDatos(requireContext());
        ivImagenReceta = view.findViewById(R.id.iv_imagen_receta);
        tvTituloReceta = view.findViewById(R.id.tv_titulo_receta);
        tvIngredientes = view.findViewById(R.id.tv_ingredientes);
        tvInstrucciones = view.findViewById(R.id.tv_instrucciones);

        // Obtener el ID de la receta desde los argumentos
        if (getArguments() != null) {
            recetaId = getArguments().getInt("recetaId", -1);
        }

        // Cargar los detalles de la receta
        cargarDetallesReceta();

        return view;
    }

    private void cargarDetallesReceta() {
        // Obtener la receta desde la base de datos
        Receta receta = dbHelper.obtenerRecetaPorId(recetaId);

        if (receta != null) {
            // Establecer el título, ingredientes e instrucciones
            tvTituloReceta.setText(receta.getTitulo());
            tvIngredientes.setText(receta.getIngredientes());
            tvInstrucciones.setText(receta.getInstrucciones());

            // Cargar la imagen si existe, de lo contrario, usar una imagen predeterminada
            if (receta.tieneImagen()) {
                ivImagenReceta.setImageURI(Uri.parse(receta.getImagenUri()));
            } else {
                ivImagenReceta.setImageResource(R.drawable.foto_predeterminada); // Cambiar por tu imagen predeterminada
            }
        }
    }
}
